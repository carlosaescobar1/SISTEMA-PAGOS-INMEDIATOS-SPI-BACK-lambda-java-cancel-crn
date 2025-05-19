package co.com.avc.models;

import lombok.Getter;
import lombok.Setter;
/**
 * MessageDtoKeysCancel
 * <p>
 * Almacena información sobre la cancelación de una clave,
 * incluyendo el identificador de la clave (keyId) y el
 * nombre del archivo asociado (fileName).
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

public class MessageDtoKeysCancel {


    /**
     * nombre de la la llave
     */

    private String keyId;

    /**
     *  Nombre del archivo
     */
    private String fileName;





}

