package co.com.avc.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BankIdEnum {

    BANCO_AV_VILLAS("0052"),
    BANCO_DE_BOGOTA("0001"),
    BANCO_DE_OCCIDENTE("0023"),
    BANCO_POPULAR("0002"),
    DALE("0097")

    ;

    private final String value;

}