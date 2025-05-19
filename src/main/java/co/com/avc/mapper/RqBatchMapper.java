package co.com.avc.mapper;
import co.com.ath.commons.util.Util;
import co.com.avc.models.MessageDtoBatch;
import co.com.avc.models.SqsDto;
import lombok.extern.slf4j.Slf4j;

/**
 * Mapper para convertir mensajes SQS en objetos {@link MessageDtoBatch} utilizados en el procesamiento de lotes
 * de cancelación de llaves bancarias en el sistema de A Toda Hora (ATH).
 *
 * @author [Tu Nombre]
 * @version 1.0
 * @since 2024
 */
@Slf4j
public class RqBatchMapper {

    /**
     * Convierte un mensaje SQS en un objeto {@link MessageDtoBatch} para procesar un lote de llaves a cancelar.
     * Deserializa el campo de mensaje del {@link SqsDto} en un objeto {@link MessageDtoBatch} manejando valores nulos.
     *
     * @param sqsDto Objeto que contiene el mensaje SQS con los datos del lote en formato JSON.
     * @return Un objeto {@link MessageDtoBatch} con los datos del lote deserializados.
     */
    public MessageDtoBatch messageDtoMapper(SqsDto sqsDto) {
        log.info("Proceso proviene de sonda");
        MessageDtoBatch messageDtoBatch = (MessageDtoBatch) Util.string2objectWhitNulls(sqsDto.getMessage(), MessageDtoBatch.class);
        log.info("messageDtoMapper: {}", Util.object2StringWithNulls(messageDtoBatch));
        return messageDtoBatch;
    }
}