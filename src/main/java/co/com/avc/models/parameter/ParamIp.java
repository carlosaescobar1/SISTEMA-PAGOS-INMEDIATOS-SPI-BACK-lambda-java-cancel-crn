package co.com.avc.models.parameter;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;
/**
 * PaymentMethodDto
 * <p>
 * almacena direcciones IP asociadas a los diferentes bancos
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
@SerdeImport(ParamIp.class)
public class ParamIp {

    @JsonProperty("ipBAVV")
    private String bavv;

    @JsonProperty("ipBBOG")
    private String bbog;

    @JsonProperty("ipBOCC")
    private String bocc;

    @JsonProperty("ipBPOP")
    private String bpop;

    @JsonProperty("ipDALE")
    private String dale;

}
