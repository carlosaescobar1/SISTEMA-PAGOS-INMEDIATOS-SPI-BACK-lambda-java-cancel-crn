package co.com.avc.service;
import co.com.ath.commons.util.Util;
import co.com.avc.cornerconn.models.DetailErrors;
import co.com.avc.cornerconn.models.MsgErrors;
import co.com.avc.constants.BatchEnum;
import co.com.avc.entity.Ath.DynamoSpiEntity;
import co.com.avc.mapper.IDynamoMapper;
import co.com.avc.mapper.IndexBatchMapper;
import co.com.avc.models.MessageDtoKeysCancel;
import co.com.avc.models.MsgInformationResponseSuccess;
import co.com.avc.models.dynamoAth.DynamoSpiDto;
import co.com.avc.models.parameter.ParamFlowConfig;
import co.com.avc.models.parameter.ParamVaultUpload;
import co.com.avc.models.parameter.VaultServicesTimeOut;
import co.com.ath.opensearch.logs.entity.index_batch.OSIndexBatch;
import co.com.ath.opensearch.logs.entity.index_key.OSIndexKey;
import co.com.ath.redebanconn.model.HeadersRq;
import co.com.avc.cornerconn.models.HttpResponseWrapper;
import co.com.avc.cornerconn.models.MsgInformationResponse;
import co.com.avc.cornerconn.service.cancellation.ICornerCancellationKeyService;
import co.com.avc.cornerconn.service.cancellation.CornerCancellationKeyServiceImpl;
import co.com.avc.repository.DynamoRepository;
import co.com.avc.service.interfaces.IOpenSearchSynchService;
import co.com.avc.service.interfaces.IUpdateOpenSearchService;
import co.com.avc.util.TimeLineUtil;
import co.com.avc.util.VaultSelectorUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.client.opensearch.core.SearchTemplateResponse;
import org.opensearch.client.opensearch.core.search.Hit;

import java.util.HashMap;
import java.util.List;

