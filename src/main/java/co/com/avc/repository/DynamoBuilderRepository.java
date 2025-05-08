package co.com.avc.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * DynamoRepository
 * <p>
 * Esta clase establece la conexión a la base de datos
 * DynamoDB en AWS
 * <p>
 * Desarrollo ATH - AvalPay Center
 * <p>
 * Creado el : 25 de Julio de 2024
 * <p>
 * Autor: Luis F. Herreño Mateus
 * <p>
 * Requerimiento: Migración AvalPay Center
 * <p>
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 * <p>
 * Clase encargada de mapear la conexion con Dynamo
 *
 * @author Luis F Herreno
 * @version 1.0
 * @since 1.0
 */

@AllArgsConstructor
@Getter
@Setter
@Slf4j
public class DynamoBuilderRepository {

    /**
     * Region de AWS en donde se alojan los servicios.
     */
    private String dynamoRegion;

    /**
     * Variable encargada de manejar mensajes de error para los catch
     */
    private final StringWriter errors = new StringWriter();

    /**
     * Variable constante de ERROR
     */
    static final String ERROR = "Error: ";

    /**
     * Método que construye un objeto de tipo DynamoDBMapper.
     * <p>
     * Este método crea un objeto de tipo DynamoDBMapper configurado con
     * la tabla de DynamoDB y el cliente de AmazonDynamoDB.
     *
     * @return Un objeto de tipo DynamoDBMapper configurado
     */
    public DynamoDbEnhancedClient getClient() {

        DynamoDbEnhancedClient enhancedClient;
        try {
            log.info("Entrando getClient");
            var dynamoDbClient = DynamoDbClient.builder().region(Region.of(dynamoRegion))
                    .credentialsProvider(
                            SdkSystemSetting.AWS_CONTAINER_CREDENTIALS_FULL_URI.getStringValue().isPresent()
                                    ? ContainerCredentialsProvider.builder().build()
                                    : EnvironmentVariableCredentialsProvider.create())
                    .build();

            enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();

            log.info("Saliendo getClient");
            return enhancedClient;
        } catch (Exception e) {
            e.printStackTrace(new PrintWriter(errors));
            log.error(ERROR + "{}", errors);
            log.info("Entra a default");
            return null;
        }
    }
}
