package co.com.avc.models.parameter;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Introspected
@SerdeImport(ParamIp.class)
public class ParamIp {

    @JsonProperty("ipBAVV")
    private String bavv;

    @JsonProperty("ipBBOG")
    private String bbog;

    @JsonProperty("ipBOCC")
    private String bocc;

    @JsonProperty("ipBPOP")
    private String bpop;

    @JsonProperty("ipDALE")
    private String dale;

}
