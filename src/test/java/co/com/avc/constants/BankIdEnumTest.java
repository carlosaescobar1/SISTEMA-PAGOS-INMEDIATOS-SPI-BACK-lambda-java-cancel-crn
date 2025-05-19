package co.com.avc.constants;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankIdEnumTest {

    @Test
    void shouldReturnCorrectValueForBancoAvVillas() {
        assertEquals("0052", BankIdEnum.BANCO_AV_VILLAS.getValue());
    }

    @Test
    void shouldReturnCorrectValueForBancoDeBogota() {
        assertEquals("0001", BankIdEnum.BANCO_DE_BOGOTA.getValue());
    }

    @Test
    void shouldReturnCorrectValueForBancoDeOccidente() {
        assertEquals("0023", BankIdEnum.BANCO_DE_OCCIDENTE.getValue());
    }

    @Test
    void shouldReturnCorrectValueForBancoPopular() {
        assertEquals("0002", BankIdEnum.BANCO_POPULAR.getValue());
    }

    @Test
    void shouldReturnCorrectValueForDale() {
        assertEquals("0097", BankIdEnum.DALE.getValue());
    }
}