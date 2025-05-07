package co.com.ath.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCodeEnum {

    /**
     * Código de respuesta exitoso para el servicio de creación de personas.
     */
    RED_PERSON_SUCCESS_STATUS_CODE("NT00"),
    /**
     * Código de respuesta exitoso para el servicio de creación de personas.
     */
    RED_PERSON_CREATED_STATUS_CODE("U000"),
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
