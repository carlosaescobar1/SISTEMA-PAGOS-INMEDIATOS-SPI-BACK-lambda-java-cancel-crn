package co.com.ath.models.dynamo;

import co.com.ath.constants.ConstantsEnum;
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
     * Objeto que guarda la información de la llave.
     */
    @JsonProperty("key")
    private KeyDto key;

    /**
     * Objeto que guarda la información del cliente.
     */
    @JsonProperty("custInf")
    private CustInfDto custInf;

    /**
     * Tipo de persona: Natural (PN) o jurídica (PJ).
     */
    @JsonProperty("custType")
    private String custType;

    /**
     * Identificación del banco emisor de la cuenta (NIT entidad emisóra)
     */
    @JsonProperty("identSerialNum")
    private String identSerialNum;

    /**
     * Objeto que guarda la información de la cuenta asociada a la llave.
     */
    @JsonProperty("acctInfo")
    private AcctInfoDto acctInfo;

    /**
     * Fecha de creación de la llave.
     */
    @JsonProperty("effDtCreate")
    private String effDtCreate;

    /**
     * Fecha de modificación de la llave.
     */
    @JsonProperty("effDtModify")
    private String effDtModify;

    /**
     * Fecha de aceptación de consentimiento de la llave
     * para su creación en DICE.
     */
    @JsonProperty("effDtConsent")
    private String effDtConsent;

    /**
     * Estado de la llave.
     */
    @JsonProperty("statusKey")
    private String statusKey;

    /**
     * Indicador de preferencia de la llave. (S/N)
     */
    @JsonProperty("preferredIndicator")
    private String preferredIndicator;

    /**
     * Nombre de la cámara receptora
     */
    @JsonProperty("vaultNameRec")
    private String vaultNameRec;

    /**
     * El cliente dio el consentimiento para la migración cuando hayan cámaras (S/N)
     */
    @JsonProperty("consent")
    private String consent;

    /**
     * Indica el directorio en el cual se encuentra sincronizada la llave.
     */
    @JsonProperty("statusDirectory")
    private String statusDirectory;

    /**
     * Nombre del alias que tiene configurado el cliente
     */
    @JsonProperty("signerAlias")
    private String signerAlias;

    /**
     * Indica el ID de comercio
     */
    @JsonProperty("merchantId")
    private String merchantId;

    public DynamoSpiDto() {
        this.vaultNameRec = ConstantsEnum.REDEBAN.getValue();
    }

}
