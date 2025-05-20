package co.com.avc.service;
import co.com.ath.commons.util.ATHException;
import co.com.ath.commons.util.Util;
import co.com.ath.cornerconn.models.CornersHeadersRq;
import co.com.ath.cornerconn.service.cancellation.ICornerCancellationKeyService;
import co.com.avc.constants.ConstantsEnum;
import co.com.avc.constants.ResponseServiceEnum;
import co.com.avc.models.dynamoAth.DynamoSpiDto;
import co.com.avc.models.parameter.ParamVaultUpload;
import co.com.avc.models.parameter.VaultServicesTimeOut;
import co.com.ath.cornerconn.models.HttpResponseWrapper;
import co.com.avc.service.interfaces.IUpdateOpenSearchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * <p>
 * RedVaultService
 * <p>
 * Desarrollo ATH - Pruebas Unitarias
 * <p>
 * Creado el 29 de enero del 2025
 * <p>
 *
 * @author Miguel Angel Robles
 * @version 1.0
 * @since 1.0
 * <p>
 * Requerimiento: Pruebas Unitarias
 * <p>
 * Copyright © A toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproduccion y copia de manera parcial o permantente salvo autorizacion
 * espreso de A Toda Hora S.A o de quien represente sus derechos
 * <p>
 */

@Slf4j
@AllArgsConstructor
public class CorVaultService {

    /**
     * Instancia del método que consume la librería de corner.
     */
    private final ICornerCancellationKeyService corDeleteService;

    /**
     * Instancia del servicio de operaciones con opensearch
     */
    private final IUpdateOpenSearchService updateOpenSearchService;

    /**
     * Método encargado de consumir y recibir la respuesta de la cámara
     *
     * @param dynamoSpiDto Modelo de datos de dynamo
     * @param paramVaultUpload Modelo de datos de conexión de la cámara
     * @param vaultServicesTimeOut valor del timeout del servicio
     * @param headersRq headers de la petición

     * @param fileName Nombre del archivo
     * @return
     */
    public HttpResponseWrapper vaultService(DynamoSpiDto dynamoSpiDto, ParamVaultUpload paramVaultUpload,
                                            VaultServicesTimeOut vaultServicesTimeOut, CornersHeadersRq headersRq,
                                             String fileName, String rqId) {

        HttpResponseWrapper httpResponseWrapper = null;
        try {

            httpResponseWrapper = corDeleteService.deleteKey(dynamoSpiDto.getKey().getKeyId(),
                    headersRq,
                    //Aqui se usa el endpoint de la cámara Corner
                    paramVaultUpload.getUrlDeleteAcctVault(),
                    vaultServicesTimeOut.getRedDeleteTimeOut());
            log.info("HttpResponseWrapper: {}", Util.object2String(httpResponseWrapper));


        } catch (IOException | URISyntaxException | InterruptedException conExp) {
            log.info("Inicia guardado en index_rejected");

            //Se queman debido a que no se tiene para Corner

            updateOpenSearchService.saveIndexRejected(dynamoSpiDto, fileName, ConstantsEnum.ERROR_ATH_SERVICE.getValue(),
                    conExp.getMessage(),  rqId);
            throw new ATHException(ResponseServiceEnum.ERROR_TEC_EXCEPTION.getServerStatusCode(),
                    ResponseServiceEnum.ERROR_TEC_EXCEPTION.getStatusDesc() + conExp.getMessage(),
                    ResponseServiceEnum.ERROR_TEC_EXCEPTION.getStatusCode());
        }
        return httpResponseWrapper;
    }
}