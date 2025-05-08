package co.com.avc.repository;

import co.com.ath.commons.util.SecretManagerUtil;
import co.com.avc.constants.GestionEnum;
import co.com.avc.models.SecretManagerDto;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;

/**
 * Desarrollo ATH - Sistema de pagos de Bajo Valor Inmediatos (SPBVI)
 * <p>
 * Creado el : 28 de agosto de 2024
 * <p>
 * Clase que almacenan los métodos de conexión a los
 * secretos de aws.
 *
 * <p>
 * Autor: Luis F. Herreño
 * <p>
 * Requerimiento: Sistema de pagos de Bajo Valor Inmediatos (SPBVI)
 * <p>
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 * <p>
 * Clase que permite la conexión hacia opensearch
 *
 * @version 1.0
 * @autor Luis F. Herreño
 */
@AllArgsConstructor
public class SecretManagerRepository {


    /**
     * Inyección de depedencia de la variable del arn
     * del secreto
     */
    private final String arnSecret;

    /**
     * Método encargado de obtener los secretos de aws.
     *
     * @return SecretManagerDto
     */
    public SecretManagerDto getSecrets() {

        SecretManagerDto secretManagerDto = new SecretManagerDto();

        JsonObject secretValues = SecretManagerUtil.getSecret(arnSecret);

        secretManagerDto.setPort(Integer.parseInt(SecretManagerUtil.getSecretValue(secretValues,
                GestionEnum.VAR_PORT.getValue())));

        secretManagerDto.setKeyword(SecretManagerUtil.getSecretValue(secretValues,
                GestionEnum.VAR_KEY.getValue()));

        secretManagerDto.setUsername(SecretManagerUtil.getSecretValue(secretValues,
                GestionEnum.VAR_USER.getValue()));

        secretManagerDto.setSchema(SecretManagerUtil.getSecretValue(secretValues,
                GestionEnum.VAR_SCHEMA.getValue()));

        secretManagerDto.setHostname(SecretManagerUtil.getSecretValue(secretValues,
                GestionEnum.VAR_HOST.getValue()));


        return secretManagerDto;
    }

}
