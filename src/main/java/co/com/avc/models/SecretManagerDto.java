package co.com.avc.models;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Introspected
@SerdeImport(SecretManagerDto.class)
public class SecretManagerDto {

    /**
     * Puerto para la conexión con OpenSearch
     */
    private int port;

    /**
     * Esquema para la conexión
     */
    private String schema;

    /**
     * Palabra clave para la autenticación en OpenSearch
     */
    private String keyword;

    /**
     * Nombre del host para la conexión con OpenSearch
     */
    private String hostname;

    /**
     * Nombre de usuario para la autenticación en OpenSearch
     */
    private String username;

}
