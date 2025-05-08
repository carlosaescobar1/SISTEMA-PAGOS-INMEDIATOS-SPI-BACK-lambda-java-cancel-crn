package co.com.avc.models.parameter;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;

/**
 * ParameterStoreDto
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
@SerdeImport(ParameterStoreDto.class)
public class ParameterStoreDto {

    private ArnSnsOpenSearch arnSnsOpenSearch;

    private VaultServicesTimeOut vaultServicesTimeOut;

    private String arnSecretOpenSearch;

    private ParamDynamo paramDynamo;

    private ParamActiveVault paramActiveVault;

    private RedebanConfigDto redebanConfigDto;

    private String region;

    private ParamFlowConfig paramFlowConfig;

    private int maxRetryBatch;

    private double blackListScore;
    /**
     * Valor de la cantidad maxima de regitros que buscara
     * en el proceso de sincronizacion de llaves en OpenSearch
     */
    private int opSearchQuerySize;
}
