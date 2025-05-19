package co.com.avc.mapper;
import co.com.ath.commons.util.Util;
import co.com.avc.models.MessageDtoDynamo;
import co.com.avc.models.MessageDtoKeysCancel;
import co.com.avc.models.SqsDto;
import co.com.avc.models.dynamoAth.DynamoSpiDto;
import co.com.ath.redebanconn.model.delete.DeleteRq;
import lombok.extern.slf4j.Slf4j;
/**
 * Mapper para convertir mensajes SQS en objetos {@link MessageDtoDynamo} o {@link MessageDtoKeysCancel}
 * utilizados en flujos de migración o contingencia para la cancelación de llaves bancarias en el sistema
 * de A Toda Hora (ATH).
 *
 * @author [Tu Nombre]
 * @version 1.0
 * @since 2024
 */
@Slf4j
public class RqMigrationMapper {

    /**
     * Convierte un mensaje SQS en un objeto {@link MessageDtoDynamo} para flujos de migración, deserializando
     * el contenido del mensaje en un {@link DynamoSpiDto} y asignando el nombre del archivo desde el asunto.
     *
     * @param sqsDto Objeto que contiene el mensaje SQS con un JSON que representa un {@link DynamoSpiDto}.
     * @return Un objeto {@link MessageDtoDynamo} con el {@link DynamoSpiDto} y el nombre del archivo.
     */
    public MessageDtoDynamo messageDtoMapper(SqsDto sqsDto) {
        log.info("Proceso proviene de Migracion");
        MessageDtoDynamo messageDtoDynamo = new MessageDtoDynamo();
        DynamoSpiDto dynamoSpiDto = (DynamoSpiDto) Util.string2objectWhitNulls(sqsDto.getMessage(), DynamoSpiDto.class);
        messageDtoDynamo.setDynamoSpiDto(dynamoSpiDto);
        messageDtoDynamo.setFileName(sqsDto.getSubject());
        log.info("messageDtoMapper: {}", Util.object2StringWithNulls(messageDtoDynamo));
        return messageDtoDynamo;
    }

    /**
     * Convierte un mensaje SQS en un objeto {@link MessageDtoKeysCancel} para flujos de contingencia,
     * deserializando el contenido del mensaje y asignando el nombre del archivo desde el asunto.
     *
     * @param sqsDto Objeto que contiene el mensaje SQS con un JSON que representa un {@link MessageDtoKeysCancel}.
     * @return Un objeto {@link MessageDtoKeysCancel} con los datos deserializados y el nombre del archivo.
     */
    public MessageDtoKeysCancel messageDtoMapperCancel(SqsDto sqsDto) {
        log.info("Proceso proviene de Migracion Contingencia");
        MessageDtoKeysCancel messageDtoKeysCancel = (MessageDtoKeysCancel)
                Util.string2objectWhitNulls(sqsDto.getMessage(), MessageDtoKeysCancel.class);
        messageDtoKeysCancel.setFileName(sqsDto.getSubject());
        log.info("MessageDtoKeysCancel: {}", Util.object2StringWithNulls(messageDtoKeysCancel));
        return messageDtoKeysCancel;
    }
}
