package co.com.avc.service.interfaces;

import co.com.avc.models.dynamoAth.DynamoSpiDto;

import java.io.IOException;

public interface ICorCancelMigTransvService {

    void corCancelMigService(String subject,
                             DynamoSpiDto dynamoSpiDto, String rqId,
                             String fileName) throws IOException;

}
