package co.com.ath.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * AcctTypeEnum
 * <p>
 * Enum que contiene los posibles valores para el
 * campo consent en la base de datos DynamoDB
 * del directorio Aval.
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
@AllArgsConstructor
/**
 * Enum que contiene los posibles valores para el
 * campo consent en la base de datos DynamoDB
 * del directorio Aval.
 */
public enum ConsentEnum {

    /**
     * Consentimiento aprobado.
     */
    S("S"),

    /**
     * Consentimiento rechazado.
     */
    N("N");

    private final String value;
}
