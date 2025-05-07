package co.com.ath.service;

import co.com.ath.models.dynamo.DynamoSpiDto;

public interface IValidateService {

    boolean validateBannedKeys(DynamoSpiDto dynamoSpiDto, String fileName, String rqId, String rqUUID);
}
