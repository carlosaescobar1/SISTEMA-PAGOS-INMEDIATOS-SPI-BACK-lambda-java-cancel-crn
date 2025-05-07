package co.com.ath.service;

import co.com.ath.commons.util.ATHException;
import co.com.ath.commons.util.Util;
import co.com.ath.constants.*;
import co.com.ath.entity.DynamoSpiEntity;
import co.com.ath.mapper.*;
import co.com.ath.models.MessageDto;
import co.com.ath.models.dynamo.DynamoSpiDto;
import co.com.ath.models.parameter.ParamFlowConfig;
import co.com.ath.models.parameter.ParamVaultUpload;
import co.com.ath.models.parameter.VaultServicesTimeOut;
import co.com.ath.opensearch.logs.constants.ActionConstants;
import co.com.ath.opensearch.logs.constants.IndexConstants;
import co.com.ath.opensearch.logs.entity.index_batch.OSIndexBatch;
import co.com.ath.redebanconn.model.HeadersRq;
import co.com.ath.redebanconn.model.HttpResponseWrapper;
import co.com.ath.redebanconn.model.MsgInformationResponse;
import co.com.ath.redebanconn.model.enrollment.EnrollmentRq;
import co.com.ath.redebanconn.service.create.IRedEnrollmentService;
import co.com.ath.redebanconn.service.create.RedEnrollmentServiceImpl;
import co.com.ath.repository.DynamoRepository;
import co.com.ath.util.TimeLineUtil;
import co.com.ath.util.VaultSelectorUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * RedEnrollmentTransvServiceImpl
 * <p>
 * Desarrollo ATH - SPBVI
 * <p>
 * Creado el: 09 de septiembre de 2024
 *
 * @author Luis F. Herreño Mateus
 * @version 1.0
 * @since 1.0
 * <p>
 * Requerimiento: SPBVI - Sistema de pagos de bajo valor inmediatos
 * <p>
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 * <p>
 * Clase encargada de la creación de llaves en la camara de redeban y el directorio
 * aval
 */

@Slf4j
@AllArgsConstructor
public class RedEnrollmentTransvServiceImpl implements IRedEnrollmentTransvService {

    /**
     * Interfaz del servicio de creación de redeban
     */
    private final IRedEnrollmentService redEnrollmentService = new RedEnrollmentServiceImpl();

    /**
     * Clase que mapea los headers.
     */
    private final HeadersMapper headersMapper;

    /**
     * Clase que contiene los metodos de mapeo del body de la petición
     * de redeban
     */
    private final RequestMapper requestMapper;

    /**
     * Interfaz que llama al método de actualización de opensearch
     */
    private final IUpdateOpenSearchService updateOpenSearchService;

    /**
     * Interfaz que llama al método de sincronización opensearch
     */
    private final IOpenSearchSynchService openSearchSynchService;

    /**
     * Clase que llama a los métodos para hacer el uso de
     * logs de timeLine
     */
    private final TimeLineUtil timeLineUtil;

    /**
     * Clase que contiene los métodos para mapear al objeto de
     * sonda
     */
    private final IndexBatchMapper indexBatchMapper;

    /**
     * Consentimiento del parameter store
     */
    private final ParamFlowConfig paramFlowConfig;

    /**
     * Objeto con la información de la camara de redeban.
     */
    private final VaultSelectorUtil vaultSelectorUtil;

    /**
     * Objeto que contiene los valores de los timeOut
     */
    private final VaultServicesTimeOut vaultServicesTimeOut;

    /**
     * Clase que contiene las operaciones de dynamo
     */
    private final DynamoRepository dynamoRepository;

    private final IDynamoMapper dynamoMapper = new DynamoMapperImpl();

