package co.com.avc.service.interfaces;

import co.com.avc.models.MessageDtoBatch;
import co.com.avc.models.dynamoAth.DynamoSpiDto;
import co.com.avc.models.parameter.ParameterStoreDto;

import java.io.IOException;

public interface ICorCancelBatchTransvService {

    void corCancelBatchService(MessageDtoBatch messageDtoBatch, String subject,
                               DynamoSpiDto dynamoSpiDto, String rqId,
                               ParameterStoreDto parameterStoreDto)
            throws IOException;

}
