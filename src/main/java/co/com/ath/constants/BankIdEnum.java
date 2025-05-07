package co.com.ath.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
/**
 * Enum que contiene los id de los bancos
 */
public enum BankIdEnum {

    /**
     * ID del banco AV VILLAS
     */
    BANCO_AV_VILLAS("0052"),
    /**
     * ID del banco BOGOTA
     */
    BANCO_DE_BOGOTA("0001"),
    /**
     * ID del banco DE OCCIDENTE
     */
    BANCO_DE_OCCIDENTE("0023"),
    /**
     * ID del banco POPULAR
     */
    BANCO_POPULAR("0002"),
    /**
     * ID de DALE
     */
    DALE("0097");

    private final String value;

}
