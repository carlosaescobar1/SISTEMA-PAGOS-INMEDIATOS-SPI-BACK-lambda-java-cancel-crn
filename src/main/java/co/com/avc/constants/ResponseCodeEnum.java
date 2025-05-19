package co.com.avc.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
