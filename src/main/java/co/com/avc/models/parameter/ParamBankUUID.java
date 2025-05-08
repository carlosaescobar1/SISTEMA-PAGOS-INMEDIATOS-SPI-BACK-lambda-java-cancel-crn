package co.com.avc.models.parameter;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Introspected
@SerdeImport(ParamBankUUID.class)
public class ParamBankUUID {

    @JsonProperty("BAVV")
    private String bavv;

    @JsonProperty("BBOG")
    private String bbog;

    @JsonProperty("BOCC")
    private String bocc;

    @JsonProperty("BPOP")
    private String bpop;

    @JsonProperty("DALE")
    private String dale;

}