import static co.com.avc.constants.ResponseCodeEnum.*;
import static co.com.ath.opensearch.logs.constants.ActionConstants.ONLINE_CANCELLATION;
import static co.com.ath.opensearch.logs.constants.IndexConstants.SONDA_INDEX;
/**
 * CorCancelCntServiceImpl
 * <p>
 * Implementa los servicios para gestionar la cancelacion de claves, interactuando
 * con la camara CORNER y Opensearch.
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
public class CorCancelCntServiceImpl {
    /**
     * Interfaz del servicio de creación de redeban
     */

    private final ICornerCancellationKeyService corDeleteService = new CornerCancellationKeyServiceImpl();


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

    /**
     * Interfaz que llama al método de actualización de opensearch
     */
    private final IUpdateOpenSearchService updateOpenSearchService;

    /**
     * Interfaz que llama al método de sincronización opensearch
     */
    private final IOpenSearchSynchService openSearchSynchService;

    /**
     *
     */
    private final ParamVaultUpload paramVaultUpload;


    /**
     * Método encargado de realizar el consumo
     * a la camara de corner
     *
     * @param subject
     * @param dynamoSpiDto
     * @param rqId
     * @param messageDtoKeysCancel
     * @param dynEntity
     */
    public void corCancelMigService(String subject,
                                    DynamoSpiDto dynamoSpiDto, String rqId,
                                    MessageDtoKeysCancel messageDtoKeysCancel, boolean dynEntity) {
        HttpResponseWrapper httpResponseWrapper;

        CorVaultService corVaultService = new CorVaultService(
                corDeleteService,
                updateOpenSearchService
        );
        //Para enviar la solicitud de cancelación a la cámara
        httpResponseWrapper = corVaultService.vaultService(
                dynamoSpiDto, paramVaultUpload, vaultServicesTimeOut, null, subject, rqId);

        // Deserializar la respuesta según el caso
        Object responseObject;
        if (httpResponseWrapper.getStatusCode() == 200) { // Código HTTP de éxito
            responseObject = Util.string2object(httpResponseWrapper.getResponseBody(), MsgInformationResponseSuccess.class);
        } else {
            responseObject = Util.string2object(httpResponseWrapper.getResponseBody(), MsgErrors.class);
        }

        if (httpResponseWrapper.getStatusCode() == 200) {
            MsgInformationResponseSuccess successResponse = (MsgInformationResponseSuccess) responseObject;
            if (successResponse != null && successResponse.getValue_key() != null) { // Ajustado a getValueKey()
                log.info("Respuesta del servicio exitosa: {}", Util.object2String(successResponse));

                DynamoSpiEntity dynamoSpiEntity = IDynamoMapper.INSTANCE.dynamoDtoToDynamoEntity(dynamoSpiDto);

                //Es true si el registro existe en dynamo
                if (Boolean.TRUE.equals(dynEntity)) {
                    dynamoRepository.delete(dynamoSpiEntity);
                } else {
                    log.info("No existe registro en Dynamo de la llave");
                }
               // cancelOpenSearch(messageDtoKeysCancel.getKeyId());
                updateBatch(messageDtoKeysCancel, rqId);
            } else {
                log.error("Respuesta exitosa pero value_key es nulo");
                updateOpenSearchService.saveIndexRejected(dynamoSpiDto, subject,
                        "Error desconocido", "value_key es nulo en respuesta exitosa", rqId);
            }
        } else {
            MsgErrors errorResponse = (MsgErrors) responseObject;
            if (errorResponse != null && errorResponse.getDetailErrors() != null) {
                DetailErrors detail = errorResponse.getDetailErrors();
                log.info("Error detallado - Type: {}, Loc: {}, Msg: {}, CorrelationId: {}",
                        detail.getType(), detail.getLoc(), detail.getMsgError(), detail.getCorrelationId());

                String msgCode = detail.getMsgError() != null ? detail.getMsgError() : "Error desconocido";
                if (msgCode.equalsIgnoreCase(COR_PERSON_ERROR_CANCEL_KEY_NOEXIST_STATUS_CODE.getValue())) {
                    log.info("Error específico: La llave no existe");
                }

                updateOpenSearchService.saveIndexRejected(dynamoSpiDto, subject,
                        detail.getType() != null ? detail.getType() : "Error desconocido",
                        detail.getMsgError() != null ? detail.getMsgError() : "Descripción no disponible",
                        detail.getCorrelationId() != null ? detail.getCorrelationId() : rqId
                        );
            } else {
                log.error("Respuesta de error nula o sin detalles");
                updateOpenSearchService.saveIndexRejected(dynamoSpiDto, subject,
                        "Error desconocido", "Detalles no disponibles", rqId);
            }
        }
    }

    private void updateBatch(MessageDtoKeysCancel messageDtoKeysCancel, String rqID) {
        //Validacion de si Existen registros en indexbatch relacionados con la llave

        SearchTemplateResponse<HashMap> searchresponse = updateOpenSearchService
                .searchBatch(messageDtoKeysCancel.getKeyId());

        List<Hit<HashMap>> hits = searchresponse.hits().hits();

        long hitsvalue = searchresponse.hits().total().value();

        if (hitsvalue == 0) {

            log.info("Inicia guardado en index_batch");

            OSIndexBatch osIndexBatch = indexBatchMapper.mapCancelRqToIndexBatchMig
                    (messageDtoKeysCancel, rqID, ONLINE_CANCELLATION.getValue(),
                            BatchEnum.BATCH_CANCEL.getValue());

            updateOpenSearchService.addElement(osIndexBatch, SONDA_INDEX);
            log.info("Finaliza guardado en index_batch" + Util.object2String(osIndexBatch));

        } else {

            log.info("Inicia Actualizacion de registros status batch cancelado");

            updateOpenSearchService.keyProcessor(hits);
        }
    }

    private void cancelOpenSearch(String keyId, String keyType) {

        SearchTemplateResponse<HashMap> searchresponse = updateOpenSearchService
                .searchTemplateKey(keyId, keyType);

        List<Hit<HashMap>> hits = searchresponse.hits().hits();

        long hitsvalue = searchresponse.hits().total().value();


        if (hitsvalue != 0) {

            ObjectMapper objectMapper = new ObjectMapper();
            OSIndexKey osIndexKey = objectMapper.convertValue(hits.get(0).source(), OSIndexKey.class);
            log.info("OSINDEX KEY " + Util.object2String(osIndexKey));


            openSearchSynchService.openSearchSyncCancel(osIndexKey);

        } else {
            log.error("No existe el registro en Opensearch");
        }
    }
}
