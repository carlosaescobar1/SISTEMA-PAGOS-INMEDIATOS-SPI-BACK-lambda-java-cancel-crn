package co.com.avc.service;

import co.com.ath.commons.util.Util;
import co.com.ath.cornerconn.models.CornersHeadersRq;
import co.com.ath.cornerconn.models.MsgErrors;
import co.com.ath.cornerconn.service.cancellation.CornerCancellationKeyServiceImpl;
import co.com.ath.cornerconn.service.cancellation.ICornerCancellationKeyService;
import co.com.avc.entity.Ath.DynamoSpiEntity;
import co.com.avc.mapper.*;
import co.com.avc.models.MessageDtoBatch;
import co.com.avc.models.MsgInformationResponseSuccess;
import co.com.avc.models.dynamoAth.DynamoSpiDto;
import co.com.avc.models.parameter.ParamFlowConfig;
import co.com.avc.models.parameter.ParamVaultUpload;
import co.com.avc.models.parameter.ParameterStoreDto;
import co.com.avc.models.parameter.VaultServicesTimeOut;
import co.com.ath.cornerconn.models.HttpResponseWrapper;
//import co.com.ath.cornerconn.models.MsgInformationResponse;
import co.com.avc.repository.DynamoRepository;
import co.com.avc.service.interfaces.IOpenSearchSynchService;
import co.com.avc.service.interfaces.ICorCancelBatchTransvService;
import co.com.avc.service.interfaces.IUpdateOpenSearchService;
import co.com.avc.util.TimeLineUtil;
import co.com.avc.util.VaultSelectorUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static co.com.avc.constants.ResponseStatusCodeEnum.PERSON_SUCCESS_STATUS_CODE;

/**
 * CorCancelBatchTransvServiceImpl
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
 * Clase encargada de la creación de llaves en la camara de corner y el directorio
 * aval
 */

@Slf4j
@AllArgsConstructor
public class CorCancelBatchTransvServiceImpl implements ICorCancelBatchTransvService {

    /**
     * Interfaz del servicio de creación de corner
     */
    private final ICornerCancellationKeyService corDeleteService = new CornerCancellationKeyServiceImpl();


    /**
     * Clase que llama a los métodos para hacer el uso de
     * logs de timeLine
     */
    private final TimeLineUtil timeLineUtil;

    /**
     * Clase que contiene los métodos para mapear al objeto de
     * sondas
     */
    private final IndexBatchMapper indexBatchMapper;

    /**
     * Consentimiento del parameter store
     */
    private final ParamFlowConfig paramFlowConfig;

    /**
     * Objeto con la información de la camara de corner
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
    /**
     * Interfaz que llama al método de actualización de opensearch
     */
    private final IUpdateOpenSearchService updateOpenSearchService;

    /**
     * Interfaz que llama al método de sincronización opensearch
     */
    private final IOpenSearchSynchService openSearchSynchService;

    /**
     * Modelo del parameter de las cámaras disponibles
     */
    private final ParamVaultUpload paramVaultUpload;


    /**
     * Método encargado de realizar el consumo
     * a la camara de corner
     *
     * @param messageDtoBatch   mensaje de sonda
     * @param subject           nombre del archivo
     * @param dynamoSpiDto      dto de dynamo
     * @param rqId              id de la petición
     * @param parameterStoreDto parameter store
     */
    @Override
    public void corCancelBatchService(MessageDtoBatch messageDtoBatch, String subject,
                                      DynamoSpiDto dynamoSpiDto, String rqId,
                                      ParameterStoreDto parameterStoreDto) {

        /**
         * Null  por que no los pide el contrato
         */
        CornersHeadersRq headersRq = null;




        CorVaultService corVaultService = new CorVaultService(
                corDeleteService,
                updateOpenSearchService
        );
        //--->
        HttpResponseWrapper httpResponseWrapper = corVaultService.vaultService(dynamoSpiDto,
                paramVaultUpload, vaultServicesTimeOut, null, subject, rqId);
                /*
        MsgInformationResponseSuccess msgInformation = (MsgInformationResponseSuccess)
                Util.string2object(httpResponseWrapper.getResponseBody(), MsgInformationResponseSuccess.class);
                */

        DynamoSpiEntity dynamoSpiEntity = IDynamoMapper.INSTANCE.dynamoDtoToDynamoEntity(dynamoSpiDto);
        //Verifica que no llegue nulo el messageDtoBatch y que contenga el osIndexBatch
        if (messageDtoBatch != null && messageDtoBatch.getOsIndexBatch() != null) {
            // Deserializar la respuesta según el caso
            Object responseObject;
            if (httpResponseWrapper.getStatusCode() == PERSON_SUCCESS_STATUS_CODE.getValue()) {
                responseObject = Util.string2object(httpResponseWrapper.getResponseBody(), MsgInformationResponseSuccess.class);
            } else {
                responseObject = Util.string2object(httpResponseWrapper.getResponseBody(), MsgErrors.class);
            }

            if (httpResponseWrapper.getStatusCode() == PERSON_SUCCESS_STATUS_CODE.getValue()) {
                MsgInformationResponseSuccess successResponse = (MsgInformationResponseSuccess) responseObject;
                if (successResponse != null && successResponse.getValue_key() != null) {
                    if (updateOpenSearchService.searchKey(dynamoSpiDto.getKey().getKeyId()) > 0) {
                        openSearchSynchService.openSearchSyncCancel(dynamoSpiEntity);
                        log.info("Finalizó borrado en OpenSearch"); //Se registra que se elimino en openseach
                        updateOpenSearchService.processSuccessBatchAction(messageDtoBatch);
                    } else {
                        log.error("No existe el registro en OpenSearch");
                    }
                    if (dynamoRepository.load(dynamoSpiEntity.getId(), dynamoSpiEntity.getSk()) != null) {
                        //Elimina el registro de la tabla de dynamo
                        dynamoRepository.delete(dynamoSpiEntity);
                    } else {
                        log.error("No existe el registro en Dynamo");
                    }
                } else {
                    log.error("Respuesta exitosa pero value_key es nulo");
                }
            } else {
                MsgErrors errorResponse = (MsgErrors) responseObject;
                log.info("Respuesta del servicio en Corner no fue exitosa: {}", Util.object2String(errorResponse));
                String fileName = messageDtoBatch.getOsIndexBatch().getFileName();
                updateOpenSearchService.processOpensearchAction(messageDtoBatch, fileName,
                        errorResponse.getDetailErrors() != null ? errorResponse.getDetailErrors().getType() : "Error desconocido",
                        errorResponse.getDetailErrors() != null ? errorResponse.getDetailErrors().getMsgError() : "No hay descripción",
                        rqId);
            }
        }
    }


}