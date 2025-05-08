package co.com.avc.mapper;

import co.com.ath.commons.util.Util;
import co.com.avc.constants.ConstantsEnum;
import co.com.avc.models.dynamo.DynamoSpiDto;
import co.com.ath.opensearch.logs.entity.index_batch.OSIndexBatch;
import co.com.ath.redebanconn.model.HeadersRq;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IndexBatchMapper {

    public OSIndexBatch mapEnrollmentRqToIndexBatch(DynamoSpiDto dynamoSpiDto,
                                                    HeadersRq headersRq,
                                                    String event,
                                                    String fileName) {

        OSIndexBatch osIndexBatch = new OSIndexBatch();
        osIndexBatch.setRqId(headersRq.getRequestId());
        osIndexBatch.setRqServiceObject(Util.object2String(dynamoSpiDto));
        osIndexBatch.setVaultNameRec(ConstantsEnum.VAULT_NAME.getValue());
        osIndexBatch.setVaultBatch(ConstantsEnum.VAULT_NAME.getValue());
        osIndexBatch.setEventBatch(event);
        osIndexBatch.setFileName(fileName);
        osIndexBatch.setEffDt(Util.createDate());
        osIndexBatch.setBankBatch(dynamoSpiDto.getAcctInfo().getBankId());
        osIndexBatch.setStatusBatch(ConstantsEnum.PENDING_BATCH.getValue());
        osIndexBatch.setBlockedBatch(ConstantsEnum.UNLOCK_BATCH.getValue());

        log.info("RqServiceObject: {}", osIndexBatch.getRqServiceObject());
        log.info("OSIndexBatch: {}", Util.object2StringWithNulls(osIndexBatch));

        return osIndexBatch;

    }

    public OSIndexBatch mapEnrollmentRqToIndexBatch(DynamoSpiDto dynamoSpiDto,
                                                    String rqUUID,
                                                    String event,
                                                    String fileName) {

        OSIndexBatch osIndexBatch = new OSIndexBatch();
        osIndexBatch.setRqId(rqUUID);
        osIndexBatch.setRqServiceObject(Util.object2String(dynamoSpiDto));
        osIndexBatch.setVaultNameRec(ConstantsEnum.VAULT_NAME.getValue());
        osIndexBatch.setVaultBatch(ConstantsEnum.VAULT_NAME.getValue());
        osIndexBatch.setEventBatch(event);
        osIndexBatch.setFileName(fileName);
        osIndexBatch.setEffDt(Util.createDate());
        osIndexBatch.setBankBatch(dynamoSpiDto.getAcctInfo().getBankId());
        osIndexBatch.setStatusBatch(ConstantsEnum.PENDING_BATCH.getValue());
        osIndexBatch.setBlockedBatch(ConstantsEnum.UNLOCK_BATCH.getValue());

        log.info("RqServiceObject: {}", osIndexBatch.getRqServiceObject());
        log.info("OSIndexBatch: {}", Util.object2StringWithNulls(osIndexBatch));

        return osIndexBatch;

    }

}
