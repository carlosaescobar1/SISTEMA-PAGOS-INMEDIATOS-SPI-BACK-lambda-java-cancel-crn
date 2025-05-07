package co.com.ath.models;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Introspected
@SerdeImport(MessageDto.class)
public class MessageDto {

    private MessageDtoBatch messageDtoBatch;

    private MessageDtoDynamo messageDtoDynamo;
}
