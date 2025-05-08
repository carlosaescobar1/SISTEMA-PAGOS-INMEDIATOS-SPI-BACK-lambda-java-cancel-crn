package co.com.avc.models;

import co.com.avc.models.dynamo.DynamoSpiDto;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Introspected
@SerdeImport(MessageDtoDynamo.class)
public class MessageDtoDynamo {

    private DynamoSpiDto dynamoSpiDto;

    private String fileName;

}
