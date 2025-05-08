package co.com.avc.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

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


