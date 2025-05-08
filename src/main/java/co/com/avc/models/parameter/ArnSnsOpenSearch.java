package co.com.avc.models.parameter;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Introspected
@SerdeImport(ArnSnsOpenSearch.class)
public class ArnSnsOpenSearch {

    private String arnSnsOpenSearchBAVV;
    private String arnSnsOpenSearchBBOG;
    private String arnSnsOpenSearchBOCC;
    private String arnSnsOpenSearchBPOP;
    private String arnSnsOpenSearchDALE;

}
