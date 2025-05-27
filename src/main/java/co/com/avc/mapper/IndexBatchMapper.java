package co.com.avc.mapper;

import co.com.ath.commons.util.Util;
import co.com.ath.redebanconn.model.HeadersRq;
import co.com.avc.LambdaHandler;
import co.com.avc.constants.BatchEnum;
import co.com.avc.constants.ConstantsEnum;
import co.com.avc.models.MessageDtoBatch;
import co.com.avc.models.MessageDtoKeysCancel;
import co.com.avc.models.dynamoAth.DynamoSpiDto;
import co.com.ath.opensearch.logs.entity.index_batch.OSIndexBatch;
import co.com.avc.cornerconn.models.CornersHeadersRq;
import lombok.extern.slf4j.Slf4j;


import static co.com.avc.constants.BatchEnum.BATCH_PENDING;
import static co.com.avc.constants.BatchEnum.BATCH_UNLOCK;
/**
 * IndexBatchMapper
 * <p>
 * Esta clase mapea datos de entrada (como DynamoSpiDto o MessageDtoKeysCancel) a objetos OSIndexBatch
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
@Slf4j
public class IndexBatchMapper {
        //Esto es Sonda
        public OSIndexBatch mapCancelRqToIndexBatch(DynamoSpiDto dynamoSpiDto,

                                                                                String rqID,
                                                                                String event,
                                                                                String fileName, MessageDtoBatch messageDto) {

            dynamoSpiDto.setEffDtModify(Util.createDate());
            OSIndexBatch osIndexBatch = new OSIndexBatch();
            osIndexBatch.setRqId(rqID);
            osIndexBatch.setRqServiceObject(Util.object2String(dynamoSpiDto));
            osIndexBatch.setVaultNameRec(ConstantsEnum.VAULT_NAME.getValue());
            osIndexBatch.setVaultBatch(ConstantsEnum.VAULT_NAME.getValue());
            osIndexBatch.setEventBatch(event);
            osIndexBatch.setFileName(fileName);
            osIndexBatch.setEffDt(Util.createDate());
            // osIndexBatch.setBankBatch(dynamoSpiDto.getAcctInfo().getBankId());
            osIndexBatch.setStatusBatch(BATCH_PENDING.getValue());
            osIndexBatch.setBlockedBatch(BATCH_UNLOCK.getValue());
            osIndexBatch.setRetryBatch(messageDto.getOsIndexBatch().getRetryBatch());
            log.info("OSIndexBatch: {}", Util.object2StringWithNulls(osIndexBatch));

            return osIndexBatch;

    }
    public OSIndexBatch mapCancelRqToIndexBatchMig(DynamoSpiDto dynamoSpiDto,
                                                    String rqID,
                                                    String event,
                                                    String fileName,String status) {

        dynamoSpiDto.setEffDtModify(Util.createDate());
        OSIndexBatch osIndexBatch = new OSIndexBatch();
        osIndexBatch.setRqId(rqID);
        osIndexBatch.setRqServiceObject(Util.object2String(dynamoSpiDto));
        osIndexBatch.setVaultNameRec(ConstantsEnum.VAULT_NAME.getValue());
        osIndexBatch.setVaultBatch(ConstantsEnum.VAULT_NAME.getValue());
        osIndexBatch.setEventBatch(event);
        osIndexBatch.setFileName(fileName);
        osIndexBatch.setEffDt(Util.createDate());
       //osIndexBatch.setBankBatch(dynamoSpiDto.getAcctInfo().getBankId());
        osIndexBatch.setStatusBatch(status);
        osIndexBatch.setBlockedBatch(BatchEnum.BATCH_UNLOCK.getValue());

        log.info("OSIndexBatch: {}", Util.object2StringWithNulls(osIndexBatch));

        return osIndexBatch;

    }

    public OSIndexBatch mapCancelRqToIndexBatchMig(MessageDtoKeysCancel messageDtoKeysCancel,
                                                   String rqID,
                                                   String event,
                                                   String status) {
           // dynamoSpiDto.setEffDtModify(Util.createDate());
        OSIndexBatch osIndexBatch = new OSIndexBatch();
        osIndexBatch.setRqId(rqID);
        osIndexBatch.setRqServiceObject(Util.object2String(messageDtoKeysCancel));
        osIndexBatch.setVaultNameRec(ConstantsEnum.VAULT_NAME.getValue());
        osIndexBatch.setVaultBatch(ConstantsEnum.VAULT_NAME.getValue());
        osIndexBatch.setEventBatch(event);
        osIndexBatch.setFileName(messageDtoKeysCancel.getFileName());
        osIndexBatch.setEffDt(Util.createDate());
       // osIndexBatch.setBankBatch(dynamoSpiDto.getAcctInfo().getBankId());
        osIndexBatch.setStatusBatch(status);
        osIndexBatch.setBlockedBatch(BatchEnum.BATCH_BLOCK.getValue());

        log.info("OSIndexBatch: {}", Util.object2StringWithNulls(osIndexBatch));

        return osIndexBatch;

    }

}
