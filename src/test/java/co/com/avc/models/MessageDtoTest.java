package co.com.avc.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageDtoTest {

    @Test
    public void testMessageDtoGettersAndSetters() {
        // Crear instancias de los objetos relacionados
        MessageDtoBatch batch = new MessageDtoBatch();
        MessageDtoDynamo dynamo = new MessageDtoDynamo();
        MessageDtoKeysCancel keysCancel = new MessageDtoKeysCancel();

        // Crear instancia de MessageDto
        MessageDto messageDto = new MessageDto();

        // Establecer valores
        messageDto.setMessageDtoBatch(batch);
        messageDto.setMessageDtoDynamo(dynamo);
        messageDto.setMessageDtoKeysCancel(keysCancel);

        // Verificar valores
        assertEquals(batch, messageDto.getMessageDtoBatch());
        assertEquals(dynamo, messageDto.getMessageDtoDynamo());
        assertEquals(keysCancel, messageDto.getMessageDtoKeysCancel());
    }

}



