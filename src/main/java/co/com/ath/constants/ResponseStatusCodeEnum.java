package co.com.ath.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseStatusCodeEnum {

    /**
     * Código de respuesta exitoso para el servicio de creación de personas.
     */
    PERSON_SUCCESS_STATUS_CODE(200),
    /**
     * Código de respuesta exitoso para el servicio de creación de personas.
     */
    PERSON_CREATED_STATUS_CODE(201),
    /**
     * Código de respuesta exitoso para el servicio de creación de comercio.
     */
    ENTERPRISE_SUCCESS_STATUS_CODE(200),


    ;

    private final int value;

}
