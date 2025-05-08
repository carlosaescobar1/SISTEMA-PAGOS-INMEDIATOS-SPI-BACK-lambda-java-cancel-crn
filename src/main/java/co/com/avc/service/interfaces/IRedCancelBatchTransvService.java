package co.com.avc.service.interfaces;

import co.com.avc.models.MessageDtoBatch;
import co.com.avc.models.dynamo.DynamoSpiDto;
import co.com.avc.models.parameter.ParameterStoreDto;

import java.io.IOException;

public interface IRedCancelBatchTransvService {

    void redCancelBatchService(MessageDtoBatch messageDtoBatch, String subject,
                               DynamoSpiDto dynamoSpiDto, String rqId,
                               String rqUUID, ParameterStoreDto parameterStoreDto)
            throws IOException;

}
