package co.com.avc.mapper;
import co.com.ath.commons.util.Util;
import co.com.avc.constants.ConstantsEnum;
import co.com.avc.models.dynamoAth.DynamoSpiDto;
import co.com.ath.redebanconn.model.delete.DeleteRq;
/**
 * Mapper para crear solicitudes de cancelación de llaves bancarias ({@link DeleteRq}) a partir de datos de
 * {@link DynamoSpiDto} o información mínima de identificación en el sistema de A Toda Hora (ATH).
 *
 * @author [Tu Nombre]
 * @version 1.0
 * @since 2024
 */
public class RqDeleteMapper {

    /**
     * Construye una solicitud de cancelación ({@link DeleteRq}) a partir de un objeto {@link DynamoSpiDto}
     * que contiene información de una llave bancaria almacenada en DynamoDB.
     *
     * @param dynamoSpiDto Objeto con datos de la llave, incluyendo información del cliente y su identificación.
     * @return Un objeto {@link DeleteRq} con los campos necesarios para solicitar la cancelación de la llave.
     */
    public DeleteRq rqDynamoMapper(DynamoSpiDto dynamoSpiDto) {
        DeleteRq deleteRq = new DeleteRq();
        deleteRq.setRequestDateTime(Util.createDate());
        deleteRq.setKeyStatus(ConstantsEnum.CANCELADA.getValue());
        deleteRq.setDocumentType(dynamoSpiDto.getCustInf().getCustIdent().getCustIdentType());
        deleteRq.setDocumentNumber(dynamoSpiDto.getCustInf().getCustIdent().getCustIdentNum());
        return deleteRq;
    }

    /**
     * Construye una solicitud de cancelación ({@link DeleteRq}) a partir de un tipo y número de documento,
     * utilizada en escenarios de contingencia donde los datos no provienen de DynamoDB.
     *
     * @param documentType   Tipo de documento del cliente (por ejemplo, cédula, NIT).
     * @param documentNumber Número de documento del cliente.
     * @return Un objeto {@link DeleteRq} con los campos necesarios para solicitar la cancelación.
     */
    public DeleteRq rqCntMapper(String documentType, String documentNumber) {
        DeleteRq deleteRq = new DeleteRq();
        deleteRq.setRequestDateTime(Util.createDate());
        deleteRq.setKeyStatus(ConstantsEnum.CANCELADA.getValue());
        deleteRq.setDocumentType(documentType);
        deleteRq.setDocumentNumber(documentNumber);
        return deleteRq;
    }
}
