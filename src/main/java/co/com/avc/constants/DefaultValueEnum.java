package co.com.avc.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/**
 * DefaultValueEnum
 * <p>
 * Enum que contiene los valores por defecto para la lambda.
 * <p>
 * Creado el: 05 de septiembre de 2024
 * <p>
 * Autor: Luis F Herreño
 * <p>
 * Requerimiento: Migración AvalPay center
 * <p>
 *     Copyright © A Toda Hora S.A. Todos los derechos reservados
 *     <p>
 *         Este software es confidencial y es propiedad de ATH, queda prohibido
 *         su uso, reproducción y copia de manera parcial o permanente salvo autorización
 *         expresa de A Toda Hora S.A o de quién represente sus derechos.
 *     <p>
 */
public enum DefaultValueEnum {

    EMPTY_STRING(""),

    EMPTY_SPACE_STRING(" "),

    APPLICATION_JSON("application/json"),

    UNKNOW_RETRY_BATCH("2");

    private final String value;

}
