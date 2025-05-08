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
@SerdeImport(PaymentMethodEntity.class)
@DynamoDBDocument
public class PaymentMethodEntity {
    @DynamoDBAttribute(attributeName = "type_payment_acc")
    private String type_payment_acc;

    @DynamoDBAttribute(attributeName = "account_number")
    private String account_number;

    public static final TableSchema<PaymentMethodEntity> SCHEMA =
            TableSchema.builder(PaymentMethodEntity.class)
                    .newItemSupplier(PaymentMethodEntity::new)
                    .addAttribute(String.class, a -> a.name("type_payment_acc")
                            .getter(PaymentMethodEntity::getType_payment_acc)
                            .setter(PaymentMethodEntity::setType_payment_acc))
                    .addAttribute(String.class, a -> a.name("account_number")
                            .getter(PaymentMethodEntity::getAccount_number)
                            .setter(PaymentMethodEntity::setAccount_number))
                    .build();
}
