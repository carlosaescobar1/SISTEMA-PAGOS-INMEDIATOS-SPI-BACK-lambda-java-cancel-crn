package co.com.avc.models.dynamo;

import co.com.avc.constants.ConstantsEnum;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;

/**
 * DynamoSpiDto
 * <p>
 * Objeto que representa el DTO de los registros
 * almacenados en el directorio Aval - base de datos
 * DynamoDB
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
@ReflectiveAccess
@SerdeImport(DynamoSpiDto.class)
public class DynamoSpiDto {

    /**
     * Hace referencia al HashKey del dynamo.
     */
    @JsonProperty("id")
    private String id;

    /**
     * Hace referencia al RangeKey del dynamo.
     */
    @JsonProperty("sk")
    private String sk;

    /**
     * Objeto que guarda la información del cliente
     */
    @JsonProperty("person")
    private PersonDto person;

    /**
     * Objeto que guarda la información de la llave
     */
    @JsonProperty("key")
    private KeyDto key;

    /**
     * Tipo de persona: Natural o juridica.
     */
    @JsonProperty("paymentMethod")
    private PaymentMethodDto paymentMethod;


    /**
     * Descripción de la llave
     */
    @JsonProperty("description")
    private String description;

    /**
     * Estado de la llave
     */
    @JsonProperty("cdStateKey")
    private String cdStateKey;



}
