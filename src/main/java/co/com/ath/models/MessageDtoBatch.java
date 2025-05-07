package co.com.ath.models;

import co.com.ath.opensearch.logs.entity.index_batch.OSIndexBatch;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Introspected
@SerdeImport(MessageDtoBatch.class)
public class MessageDtoBatch {

    @JsonProperty("id")
    private String idOpensearch;

    @JsonProperty("source")
    private OSIndexBatch osIndexBatch;
}
