package co.com.avc.entity;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

/**
 * AcctInfoEntity
 *
 * Desarrollo AVC - Sistema de pagos de Bajo Valor Inmediatos (SPBVI)
 *
 * Creado el : 7 de mayo 2025
 *
 * Autor: Jonhatan G Romero
 *
 * Requerimiento: Sistema de pagos de Bajo Valor Inmediatos (SPBVI)
 *
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 *
 * Este software es confidencial y es propiedad de AVC, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 *
 * Clase que representa la información de la cuenta asociada a la llave del usuario.
 *
 * @author Jonhatan G Romero
 * @version 1.0
 * @since  1.0
 */
@Getter
@Setter
@Introspected
@ReflectiveAccess
@SerdeImport(DynamoSpiEntity.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DynamoSpiEntity {

    /*Objeto que guarda la información del cliente.*/
    @DynamoDBAttribute(attributeName = "person")
    private PersonEntity person;

    /*Objeto que guarda la información de la llave.*/
    @DynamoDBAttribute(attributeName = "key")
    private KeyEntity key;

    /*Tipo de persona: Natural o juridica.*/
    @DynamoDBAttribute(attributeName = "paymentMethod")
    private PaymentMethodEntity paymentMethod;

    /*Descripcion correspondiente a la llave*/
    @DynamoDBAttribute(attributeName = "description")
    private String description;

    /*Estado de la llave*/
    @DynamoDBAttribute(attributeName = "cdStateKey")
    private String cdStateKey;

    public static final TableSchema<DynamoSpiEntity> TABLE_SCHEMA_DYNAMO_SPI = TableSchema.builder(DynamoSpiEntity.class)
            .newItemSupplier(DynamoSpiEntity::new)
            .addAttribute(
                    EnhancedType.documentOf(PersonEntity.class, PersonEntity.SCHEMA),
                    a -> a.name("person")
                            .getter(DynamoSpiEntity::getPerson)
                            .setter(DynamoSpiEntity::setPerson))
            .addAttribute(
                    EnhancedType.documentOf(KeyEntity.class, KeyEntity.SCHEMA),
                    a -> a.name("key")
                            .getter(DynamoSpiEntity::getKey)
                            .setter(DynamoSpiEntity::setKey))
            .addAttribute(
                    EnhancedType.documentOf(PaymentMethodEntity.class, PaymentMethodEntity.SCHEMA),
                    a -> a.name("paymentMethod")
                            .getter(DynamoSpiEntity::getPaymentMethod)
                            .setter(DynamoSpiEntity::setPaymentMethod))
            .addAttribute(String.class, a -> a.name("description")
                    .getter(DynamoSpiEntity ::getDescription)
                    .setter(DynamoSpiEntity ::setDescription))
            .addAttribute(String.class, a -> a.name("cdStateKey")
                    .getter(DynamoSpiEntity::getCdStateKey)
                    .setter(DynamoSpiEntity::setCdStateKey))
            .build();
}