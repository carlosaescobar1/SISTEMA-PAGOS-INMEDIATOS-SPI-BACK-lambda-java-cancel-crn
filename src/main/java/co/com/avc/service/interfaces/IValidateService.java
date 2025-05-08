package co.com.avc.service.interfaces;

import co.com.avc.models.dynamo.DynamoSpiDto;

public interface IValidateService {

    boolean validateBannedKeys(DynamoSpiDto dynamoSpiDto, String fileName, String rqId, String rqUUID);
}
