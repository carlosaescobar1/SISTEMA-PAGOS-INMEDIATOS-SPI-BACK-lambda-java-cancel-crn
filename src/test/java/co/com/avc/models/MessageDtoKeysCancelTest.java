package co.com.avc.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageDtoKeysCancelTest {

    @Test
    public void testMessageDtoKeysCancelGettersAndSetters() {
        // Crear instancia de MessageDtoKeysCancel
        MessageDtoKeysCancel messageDtoKeysCancel = new MessageDtoKeysCancel();

        // Establecer valores
        String keyId = "test-key-id";
        String fileName = "test-file.txt";
        messageDtoKeysCancel.setKeyId(keyId);
        messageDtoKeysCancel.setFileName(fileName);

        // Verificar valores
        assertEquals(keyId, messageDtoKeysCancel.getKeyId());
        assertEquals(fileName, messageDtoKeysCancel.getFileName());
    }
}