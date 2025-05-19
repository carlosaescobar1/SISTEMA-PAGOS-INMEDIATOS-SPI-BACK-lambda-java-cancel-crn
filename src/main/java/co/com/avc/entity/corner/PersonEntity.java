package co.com.avc.entity.corner;

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
 * AcctInfoEntity
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
@SerdeImport(PersonEntity.class)
@DynamoDBDocument
public class PersonEntity {

    @DynamoDBAttribute(attributeName = "first_name")
    private String first_name;

    @DynamoDBAttribute(attributeName = "second_name")
    private String second_name;

    @DynamoDBAttribute(attributeName = "first_surname")
    private String first_surname;

    @DynamoDBAttribute(attributeName = "second_surname")
    private String second_surname;

    @DynamoDBAttribute(attributeName = "type_person")
    private String type_person;

    @DynamoDBAttribute(attributeName = "businessName")
    private String businessName;

    @DynamoDBAttribute(attributeName = "documentType")
    private String documentType;

    @DynamoDBAttribute(attributeName = "documentNumber")
    private String documentNumber;




    public static final TableSchema<PersonEntity> SCHEMA =
            TableSchema.builder(PersonEntity.class)
                    .newItemSupplier(PersonEntity::new)
                    .addAttribute(String.class, a -> a.name("first_name")
                            .getter(PersonEntity::getFirst_name)
                            .setter(PersonEntity::setFirst_name))
                    .addAttribute(String.class, a -> a.name("second_name")
                            .getter(PersonEntity::getSecond_name)
                            .setter(PersonEntity::setSecond_name))
                    .addAttribute(String.class, a -> a.name("first_surname")
                            .getter(PersonEntity::getFirst_surname)
                            .setter(PersonEntity::setFirst_surname))
                    .addAttribute(String.class, a -> a.name("second_surname")
                            .getter(PersonEntity::getSecond_surname)
                            .setter(PersonEntity::setSecond_surname))
                    .addAttribute(String.class, a -> a.name("type_person")
                            .getter(PersonEntity::getType_person)
                            .setter(PersonEntity::setType_person))
                    .addAttribute(String.class, a -> a.name("businessName")
                            .getter(PersonEntity::getBusinessName)
                            .setter(PersonEntity::setBusinessName))
                    .addAttribute(String.class, a -> a.name("documentType")
                            .getter(PersonEntity::getDocumentType)
                            .setter(PersonEntity::setDocumentType))
                    .addAttribute(String.class, a -> a.name("documentNumber")
                            .getter(PersonEntity::getDocumentNumber)
                            .setter(PersonEntity::setDocumentNumber))
                    .build();

}
