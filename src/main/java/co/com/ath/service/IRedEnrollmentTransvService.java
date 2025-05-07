package co.com.ath.service;

import co.com.ath.models.MessageDto;
import co.com.ath.models.dynamo.DynamoSpiDto;

public interface IRedEnrollmentTransvService {

    void redEnrollService(MessageDto messageDto, String subject, DynamoSpiDto dynamoSpiDto, String rqID, String dateOperation, String rqUUID);
}
