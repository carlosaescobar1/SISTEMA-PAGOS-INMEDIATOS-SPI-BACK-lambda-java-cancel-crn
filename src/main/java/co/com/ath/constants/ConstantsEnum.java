package co.com.ath.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
    VAULT_NAME("REDEBAN"),

    CEL("2"),

    EVENT_BATCH_CONSENT("consent"),

    EMPTY_STRING(""),

    FIRST_ARRAY_ITEM("0"),

    PN("PN"),

    REDEBAN("REDEBAN"),

    REDEBAN_PERSON("REDEBAN_PERSON"),

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

    ACTIVE_FLOW("ACTIVA"),

    INDEX_KEY_TEMPLATE("TemplateKey"),

    INDEX_BATCH_BY_KEY_TEMPLATE("TemplateBatchByKey"),

    INDEX_MIGRATE("migrate"),

    OP_PARAMETER_KEY_TYPE("keyType"),

    OP_PARAMETER_KEY_VALUE("keyId"),

    OP_PARAMETER_BLOCKED_BATCH("blockedBatch");

    private final String value;
}
