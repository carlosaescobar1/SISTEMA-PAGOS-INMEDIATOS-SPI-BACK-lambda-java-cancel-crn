package co.com.avc.models;

import co.com.avc.models.dynamoAth.DynamoSpiDto;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;
/**
 * MessageDtoDynamo
 * <p>
 * Es una estructura de datos que encapsula un objeto DynamoSpiDto
 * y un nombre de archivo (fileName). Se utiliza para serializar y gestionar
 * datos relacionados con registros de DynamoDB
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
@SerdeImport(MessageDtoDynamo.class)
public class MessageDtoDynamo {

    private DynamoSpiDto dynamoSpiDto;

    private String fileName;

}
