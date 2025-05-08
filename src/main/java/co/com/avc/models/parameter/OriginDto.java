package co.com.avc.models.parameter;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;

/**
 * OriginDto
 * <p>
 * Enum que contiene la información necesaria
 * para las respuestas de la lambda.
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
@SerdeImport(OriginDto.class)
public class OriginDto {

    /**
     * Valor del Origen cuando viene de mobiles
     */
    private String mobileBanking;

    /**
     * Valor del origen cuando viene de la página
     * de la entidad
     */
    private String virtualBanking;

    /**
     * Valor del origen cuando viene de las
     * oficinas de las entidades
     */
    private String offices;

}
