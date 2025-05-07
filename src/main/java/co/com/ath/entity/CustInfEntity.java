package co.com.ath.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

/**
 * CustInfEntity
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
@SerdeImport(CustInfEntity.class)
@DynamoDBDocument
public class CustInfEntity {

    @DynamoDBAttribute(attributeName = "custFirstName")
    private String custFirstName;

    @DynamoDBAttribute(attributeName = "custSecondName")
    private String custSecondName;

    @DynamoDBAttribute(attributeName = "custFirstLastName")
    private String custFirstLastName;

    @DynamoDBAttribute(attributeName = "custSecondLastName")
    private String custSecondLastName;

    @DynamoDBAttribute(attributeName = "custLegalName")
    private String custLegalName;

    @DynamoDBAttribute(attributeName = "custIdent")
    private CustIdentEntity custIdent;

    public static final TableSchema<CustInfEntity> SCHEMA =
            TableSchema.builder(CustInfEntity.class)
                    .newItemSupplier(CustInfEntity::new)
                    .addAttribute(String.class, a -> a.name("custFirstName")
                            .getter(CustInfEntity::getCustFirstName)
                            .setter(CustInfEntity::setCustFirstName))
                    .addAttribute(String.class, a -> a.name("custSecondName")
                            .getter(CustInfEntity::getCustSecondName)
                            .setter(CustInfEntity::setCustSecondName))
                    .addAttribute(String.class, a -> a.name("custFirstLastName")
                            .getter(CustInfEntity::getCustFirstLastName)
                            .setter(CustInfEntity::setCustFirstLastName))
                    .addAttribute(String.class, a -> a.name("custSecondLastName")
                            .getter(CustInfEntity::getCustSecondLastName)
                            .setter(CustInfEntity::setCustSecondLastName))
                    .addAttribute(String.class, a -> a.name("custLegalName")
                            .getter(CustInfEntity::getCustLegalName)
                            .setter(CustInfEntity::setCustSecondLastName))
                    .addAttribute(
                            EnhancedType.documentOf(CustIdentEntity.class, CustIdentEntity.SCHEMA),
                            a -> a.name("custIdent")
                                    .getter(CustInfEntity::getCustIdent)
                                    .setter(CustInfEntity::setCustIdent))
                    .build();

}