    /**
     * Método encargado de realizar el consumo
     * a la camara de redeban
     *
     * @param messageDto
     * @param subject
     */
    @Override
    public void redEnrollService(MessageDto messageDto, String subject, DynamoSpiDto dynamoSpiDto, String rqId, String dateOperation, String rqUUID) {

        HeadersRq headersRq = headersMapper.headersMapper(
                dynamoSpiDto.getAcctInfo().getBankId(),
                rqId,
                rqUUID,
                dateOperation
        );

        log.info("Entra a redEnrollService");

        EnrollmentRq enrollmentRq = requestMapper.bodyMapper(dynamoSpiDto);

        log.info("EnrollmentRq: {}", Util.object2String(enrollmentRq));

        String fileName = (messageDto.getMessageDtoBatch() != null ?
                messageDto.getMessageDtoBatch().getOsIndexBatch().getFileName()
                : subject);

        ParamVaultUpload paramVaultUpload = vaultSelectorUtil.selectorVault();

        HttpResponseWrapper httpResponseWrapper = null;

        if (paramFlowConfig.getVaultSyncFlow().equalsIgnoreCase(ConstantsEnum.ACTIVE_FLOW.getValue())) {
            try {

                timeLineUtil.sendLogRq(enrollmentRq,
                        headersRq);

                //variable consentimineto
                String consent = dynamoSpiDto.getConsent();
                //variable fecha consentimiento
                String effDtConsent = dynamoSpiDto.getEffDtConsent();

                if (ConsentEnum.N.getValue().equalsIgnoreCase(consent)) {
                    log.info("Consentimiento es N, creación en DirectorioAval");
                    DynamoSpiEntity dynamoSpiEntity = dynamoMapper.dynamoDtoToDynamoEntity(dynamoSpiDto);
                    dynamoSpiEntity.setStatusDirectory("DIRAVAL");
                    //Guardado en Dynamo
                    dynamoRepository.save(dynamoSpiEntity);
                    //guardado en OpenSearch
                    openSearchSynchService.openSearchSyncEnroll(dynamoSpiEntity);
                    //envio de mensaje dto para batch
                    updateOpenSearchService.processSuccessBatchAction(messageDto);
                } else if (ConstantsEnum.S.getValue().equalsIgnoreCase(consent) && effDtConsent != null && !effDtConsent.isEmpty()) {
                    log.info("Consentimiento es S, conexion con redeban");

                    httpResponseWrapper = redEnrollmentService
                            .enrollmentKeyService(
                                    enrollmentRq,
                                    headersRq,
                                    paramVaultUpload.getUrlEnrollmentVault(),
                                    vaultServicesTimeOut.getRedEnrollmentTimeOut());

                    log.info("HttpResponseWrapper: {}", Util.object2String(httpResponseWrapper));

                    MsgInformationResponse msgInformationResponse = (MsgInformationResponse)
                            Util.string2object(httpResponseWrapper.getResponseBody(), MsgInformationResponse.class);

                    if (messageDto.getMessageDtoBatch() != null
                            && messageDto.getMessageDtoBatch().getOsIndexBatch() != null
                            && messageDto.getMessageDtoBatch().getOsIndexBatch()
                            .getEventBatch().equalsIgnoreCase(ActionConstants.EVENT_BATCH_VAULT_SYNC.getValue())) {

                        processEnrollmentServiceRs(msgInformationResponse,
                                httpResponseWrapper.getStatusCode(),
                                dynamoSpiDto, messageDto, headersRq,
                                fileName, rqId, subject);

                    } else {
                        processEnrollmentServiceRs(msgInformationResponse, httpResponseWrapper.getStatusCode(),
                                dynamoSpiDto, messageDto, enrollmentRq, headersRq, dynamoSpiDto.getConsent(),
                                paramVaultUpload.getConsentMigrate(), fileName, rqId, subject, rqUUID);
                    }
                } else {
                    log.info("El campo effDtConsent es NULL");
                }

            } catch (IOException conExp) {
                throw new ATHException(ResponseServiceEnum.ERROR_TEC_EXCEPTION.getServerStatusCode(),
                        ResponseServiceEnum.ERROR_TEC_EXCEPTION.getStatusDesc() + conExp.getMessage(),
                        ResponseServiceEnum.ERROR_TEC_EXCEPTION.getStatusCode());
            } finally {
                timeLineUtil.sendLogRs(enrollmentRq,
                        headersRq);
            }
        } else {
            processEnrollmentDirAvalService(dynamoSpiDto, messageDto, rqId);
        }
    }


