package co.com.avc.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MsgInformationResponseSuccessTest {

    @Test
    public void testMsgInformationResponseSuccessGettersAndSetters() {
        // Crear instancia de MsgInformationResponseSuccess
        MsgInformationResponseSuccess response = new MsgInformationResponseSuccess();

        // Establecer valores
        String valueKey = "test-value";
        response.setValue_key(valueKey);

        // Verificar valores
        assertEquals(valueKey, response.getValue_key());
    }
}