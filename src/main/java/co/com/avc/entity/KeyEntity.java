package co.com.avc.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

/**
 * KeyEntity
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
@ToString
@Introspected
@ReflectiveAccess
@SerdeImport(KeyEntity.class)
@DynamoDBDocument
public class KeyEntity {

    @DynamoDBAttribute
    private String keyType;

    @DynamoDBAttribute
    private String keyId;

    public static final TableSchema<KeyEntity> SCHEMA = TableSchema.builder(KeyEntity.class)
            .newItemSupplier(KeyEntity::new)
            .addAttribute(String.class, a -> a.name("keyType")
                    .getter(KeyEntity::getKeyType)
                    .setter(KeyEntity::setKeyType))
            .addAttribute(String.class, a -> a.name("keyId")
                    .getter(KeyEntity::getKeyId)
                    .setter(KeyEntity::setKeyId))
            .build();
}
