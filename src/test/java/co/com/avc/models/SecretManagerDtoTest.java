package co.com.avc.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SecretManagerDtoTest {

    @Test
    public void testSecretManagerDtoGettersAndSetters() {
        // Crear instancia de SecretManagerDto
        SecretManagerDto secretManagerDto = new SecretManagerDto();

        // Establecer valores
        int port = 9200;
        String schema = "https";
        String keyword = "test-keyword";
        String hostname = "localhost";
        String username = "test-user";

        secretManagerDto.setPort(port);
        secretManagerDto.setSchema(schema);
        secretManagerDto.setKeyword(keyword);
        secretManagerDto.setHostname(hostname);
        secretManagerDto.setUsername(username);

        // Verificar valores
        assertEquals(port, secretManagerDto.getPort());
        assertEquals(schema, secretManagerDto.getSchema());
        assertEquals(keyword, secretManagerDto.getKeyword());
        assertEquals(hostname, secretManagerDto.getHostname());
        assertEquals(username, secretManagerDto.getUsername());
    }
}