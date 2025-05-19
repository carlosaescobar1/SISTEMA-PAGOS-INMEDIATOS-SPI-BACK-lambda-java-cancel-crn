package co.com.avc.mapper;

import co.com.ath.commons.util.Util;
import co.com.avc.models.dynamoAth.DynamoSpiDto;
import co.com.ath.opensearch.logs.constants.PhaseEnum;
import co.com.ath.opensearch.logs.entity.index_rejected.OSIndexRejected;
/**
 * IndexRejectedMapper
 * <p>
 * mapea datos de entrada, como DynamoSpiDto, a un objeto
 * OSIndexRejected para registrar información sobre transacciones o registros rechazados.
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
public class IndexRejectedMapper {

    private final OSIndexRejected osIndexRejected = new OSIndexRejected();

    public OSIndexRejected indexRejected(DynamoSpiDto dynamoSpiDto, String fileName, String errorType, String errorDesc, String rqId) {

        osIndexRejected.setFileName(fileName);
        osIndexRejected.setBankId(dynamoSpiDto.getAcctInfo().getBankId());
        osIndexRejected.setKey(dynamoSpiDto.getKey().getKeyId());
        osIndexRejected.setRqId(rqId);
        osIndexRejected.setErrorType(errorType);
        osIndexRejected.setErrorDesc(errorDesc);
        osIndexRejected.setErrorDate(Util.createDate());
        osIndexRejected.setPhase(PhaseEnum.SYNCHRONIZATION.getValue());

        return osIndexRejected;
    }

}
