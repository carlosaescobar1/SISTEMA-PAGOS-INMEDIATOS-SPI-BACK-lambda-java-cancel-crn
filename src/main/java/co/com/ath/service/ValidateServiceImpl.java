package co.com.ath.service;

import co.com.ath.commons.util.ATHException;
import co.com.ath.constants.ResponseServiceEnum;
import co.com.ath.models.dynamo.DynamoSpiDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ValidateServiceImpl implements IValidateService {

    /**
     * Instancia del servicio de sincronización de opensearch
     */
    private final IOpenSearchSynchService openSearchSynchService;

    /**
     * Instancia del servicio de operaciones en opensearch
     */
    private final IUpdateOpenSearchService updateOpenSearchService;

    /**
     * Método que hace la validación de las listas de llaves no
     * permitidas por las entidades
     *
     * @param dynamoSpiDto Datos del modelo de dynamo
     * @param fileName     Nombre del archivo
     * @param rqId         Id de la petición
     * @param rqUUID       UUID de la petición
     */
    @Override
    public boolean validateBannedKeys(DynamoSpiDto dynamoSpiDto, String fileName, String rqId, String rqUUID) {
        try {
            openSearchSynchService.openSearchSyncBlackListValidate(dynamoSpiDto.getKey().getKeyId());
            log.info("La llave paso la validación de las llaves no permitidas");
            return true;
        } catch (ATHException athException) {
            log.info("La llave a crear no es permitida");
            updateOpenSearchService.saveIndexRejected(dynamoSpiDto, fileName,
                    ResponseServiceEnum.ERROR_VALIDATE_BANNED_KEYS.getServerStatusCode(),
                    ResponseServiceEnum.ERROR_VALIDATE_BANNED_KEYS.getAdditionalStatusDesc(),
                    rqId, rqUUID);
            return false;
        }
    }
}
