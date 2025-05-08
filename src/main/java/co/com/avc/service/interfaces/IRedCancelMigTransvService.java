package co.com.avc.service.interfaces;

import co.com.avc.models.dynamo.DynamoSpiDto;

import java.io.IOException;

public interface IRedCancelMigTransvService {

    void redCancelMigService(String subject,
                             DynamoSpiDto dynamoSpiDto, String rqId,
                             String rqUUID, String fileName) throws IOException;

}
