package co.com.avc.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Introspected
@SerdeImport(SqsDto.class)
public class SqsDto {

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Subject")
    private String subject;
}
