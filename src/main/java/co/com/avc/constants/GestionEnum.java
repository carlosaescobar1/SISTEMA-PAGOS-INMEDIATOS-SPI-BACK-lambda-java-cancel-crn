/**
 * Enum con los nombres de variables entorno
 */
package co.com.avc.constants;


/**
 * Enum con los nombres de variables entorno
 * <p>
 * GestionEnum
 * <p>
 * Desarrollo ATH - Sistema de pagos de Bajo Valor Inmediatos (SPBVI)
 * <p>
 * Creado el : 28 de julio de 2024
 *
 * @author Kevin A. Smok Garcia
 * @version 1.0
 * @since 1.0
 * <p>
 * Requerimiento: Sistema de pagos de Bajo Valor Inmediatos (SPBVI)
 * <p>
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 */
public enum GestionEnum {
    /**
     * Clave del secreto para acceder al valor del usuario
     */
    VAR_USER("username"),

    /**
     * Clave del secreto para acceder al valor de la contraseña
     */
    VAR_KEY("password"),

    /**
     * Clave del secreto para acceder al valor del host
     */
    VAR_HOST("host"),

    /**
     * Clave del secreto para acceder al valor del puerto
     */
    VAR_PORT("port"),

    /**
     * Clave del secreto para acceder al valor del esquema
     */
    VAR_SCHEMA("schema");

    private String value;

    /**
     * Método que permite que se asigne un valor a cada una de las constantes
     *
     * @param value
     */
    private GestionEnum(String value) {
        this.value = value;
    }

    /**
     * Método que permite obtener los valores de las constantes
     *
     * @return valor de la constante
     */
    public String getValue() {
        return this.value;
    }
}
