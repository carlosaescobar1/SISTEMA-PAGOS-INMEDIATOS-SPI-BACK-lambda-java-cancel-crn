package co.com.avc.models.dynamo;


import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
