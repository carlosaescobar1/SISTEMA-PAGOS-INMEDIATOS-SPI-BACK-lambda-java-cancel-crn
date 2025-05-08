package co.com.avc.models.dynamo;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * KeyDto
 * <p>
 * Objeto que guarda la información de la llave.
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
@AllArgsConstructor
@NoArgsConstructor
@Introspected
@SerdeImport(KeyDto.class)
public class KeyDto {

    /**
     * Tipo de llave.
     */
    private String keyType;

    /**
     * Valor de la llave.
     */
    private String keyId;

}