    /**
     * Método encargado de procesar la respuesta de la cámara y hacer el
     * respectivo redireccionamiento a sonda
     *
     * @param msgInformationResponse
     * @param httpStatusCode
     * @param dynamoSpiDto
     * @param messageDto
     * @param headersRq
     * @param fileName
     */
    private void processEnrollmentServiceRs(MsgInformationResponse msgInformationResponse,
                                            int httpStatusCode,
                                            DynamoSpiDto dynamoSpiDto,
                                            MessageDto messageDto,
                                            HeadersRq headersRq,
                                            String fileName,
                                            String rqId,
                                            String subject
    ) {

        if (httpStatusCode == ResponseStatusCodeEnum.PERSON_SUCCESS_STATUS_CODE.getValue()
                && msgInformationResponse.getMessageInformation().getMsgCode().equalsIgnoreCase(ResponseCodeEnum.RED_PERSON_SUCCESS_STATUS_CODE.getValue())
                || (httpStatusCode == ResponseStatusCodeEnum.PERSON_CREATED_STATUS_CODE.getValue()
                && msgInformationResponse.getMessageInformation().getMsgCode().equalsIgnoreCase(ResponseCodeEnum.RED_PERSON_CREATED_STATUS_CODE.getValue()))
        ) {

            updateOpenSearchService.processSuccessBatchAction(messageDto);

        } else {
            updateOpenSearchService.processOpensearchAction(messageDto,
                    dynamoSpiDto,
                    headersRq,
                    fileName,
                    msgInformationResponse.getMessageInformation().getMsgCode(),
                    msgInformationResponse.getMessageInformation().getMsgDescription(),
                    rqId,
                    subject
            );
        }
    }


    private void processEnrollmentServiceRs(MsgInformationResponse msgInformationResponse,
                                            int httpStatusCode,
                                            DynamoSpiDto dynamoSpiDto,
                                            MessageDto messageDto, EnrollmentRq enrollmentRq,
                                            HeadersRq headersRq, String consent, String uri,
                                            String fileName, String rqId, String subject, String rqUUID
    ) {

        if (httpStatusCode == ResponseStatusCodeEnum.PERSON_SUCCESS_STATUS_CODE.getValue()
                && msgInformationResponse.getMessageInformation().getMsgCode().equalsIgnoreCase(ResponseCodeEnum.RED_PERSON_SUCCESS_STATUS_CODE.getValue())
                || (httpStatusCode == ResponseStatusCodeEnum.PERSON_CREATED_STATUS_CODE.getValue()
                && msgInformationResponse.getMessageInformation().getMsgCode().equalsIgnoreCase(ResponseCodeEnum.RED_PERSON_CREATED_STATUS_CODE.getValue()))
        ) {

            log.info("Respuesta del servicio exitosa: {}", Util.object2String(msgInformationResponse));


            log.info("Inicia guardado en dynamo");

            DynamoSpiEntity dynamoSpiEntity = dynamoMapper.dynamoDtoToDynamoEntity(dynamoSpiDto);

            log.info("Pasa mapeo dynamo: {}", Util.object2String(dynamoSpiEntity));

            if (dynamoRepository.load(dynamoSpiEntity.getId(), dynamoSpiEntity.getSk()) == null) {
                log.info("Entidad de dynamo: {}", Util.object2StringWithNulls(dynamoSpiEntity));

                dynamoRepository.save(dynamoSpiEntity);

                log.info("Finalizo guardado en dynamo");
            }

            if (updateOpenSearchService.searchKey(dynamoSpiDto) == 0) {
                log.info("Inicia sincronización con OpenSearch");

                openSearchSynchService.openSearchSyncEnroll(dynamoSpiEntity);

                log.info("Finalizo guardado opensearch ");
            }
            updateOpenSearchService.processSuccessBatchAction(messageDto);

            if (consent.equalsIgnoreCase(ConstantsEnum.S.getValue())
                    && paramFlowConfig.getConsentFlow().equalsIgnoreCase(ConstantsEnum.ACTIVE_FLOW.getValue())) {
                //Servicio PUT
//                migrateTermsAndConditions(enrollmentRq, headersRq, uri);
            } else if (consent.equalsIgnoreCase(ConstantsEnum.S.getValue())) {
                saveOpensearchConsent(dynamoSpiDto, headersRq, fileName);
            }

        } else {

            log.info("Respuesta del servicio no exitosa: {}", Util.object2String(msgInformationResponse));

            updateOpenSearchService.processOpensearchAction(messageDto,
                    dynamoSpiDto,
                    headersRq,
                    fileName,
                    msgInformationResponse.getMessageInformation().getMsgCode(),
                    msgInformationResponse.getMessageInformation().getMsgDescription(),
                    rqId,
                    subject);


        }
    }

