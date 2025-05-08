package co.com.avc.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ResponseServiceEnum
 * <p>
 * Enum que contiene la información necesaria
 * para las respuestas de la lambda.
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
public enum ResponseServiceEnum {


    /**
     * Constante de error en caso de que se presente un fallo al intentar operación sobre DynamoDB.
     */
    ERROR_TEC_EXCEPTION(500, "500", SeverityEnum.ERROR.getValue(),
            "Error al intentar operación sobre el directorio federado.", 500,
            "CONEXIÓN A CÁMARAS"),

    /**
     * Constante de error en caso de que se presente un fallo al intentar operación sobre DynamoDB.
     */
    ERROR_TEC_EXCEPTION_DYNAMO(500, "500", SeverityEnum.ERROR.getValue(),
            "Error al intentar operación sobre DynamoDB.", 500,
            ComponentEnum.CREATION.getValue()),

    /**
     * Constante de error en caso de que se presente un fallo al intentar operación sobre DynamoDB.
     */
    ERROR_TEC_EXCEPTION_BANK_ID(500, "500", SeverityEnum.ERROR.getValue(),
            "ID del banco no identificado", 500,
            "CONEXIÓN A CÁMARAS"),

    /**
     * Constante de error en caso de que se la llave ya exista.
     */
    ERROR_VALIDATE_BANNED_KEYS(206, "14", SeverityEnum.ERROR.getValue(),
            "La llave indicada pertenece al listado de llaves no permitidas", 14,
            "La llave indicada pertenece al listado de llaves no permitidas");

    private final Integer statusCode;
    private final String serverStatusCode;
    private final String severity;
    private final String statusDesc;
    private final Integer additionalStatusCode;
    private final String additionalStatusDesc;

}
