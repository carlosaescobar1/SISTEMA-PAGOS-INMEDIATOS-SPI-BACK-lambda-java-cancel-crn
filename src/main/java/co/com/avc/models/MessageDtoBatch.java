package co.com.avc.models;

import co.com.ath.opensearch.logs.entity.index_batch.OSIndexBatch;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;
/**
 * MessageDto
 * <p>
 * Almacena información de un lote de mensajes, con un identificador
 * para OpenSearch (idOpensearch) y un objeto (OSIndexBatch) como fuente
 * <p>
 * Desarrollo ATH - SPBVI
 * <p>
 * Creado él: 09 de septiembre de 2024
 *
 * @author Luis F. Herreño Mateus
 * @version 1.0
 * @since 1.0
 * <p>
 * Requerimiento: SPBVI - Sistema de pagos de bajo valor inmediatos
 * <p>
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 */
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
