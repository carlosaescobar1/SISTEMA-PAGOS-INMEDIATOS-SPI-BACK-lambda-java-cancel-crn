package co.com.ath.entity;

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
@SerdeImport(AcctInfoEntity.class)
@DynamoDBDocument
public class AcctInfoEntity {

    /**
     * Tipo de cuenta.
     */
    @DynamoDBAttribute(attributeName = "acctType")
    private String acctType;

    /**
     * Identificador de la cuenta.
     */
    @DynamoDBAttribute(attributeName = "acctId")
    private String acctId;

    /**
     * Identificador del banco.
     */
    @DynamoDBAttribute(attributeName = "bankId")
    private String bankId;

    /**
     * Constructor por defecto.
     */
    public static final TableSchema<AcctInfoEntity> SCHEMA =
            TableSchema.builder(AcctInfoEntity.class)
                    .newItemSupplier(AcctInfoEntity::new)
                    .addAttribute(String.class, a -> a.name("acctType")
                            .getter(AcctInfoEntity::getAcctType)
                            .setter(AcctInfoEntity::setAcctType))
                    .addAttribute(String.class, a -> a.name("acctId")
                            .getter(AcctInfoEntity::getAcctId)
                            .setter(AcctInfoEntity::setAcctId))
                    .addAttribute(String.class, a -> a.name("bankId")
                            .getter(AcctInfoEntity::getBankId)
                            .setter(AcctInfoEntity::setBankId))
                    .build();
}
