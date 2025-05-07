package co.com.ath.constants;

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
public enum SeverityEnum {

    /**
     * Constante que representa el nivel de severidad INFO.
     */
    IFO("Info"),
    /**
     * Constante que representa el nivel de severidad WARNING.
     */
    WARNING("Warning"),
    /**
     * Constante que representa el nivel de severidad ERROR.
     */
    ERROR("Error");

    private final String value;

}
