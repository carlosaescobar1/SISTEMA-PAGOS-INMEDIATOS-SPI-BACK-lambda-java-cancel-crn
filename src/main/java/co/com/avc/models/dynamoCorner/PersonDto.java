package co.com.avc.models.dynamoCorner;


import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * PaymentMethodDto
 * <p>
 * Objeto que guarda la información de la persona.
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
@SerdeImport(PersonDto.class)
public class PersonDto {

    /**
     * Primer nombre cliente
     */
    private String first_name;

    /**
     * Segundo nombre cliente
     */
    private String second_name;

    /**
     * Primer apellido cliente
     */
    private String first_surname;

    /**
     * Segundo apellido cliente
     */
    private String second_surname;

    /**
     * Tipo de persona - Natural o Juridica
     */
    private String type_person;

    /**
     * Nombre de la empresa
     */
    private String businessName;

    /**
     * Tipo de documento
     */
    private String documentType;

    /**
     * Numero de documento
     */
    private String documentNumber;

}
