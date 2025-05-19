package co.com.avc.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * ResponseStatusCodeEnum
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
public enum ResponseStatusCodeEnum {


    /**
     * Codigos de respuesta por SQS: Recibio petición
     */
    PERSON_CREATED_STATUS_CODE(201),

    /**
     * Codigos de respuesta por corner: Recibio petición
     */
    PERSON_SUCCESS_STATUS_CODE(200)
    ,
    /**
     * Codigos de respuesta por corner: No encontro resultados
     */
    PERSON_NOTFOUND_STATUS_CODE(404),

    ENTERPRISE_SUCCESS_STATUS_CODE(201);

    private int value;
}


