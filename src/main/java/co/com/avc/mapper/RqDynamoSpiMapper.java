package co.com.avc.mapper;
import co.com.ath.commons.util.Util;
import co.com.ath.cornerconn.models.Key;
import co.com.ath.cornerconn.models.PaymentMethod;
import co.com.ath.cornerconn.models.Person;
import co.com.avc.constants.ConstantsEnum;
import co.com.avc.models.MessageDtoBatch;
import co.com.avc.models.MessageDtoDynamo;
import co.com.avc.models.MessageDtoKeysCancel;
import co.com.avc.models.dynamoAth.DynamoSpiDto;
import co.com.avc.models.dynamoAth.KeyDto;
import co.com.avc.models.dynamoCorner.PaymentMethodDto;
import co.com.avc.models.dynamoCorner.PersonDto;
import lombok.extern.slf4j.Slf4j;

/**
 * Mapper para convertir diferentes tipos de mensajes de entrada en objetos {@link DynamoSpiDto} utilizados
 * para interactuar con DynamoDB en el proceso de cancelación de llaves bancarias en el sistema de A Toda Hora (ATH).
 *
 * @author [Tu Nombre]
 * @version 1.0
 * @since 2024
 */
@Slf4j
public class RqDynamoSpiMapper {

    /**
     * Convierte un objeto {@link MessageDtoBatch} que representa un lote de llaves en un {@link DynamoSpiDto}
     * para su almacenamiento o actualización en DynamoDB.
     *
     * @param messageDto Objeto que contiene un lote de llaves con un campo JSON que representa un {@link DynamoSpiDto}.
     * @return Un objeto {@link DynamoSpiDto} con los datos deserializados y campos adicionales configurados.
     */
    public DynamoSpiDto redirectDynamoBatch(MessageDtoBatch messageDto) {
        DynamoSpiDto dynamoSpiDto = (DynamoSpiDto) Util.string2objectWhitNulls(
                messageDto.getOsIndexBatch().getRqServiceObject(),
                DynamoSpiDto.class);
        dynamoSpiDto.setVaultNameRec(ConstantsEnum.REDEBAN.getValue());
        dynamoSpiDto.setEffDtModify(Util.createDate());
        log.info("redirectDynamoData: {}", Util.object2StringWithNulls(dynamoSpiDto));
        //lLEGA
        return dynamoSpiDto;
    }

    /**
     * Convierte un objeto {@link MessageDtoDynamo} que contiene un {@link DynamoSpiDto} predefinido en un
     * {@link DynamoSpiDto} preparado para su uso en DynamoDB, utilizado en flujos de migración.
     *
     * @param messageDto Objeto que incluye un {@link DynamoSpiDto} con datos de una llave bancaria.
     * @return Un objeto {@link DynamoSpiDto} con campos adicionales configurados.
     */
    public DynamoSpiDto redirectDynamoMig(MessageDtoDynamo messageDto) {
        DynamoSpiDto dynamoSpiDto = messageDto.getDynamoSpiDto();
        dynamoSpiDto.setVaultNameRec(ConstantsEnum.CORNER.getValue());
        dynamoSpiDto.setEffDtModify(Util.createDate());
        log.info("redirectDynamoData: {}", Util.object2StringWithNulls(dynamoSpiDto));
        return dynamoSpiDto;
    }

    /**
     * Construye un objeto {@link DynamoSpiDto} a partir de un {@link MessageDtoKeysCancel} que contiene
     * información mínima de una llave a cancelar, utilizado en escenarios de contingencia.
     *
     * @param messageDtoKeysCancel Objeto con datos básicos de la llave, como ID, y datos del cliente, para Corner.
     * @return Un objeto {@link DynamoSpiDto} completamente poblado para su uso en DynamoDB.
     */
    public DynamoSpiDto redirectDynamoCnt(MessageDtoKeysCancel messageDtoKeysCancel) {
        DynamoSpiDto dynamoSpiDto = new DynamoSpiDto();
        KeyDto key = new KeyDto();
        // Como solo se necesita el ID de la llave, se usa tanto como ID como SK
        dynamoSpiDto.setId(messageDtoKeysCancel.getKeyId());
        dynamoSpiDto.setSk(messageDtoKeysCancel.getKeyId());
        dynamoSpiDto.setEffDtModify(Util.createDate());

        //Key_value
        key.setKeyId(messageDtoKeysCancel.getKeyId());
        dynamoSpiDto.setKey(key);
        log.info("redirectDynamoCnt: {}", Util.object2StringWithNulls(dynamoSpiDto));
        //Los demas datos seran nulos
        return dynamoSpiDto;
    }

}
