package co.com.avc.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SqsDtoTest {

    @Test
    public void testSqsDtoGettersAndSetters() {
        // Crear instancia de SqsDto
        SqsDto sqsDto = new SqsDto();

        // Establecer valores
        String message = "Test message";
        String subject = "Test subject";
        sqsDto.setMessage(message);
        sqsDto.setSubject(subject);

        // Verificar valores
        assertEquals(message, sqsDto.getMessage());
        assertEquals(subject, sqsDto.getSubject());
    }
}