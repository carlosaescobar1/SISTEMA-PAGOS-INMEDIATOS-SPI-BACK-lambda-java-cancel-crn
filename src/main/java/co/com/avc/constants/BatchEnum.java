package co.com.avc.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * SeverityEnum
 * <p>
 * Enum que contiene los posibles valores que podrá
 * tomar el campo Severity en las respuestas de la lambda.
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
@AllArgsConstructor
@Getter
public enum BatchEnum {

    BATCH_INDEX("index_batch"),

    BATCH_INDEX_BY_KEY_TEMPLATE("TemplateBatchByKey"),

    BATCH_BLOCK("1"),

    BATCH_UNLOCK("0"),

    BATCH_FAILED("Fallido"),

    BATCH_SUCCESS("Exitoso"),

    BATCH_PENDING("Pendiente"),

    BATCH_CANCEL("Cancelado"),

    BATCH_STATUS("statusBatch"),

    BATCH_SUBJECT("batch"),

    BATCH_CANCEL_SUBJECT("CANC"),

    BATCH_EVENT_CONSENT("consent"),
    ;

    private final String value;

}