    /**
     * Método encargado de procesar la respuesta de la cámara y hacer el
     * respectivo redireccionamiento a sonda
     *
     * @param dynamoSpiDto
     * @param messageDto
     */
    private void processEnrollmentDirAvalService(
            DynamoSpiDto dynamoSpiDto,
            MessageDto messageDto, String rqUUID) {

        log.info("Inicia guardado en dynamo flujo de cámaras no activo");
        DynamoSpiEntity dynamoSpiEntity = dynamoMapper.dynamoDtoToDynamoEntity(dynamoSpiDto);

        if (dynamoRepository.load(dynamoSpiEntity.getId(), dynamoSpiEntity.getSk()) == null) {
            log.info("Entidad de dynamo a guardar: {}", Util.object2StringWithNulls(dynamoSpiEntity));
            dynamoRepository.save(dynamoSpiEntity);

        }

        if (updateOpenSearchService.searchKey(dynamoSpiDto) == 0) {

            log.info("Inicia sincronización con OpenSearch flujo de cámaras no activo");
            openSearchSynchService.openSearchSyncEnroll(dynamoSpiEntity);
            log.info("Finaliza sincronización con OpenSearch flujo de cámaras no activo");
        }

        if (updateOpenSearchService.searchBatch(dynamoSpiDto) == 0) {
            log.info("Inicia actualizacion/guardado en index_batch");
            saveOpensearchVaultSync(dynamoSpiDto, rqUUID, messageDto.getMessageDtoDynamo().getFileName());
            log.info("Finaliza actualizacion/guardado en index_batch");
        }

    }


//    private void migrateTermsAndConditions(EnrollmentRq enrollmentRq, HeadersRq headersRq, String uri) throws IOException {
//
//        ConsentRq consentRq = requestMapper.consentBodyMapper(enrollmentRq);
//        String keyValue = consentRq.getKeyValue();
//
//        HttpResponseWrapper consentResponse = redConsentService.confirmKey(
//                consentRq,
//                keyValue,
//                uri,
//                vaultServicesTimeOut.getRedEnrollmentTimeOut(),
//                headersRq
//        );
//
//        log.info("Código de respuesta del servicio de consentimiento: {}", consentResponse.getStatusCode());
//        log.info("Respuesta del servicio de consentimiento: {}", consentResponse.getResponseBody());
//
//    }

    private void saveOpensearchConsent(DynamoSpiDto dynamoSpiDto, HeadersRq headersRq, String fileName) {
        OSIndexBatch osIndexBatch = indexBatchMapper.mapEnrollmentRqToIndexBatch(dynamoSpiDto, headersRq,
                ActionConstants.EVENT_BATCH_CONSENT.getValue(),
                fileName
        );
        updateOpenSearchService.addElement(osIndexBatch, IndexConstants.SONDA_INDEX);
    }

    private void saveOpensearchVaultSync(DynamoSpiDto dynamoSpiDto, String rqUUID, String fileName) {
        OSIndexBatch osIndexBatch = indexBatchMapper.mapEnrollmentRqToIndexBatch(dynamoSpiDto, rqUUID,
                ActionConstants.EVENT_BATCH_VAULT_SYNC.getValue(),
                fileName
        );
        updateOpenSearchService.addElement(osIndexBatch, IndexConstants.SONDA_INDEX);
    }

    private void saveOpensearchMigrate(DynamoSpiDto dynamoSpiDto, String rqUUID, String fileName) {
        OSIndexBatch osIndexBatch = indexBatchMapper.mapEnrollmentRqToIndexBatch(dynamoSpiDto, rqUUID,
                ConstantsEnum.INDEX_MIGRATE.getValue(),
                fileName
        );
        updateOpenSearchService.addElement(osIndexBatch, IndexConstants.SONDA_INDEX);
    }
}
