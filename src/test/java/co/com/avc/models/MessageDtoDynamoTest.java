package co.com.avc.models;

import co.com.avc.models.dynamoAth.DynamoSpiDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageDtoDynamoTest {

    @Test
    public void testMessageDtoDynamoGettersAndSetters() {
        // Crear instancia de DynamoSpiDto
        DynamoSpiDto dynamoSpiDto = new DynamoSpiDto();

        // Crear instancia de MessageDtoDynamo
        MessageDtoDynamo messageDtoDynamo = new MessageDtoDynamo();

        // Establecer valores
        String fileName = "test-file.txt";
        messageDtoDynamo.setDynamoSpiDto(dynamoSpiDto);
        messageDtoDynamo.setFileName(fileName);

        // Verificar valores
        assertEquals(dynamoSpiDto, messageDtoDynamo.getDynamoSpiDto());
        assertEquals(fileName, messageDtoDynamo.getFileName());
    }
}