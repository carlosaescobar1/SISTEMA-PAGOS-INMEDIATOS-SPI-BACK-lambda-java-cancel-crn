package co.com.avc.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * ConstantsEnum
 * <p>
 * Enum que contiene las constantes de la lambda.
 * <p>
 * Creado el: 05 de septiembre de 2024
 * <p>
 * Autor: Luis F Herreño
 * <p>
 * Requerimiento: Migración AvalPay center
 * <p>
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 * <p>
 *
 * @author Luis F Herreno
 * @version 1.0
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public enum ConstantsEnum {

    /**
     * Comentario mostrado al presentarse errores en conexiones
     */
    ERROR_SERVICE("Error durante el proceso de creación (Exception): "),

    /**
     * Comentario mostrado al presentarse errores en conexiones
     */
    ERROR_ATH_SERVICE("Error durante el proceso de creación (ATHException): "),

    /**
     * Comentario mostrado al nombre del Vault
     */
    VAULT_NAME("CORNER"),

    CEL("2"),

    EVENT_BATCH_CONSENT("consent"),

    EMPTY_STRING(""),

    FIRST_ARRAY_ITEM("0"),

    PN("PN"),

    REDEBAN("REDEBAN"),

    CORNER("CORNER"),

    REDEBAN_PERSON("REDEBAN_PERSON"),

    CORNER_PERSON("CORNER"),

    /**
     * Comentario mostrado al presentarse un error en el metodo de guardado opensearch
     */
    ERROR_SAVE("No se pudo realizar el guardado del documento "),

    /**
     * Comentario mostrado al presentarse errores en conexiones con opensearch
     */
    ERROR_CONNECTION("No se esta generando la conexion con OpenSearch Correctamente "),

    PENDING_BATCH("Pendiente"),

    FAILED_BATCH("Fallido"),

    SUCCESS_BATCH("Exitoso"),

    BLOCK_BATCH("1"),

    UNLOCK_BATCH("0"),

    S("S"),

    N("N"),

    SUBJECT_BATCH("batch"),

    SUBJECT_MIGRATION("MIG"),

    HEADER_CHANNEL("OFVV"),



    INDEX_KEY_TEMPLATE("TemplateKey"),

    INDEX_BATCH_BY_KEY_TEMPLATE("TemplateBatchByKey"),

    INDEX_MIGRATE("migrate"),

    OP_PARAMETER_KEY_TYPE("keyType"),

    OP_PARAMETER_KEY_VALUE("keyId"),

    OP_PARAMETER_BLOCKED_BATCH("blockedBatch"),

    ACTIVE_FLOW("ACTIVA"),

    CANCELADA("CANCELADA"),
    KEY_ID_START_WITH("@"),
    ;

    private final String value;
}
