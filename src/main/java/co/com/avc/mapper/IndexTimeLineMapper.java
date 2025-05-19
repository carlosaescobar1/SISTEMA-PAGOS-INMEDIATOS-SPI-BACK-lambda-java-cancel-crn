package co.com.avc.mapper;

import co.com.ath.commons.util.Util;
import co.com.avc.constants.ConstantsEnum;
import co.com.avc.models.dynamoAth.DynamoSpiDto;
import co.com.ath.opensearch.logs.constants.TypeServiceConstants;
import co.com.ath.opensearch.logs.entity.index_timeline.OSIndexTimeline;
import co.com.ath.redebanconn.model.Customer;
import co.com.ath.redebanconn.model.HeadersRq;
import co.com.ath.redebanconn.model.enrollment.EnrollmentRq;
import co.com.avc.util.IpSelectorUtil;
/**
 * IndexTimeLineMapper
 * <p>
 * Esta clase mapea un objeto DynamoSpiDto a un objeto OSIndexTimeline
 * para registrar información en el índice index_timeline
 * <p>
 * Desarrollo ATH - SPBVI
 * <p>
 * Creado él: 09 de septiembre de 2024
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
 */
public class IndexTimeLineMapper {

    /**
     * Instancia del objeto que representa el indice index_timeline de Open search
     */
    private OSIndexTimeline osIndexTimeline = new OSIndexTimeline();

    private final IpSelectorUtil ipSelectorUtil;

    public IndexTimeLineMapper(IpSelectorUtil ipSelectorUtil) {
        this.ipSelectorUtil = ipSelectorUtil;
    }


    /**
     * Método que mapea el objeto de entrada de la solicitud de registro (Enrollment)
     * al objeto que representa el índice de time_line en Open search.
     *
     *  que representa el request de la
     *                     petición de creación en línea.
     * que representa el tipo de log que se quiere guardar
     *                     Request (Rq) o Response (Rs) usado para asignar la fecha
     *                     de la operación.
     * @return Objeto OSIndexTimeline con la información mapeada.
     */

    public OSIndexTimeline mapDynamoToTimeLine(DynamoSpiDto dynamoSpiDto, String rqUUID) {

        osIndexTimeline.setRqId(rqUUID);
        osIndexTimeline.setKeyType(getKeyType(dynamoSpiDto));
        osIndexTimeline.setKeyId(getKeyId(dynamoSpiDto));
        osIndexTimeline.setDateOperation(Util.createDate());
        osIndexTimeline.setCompanyId(getCompanyId(dynamoSpiDto));
        osIndexTimeline.setBankId(getBankId(dynamoSpiDto));
        osIndexTimeline.setChanel(getChanel());
        osIndexTimeline.setIp(getIp(dynamoSpiDto));

        return osIndexTimeline;
    }


    private String getKeyType(DynamoSpiDto dynamoSpiDto) {
        return dynamoSpiDto != null
                && dynamoSpiDto.getKey() != null
                && dynamoSpiDto.getKey().getKeyType() != null
                ? dynamoSpiDto.getKey().getKeyType()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }

    private String getKeyId(DynamoSpiDto dynamoSpiDto) {
        return dynamoSpiDto != null
                && dynamoSpiDto.getKey() != null
                && dynamoSpiDto.getKey().getKeyId() != null
                ? dynamoSpiDto.getKey().getKeyId()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }

    private String getCompanyId(DynamoSpiDto dynamoSpiDto) {
        return dynamoSpiDto != null
                && dynamoSpiDto.getAcctInfo() != null
                && dynamoSpiDto.getAcctInfo().getBankId() != null
                ? dynamoSpiDto.getAcctInfo().getBankId()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }

    private String getBankId(DynamoSpiDto dynamoSpiDto) {
        return dynamoSpiDto != null
                && dynamoSpiDto.getAcctInfo() != null
                && dynamoSpiDto.getAcctInfo().getBankId() != null
                ? dynamoSpiDto.getAcctInfo().getBankId()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }


    private String getChanel() {
        return ConstantsEnum.HEADER_CHANNEL.getValue();
    }

    private String getIp(DynamoSpiDto dynamoSpiDto) {
        return dynamoSpiDto != null
                && dynamoSpiDto.getAcctInfo() != null
                && dynamoSpiDto.getAcctInfo().getBankId() != null
                && !dynamoSpiDto.getAcctInfo().getBankId().trim().isEmpty()
                ? ipSelectorUtil.getIp(dynamoSpiDto.getAcctInfo().getBankId())
                : ConstantsEnum.EMPTY_STRING.getValue();
    }

}
