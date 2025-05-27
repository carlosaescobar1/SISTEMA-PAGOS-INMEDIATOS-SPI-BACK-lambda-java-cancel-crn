package co.com.avc.service;
import co.com.ath.commons.util.Util;
import co.com.avc.cornerconn.models.MsgErrors;
import co.com.avc.constants.BatchEnum;
import co.com.avc.entity.Ath.DynamoSpiEntity;
import co.com.avc.mapper.*;
import co.com.avc.models.MsgInformationResponseSuccess;
import co.com.avc.models.dynamoAth.DynamoSpiDto;
import co.com.avc.models.parameter.ParamFlowConfig;
import co.com.avc.models.parameter.ParamVaultUpload;
import co.com.avc.models.parameter.VaultServicesTimeOut;
import co.com.ath.opensearch.logs.entity.index_batch.OSIndexBatch;
import co.com.avc.cornerconn.models.HttpResponseWrapper;
import co.com.avc.cornerconn.models.MsgInformationResponse;
import co.com.avc.cornerconn.service.cancellation.ICornerCancellationKeyService;
import co.com.avc.cornerconn.service.cancellation.CornerCancellationKeyServiceImpl;
import co.com.avc.repository.DynamoRepository;
import co.com.avc.service.interfaces.IOpenSearchSynchService;
import co.com.avc.service.interfaces.ICorCancelMigTransvService;
import co.com.avc.service.interfaces.IUpdateOpenSearchService;
import co.com.avc.util.TimeLineUtil;
import co.com.avc.util.VaultSelectorUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


import static co.com.avc.constants.ResponseStatusCodeEnum.PERSON_SUCCESS_STATUS_CODE;
import static co.com.ath.opensearch.logs.constants.ActionConstants.EVENT_BATCH_VAULT_SYNC;
import static co.com.ath.opensearch.logs.constants.IndexConstants.SONDA_INDEX;
/**
 * CorCancelMigTransvServiceImpl
 * <p>
 * 
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
public class CorCancelMigTransvServiceImpl implements ICorCancelMigTransvService {

    /**
     * Interfaz del servicio de caneclacion de corner
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
     * Modelo que contiene los datos necesarios para conectarse a las cámaras
     */
    private final ParamVaultUpload paramVaultUpload;

    @Override
    public void corCancelMigService(String subject, DynamoSpiDto dynamoSpiDto, String rqId, String fileName) throws IOException {
        CorVaultService corVaultService = new CorVaultService(
                corDeleteService,
                updateOpenSearchService
        );

        // Para enviar la solicitud de cancelación a la cámara
        HttpResponseWrapper httpResponseWrapper = corVaultService.vaultService(
                dynamoSpiDto, paramVaultUpload, vaultServicesTimeOut, null, subject, rqId);

        // Deserializar la respuesta según el caso
        Object responseObject;
        if (httpResponseWrapper.getStatusCode() == PERSON_SUCCESS_STATUS_CODE.getValue()) {
            responseObject = Util.string2object(httpResponseWrapper.getResponseBody(), MsgInformationResponseSuccess.class);
        } else {
            responseObject = Util.string2object(httpResponseWrapper.getResponseBody(), MsgErrors.class);
        }

        if (httpResponseWrapper.getStatusCode() == PERSON_SUCCESS_STATUS_CODE.getValue() ||
                ((MsgInformationResponseSuccess) responseObject).getValue_key() != null && !((MsgInformationResponseSuccess) responseObject).getValue_key().isEmpty()) {
            log.info("Respuesta del servicio exitosa: {}", Util.object2String(responseObject));

            DynamoSpiEntity dynamoSpiEntity = IDynamoMapper.INSTANCE.dynamoDtoToDynamoEntity(dynamoSpiDto);

            // Verificar y eliminar registro en DynamoDB si existe
            if (dynamoRepository.load(dynamoSpiEntity.getId(), dynamoSpiEntity.getSk()) != null) {
                dynamoRepository.delete(dynamoSpiEntity);
            } else {
                log.error("No existe el registro en Dynamo");
            }

            // Verificar y sincronizar cancelación en OpenSearch
            if (updateOpenSearchService.searchKey(dynamoSpiDto.getKey().getKeyId(),
                    dynamoSpiDto.getKey().getKeyType()) != 0) {
                openSearchSynchService.openSearchSyncCancel(dynamoSpiEntity);
            } else {
                log.error("No existe el registro en OpenSearch");
            }

            // Validar y actualizar o crear registro en index_batch
            if (updateOpenSearchService.searchBatch(dynamoSpiDto) == 0) {
                log.info("Inicia guardado en index_batch");
                OSIndexBatch osIndexBatch = indexBatchMapper.mapCancelRqToIndexBatchMig(
                        dynamoSpiDto, rqId, EVENT_BATCH_VAULT_SYNC.getValue(),
                        fileName, BatchEnum.BATCH_CANCEL.getValue()
                );
                updateOpenSearchService.addElement(osIndexBatch, SONDA_INDEX);
                log.info("Finaliza guardado en index_batch: {}", Util.object2String(osIndexBatch));
            } else {
                log.info("Inicia actualización de registros status batch cancelado");
            }
        } else {
            MsgErrors errorResponse = (MsgErrors) responseObject;
            log.info("Respuesta del servicio en cámara Corner no fue exitosa: {}", Util.object2String(errorResponse));
            updateOpenSearchService.saveIndexRejected(dynamoSpiDto, subject,
                    errorResponse.getDetailErrors() != null ? errorResponse.getDetailErrors().getType() : "Error desconocido",
                    errorResponse.getDetailErrors() != null ? errorResponse.getDetailErrors().getMsgError() : "No hay descripción",
                    rqId); // Usamos rqId como rqUID y requestId
        }
    }
}
