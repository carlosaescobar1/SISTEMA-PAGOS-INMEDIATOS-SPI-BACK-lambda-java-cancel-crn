/**
 * Clase en package de constants
 */
package co.com.avc.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ComponentEnum
 * <p>
 * Enum que contiene los componentes de la lambda.
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
/**
 * Enum que contiene los componentes de la lambda.
 */
public enum ComponentEnum { //NO NECESARIA


    DYNAMO_DB("DYNAMO_DB"),
    VALIDATIONS("VALIDATIONS"),
    CREATION("CREATION"),

    ;

    private final String value;

}

