package co.com.avc.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * ResponseCodeEnum
 * <p>
 * Enum que contiene los posibles valores que podrá
 * tomar el campo ResponseCode en las respuestas de la lambda.
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

public enum ResponseCodeEnum { //Enums de las respuestas respecto a las peticiones

    /**
     * Código de respuesta exitoso para el servicio de creación de personas.
     */
    CRN_PERSON_SUCCESS_STATUS_CODE("Value_key"),






    COR_PERSON_ERROR_CANCEL_KEY_NOEXIST_STATUS_CODE("XXXX"),

    RED_PERSON_SUCCESS_STATUS_CODE("NT00"),
    COR_PERSON_SUCCESS_STATUS_CODE("SUCCES"),//Esto es al crear en redeban
    /**
     * Código de respuesta exitoso para el servicio de creación de personas.
     */
    RED_PERSON_CREATED_STATUS_CODE("U000"),
    COR_PERSON_CREATED_STATUS_CODE("CREATED"),
    /**
     * Código de respuesta fallido para el servicio de creación de personas.
     */
    RED_PERSON_ERROR_CREATED_STATUS_CODE("U808"),
    /**
     * Código de respuesta fallido para el servicio de creación de personas.
     */
    RED_PERSON_ERROR_BODY_STATUS_CODE("NT40"),
    ;



    private final String value;

}
