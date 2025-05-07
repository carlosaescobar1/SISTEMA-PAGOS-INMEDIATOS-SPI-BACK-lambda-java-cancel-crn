package co.com.ath.entity;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags;

/**
 * DynamoSpiEntity
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
@SerdeImport(DynamoSpiEntity.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DynamoSpiEntity {

    @DynamoDBHashKey(attributeName = "id")
    private String id;

    @DynamoDBRangeKey(attributeName = "sk")
    private String sk;

    @DynamoDBAttribute(attributeName = "key")
    private KeyEntity key;

    @DynamoDBAttribute(attributeName = "custInf")
    private CustInfEntity custInf;

    @DynamoDBAttribute(attributeName = "custType")
    private String custType;

    @DynamoDBAttribute(attributeName = "identSerialNum")
    private String identSerialNum;

    @DynamoDBAttribute(attributeName = "acctInfo")
    private AcctInfoEntity acctInfo;

    @DynamoDBAttribute(attributeName = "effDtCreate")
    private String effDtCreate;

    @DynamoDBAttribute(attributeName = "effDtModify")
    private String effDtModify;

    @DynamoDBAttribute(attributeName = "effDtConsent")
    private String effDtConsent;

    @DynamoDBAttribute(attributeName = "statusKey")
    private String statusKey;

    @DynamoDBAttribute(attributeName = "preferredIndicator")
    private String preferredIndicator;

    @DynamoDBAttribute(attributeName = "vaultNameRec")
    private String vaultNameRec;

    @DynamoDBAttribute(attributeName = "consent")
    private String consent;

    @DynamoDBAttribute(attributeName = "statusDirectory")
    private String statusDirectory;

    @DynamoDBAttribute(attributeName = "signerAlias")
    private String signerAlias;
    

    public static final TableSchema<DynamoSpiEntity> TABLE_SCHEMA_DYNAMO_SPI = TableSchema.builder(DynamoSpiEntity.class)
            .newItemSupplier(DynamoSpiEntity::new)
            .addAttribute(String.class, a -> a.name("id")
                    .getter(DynamoSpiEntity::getId)
                    .setter(DynamoSpiEntity::setId)
                    .tags(StaticAttributeTags.primaryPartitionKey()))
            .addAttribute(String.class, a -> a.name("sk")
                    .getter(DynamoSpiEntity::getSk)
                    .setter(DynamoSpiEntity::setSk)
                    .tags(StaticAttributeTags.primarySortKey()))
            .addAttribute(
                    EnhancedType.documentOf(KeyEntity.class, KeyEntity.SCHEMA),
                    a -> a.name("key")
                            .getter(DynamoSpiEntity::getKey)
                            .setter(DynamoSpiEntity::setKey))
            .addAttribute(
                    EnhancedType.documentOf(CustInfEntity.class, CustInfEntity.SCHEMA),
                    a -> a.name("custInf")
                            .getter(DynamoSpiEntity::getCustInf)
                            .setter(DynamoSpiEntity::setCustInf))
            .addAttribute(String.class, a -> a.name("custType")
                    .getter(DynamoSpiEntity::getCustType)
                    .setter(DynamoSpiEntity::setCustType))
            .addAttribute(String.class, a -> a.name("identSerialNum")
                    .getter(DynamoSpiEntity::getIdentSerialNum)
                    .setter(DynamoSpiEntity::setIdentSerialNum))
            .addAttribute(
                    EnhancedType.documentOf(AcctInfoEntity.class, AcctInfoEntity.SCHEMA),
                    a -> a.name("acctInfo")
                            .getter(DynamoSpiEntity::getAcctInfo)
                            .setter(DynamoSpiEntity::setAcctInfo))
            .addAttribute(String.class, a -> a.name("effDtCreate")
                    .getter(DynamoSpiEntity::getEffDtCreate)
                    .setter(DynamoSpiEntity::setEffDtCreate))
            .addAttribute(String.class, a -> a.name("effDtModify")
                    .getter(DynamoSpiEntity::getEffDtModify)
                    .setter(DynamoSpiEntity::setEffDtModify))
            .addAttribute(String.class, a -> a.name("effDtConsent")
                    .getter(DynamoSpiEntity::getEffDtConsent)
                    .setter(DynamoSpiEntity::setEffDtConsent))
            .addAttribute(String.class, a -> a.name("statusKey")
                    .getter(DynamoSpiEntity::getStatusKey)
                    .setter(DynamoSpiEntity::setStatusKey))
            .addAttribute(String.class, a -> a.name("preferredIndicator")
                    .getter(DynamoSpiEntity::getPreferredIndicator)
                    .setter(DynamoSpiEntity::setPreferredIndicator))
            .addAttribute(String.class, a -> a.name("vaultNameRec")
                    .getter(DynamoSpiEntity::getVaultNameRec)
                    .setter(DynamoSpiEntity::setVaultNameRec))
            .addAttribute(String.class, a -> a.name("consent")
                    .getter(DynamoSpiEntity::getConsent)
                    .setter(DynamoSpiEntity::setConsent))
            .addAttribute(String.class, a -> a.name("statusDirectory")
                    .getter(DynamoSpiEntity::getStatusDirectory)
                    .setter(DynamoSpiEntity::setStatusDirectory))
            .addAttribute(String.class, a -> a.name("signerAlias")
                    .getter(DynamoSpiEntity::getSignerAlias)
                    .setter(DynamoSpiEntity::setSignerAlias))
            .build();
}
