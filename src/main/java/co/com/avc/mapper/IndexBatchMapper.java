package co.com.avc.mapper;

import co.com.ath.commons.util.Util;
import co.com.avc.constants.BatchEnum;
import co.com.avc.constants.ConstantsEnum;
import co.com.avc.models.MessageDtoBatch;
import co.com.avc.models.MessageDtoKeysCancel;
import co.com.avc.models.dynamo.DynamoSpiDto;
import co.com.ath.opensearch.logs.entity.index_batch.OSIndexBatch;
import co.com.ath.cornerconn.models.CornersHeadersRq;
import lombok.extern.slf4j.Slf4j;

import static co.com.ath.constants.BatchEnum.BATCH_PENDING;
import static co.com.ath.constants.BatchEnum.BATCH_UNLOCK;

@Slf4j
public class IndexBatchMapper {

    public OSIndexBatch mapCancelRqToIndexBatch(DynamoSpiDto dynamoSpiDto,
                                                CornersHeadersRq headersRq,
                                                String event,
                                                String fileName, MessageDtoBatch messageDto) {


        OSIndexBatch osIndexBatch = new OSIndexBatch();
        osIndexBatch.setRqServiceObject(Util.object2String(dynamoSpiDto));
        // Tener encuenta que el Enum VAULT_NAME es de camara REDEBAN
        osIndexBatch.setVaultNameRec(ConstantsEnum.VAULT_NAME.getValue());
        osIndexBatch.setVaultBatch(ConstantsEnum.VAULT_NAME.getValue());
        osIndexBatch.setEventBatch(event);
        osIndexBatch.setFileName(fileName);
        osIndexBatch.setStatusBatch(BATCH_PENDING.getValue());
        osIndexBatch.setBlockedBatch(BATCH_UNLOCK.getValue());
        osIndexBatch.setRetryBatch(messageDto.getOsIndexBatch().getRetryBatch());
        log.info("OSIndexBatch: {}", Util.object2StringWithNulls(osIndexBatch));

        return osIndexBatch;

    }

    public OSIndexBatch mapCancelRqToIndexBatchMig(DynamoSpiDto dynamoSpiDto,
                                                    String event,
                                                    String fileName) {

        OSIndexBatch osIndexBatch = new OSIndexBatch();
        osIndexBatch.setRqServiceObject(Util.object2String(dynamoSpiDto));
        osIndexBatch.setVaultNameRec(ConstantsEnum.VAULT_NAME.getValue());
        osIndexBatch.setVaultBatch(ConstantsEnum.VAULT_NAME.getValue());
        osIndexBatch.setEventBatch(event);
        osIndexBatch.setFileName(fileName);
        osIndexBatch.setEffDt(Util.createDate());
        osIndexBatch.setStatusBatch(ConstantsEnum.PENDING_BATCH.getValue());
        osIndexBatch.setBlockedBatch(ConstantsEnum.UNLOCK_BATCH.getValue());

        log.info("RqServiceObject: {}", osIndexBatch.getRqServiceObject());
        log.info("OSIndexBatch: {}", Util.object2StringWithNulls(osIndexBatch));

        return osIndexBatch;

    }

    public OSIndexBatch mapCancelRqToIndexBatchMig(MessageDtoKeysCancel messageDtoKeysCancel,
                                                   String event,
                                                   String status) {

        // dynamoSpiDto.setEffDtModify(Util.createDate());
        OSIndexBatch osIndexBatch = new OSIndexBatch();
        //osIndexBatch.setRqServiceObject(Util.object2String(messageDtoKeysCancel));
        osIndexBatch.setVaultNameRec(ConstantsEnum.VAULT_NAME.getValue());
        osIndexBatch.setVaultBatch(ConstantsEnum.VAULT_NAME.getValue());
        osIndexBatch.setEventBatch(event);
        osIndexBatch.setFileName(messageDtoKeysCancel.getFileName());
        osIndexBatch.setEffDt(Util.createDate());
        //osIndexBatch.setBankBatch(dynamoSpiDto.getAcctInfo().getBankId());
        osIndexBatch.setStatusBatch(status);
        osIndexBatch.setBlockedBatch(BatchEnum.BATCH_BLOCK.getValue());

        log.info("OSIndexBatch: {}", Util.object2StringWithNulls(osIndexBatch));

        return osIndexBatch;

    }

}
