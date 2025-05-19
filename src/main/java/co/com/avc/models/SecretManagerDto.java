package co.com.avc.models;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;
/**
 * SecretManagerDto
 * <p>
 * almacena parámetros de configuración para la conexión con OpenSearch
 * <p>
 * Desarrollo ATH - SPBVI
 * <p>
 * Creado él: 09 de septiembre de 2024
 *
 * @author Luis F. Herreño Mateus
 * @version 1.0
 * @since 1.0
 * <p>
 * Requerimiento: SPBVI - Sistema de pagos de bajo valor inmediatos
 * <p>
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 */
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
