package co.com.avc.repository;

import co.com.ath.commons.util.ATHException;
import co.com.ath.commons.util.Util;
import co.com.avc.constants.ResponseServiceEnum;
import co.com.avc.entity.Ath.DynamoSpiEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import static co.com.avc.entity.Ath.DynamoSpiEntity.TABLE_SCHEMA_DYNAMO_SPI;
/**
 * DynamoRepository
 * <p>
 * Gestiona operaciones CRUD de objetos DynamoSpiEntity en DynamoDB.
 * <p>
 * Desarrollo ATH - AvalPay Center
 * <p>
 * Creado el : 25 de Julio de 2024
 * <p>
 * Autor: Luis F. Herreño Mateus
 * <p>
 * Requerimiento: Migración AvalPay Center
 * <p>
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 * <p>
 * Clase encargada de mapear la conexion con Dynamo
 *
 * @author Luis F Herreno
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@AllArgsConstructor
public class DynamoRepository {

    private final DynamoDbEnhancedClient client;
    private final String tableName;

    /**
     * Método que busca un objeto de tipo DynamoSpiEntity desde DynamoDB.
     * <p>
     * Recibe el ID y la clave secundaria (SK) del objeto a cargar.
     * <p>
     * Recibe los parámetros en el método migrateKeys clase MigrationKeyServiceImpl
     * y método updateAccountInformation clase UpdateKeyServiceImpl
     *
     * @param id ID del objeto a buscar
     * @param sk Clave secundaria (SK) del objeto a buscar
     * @return El objeto cargado de tipo DynamoSpiEntity
     */
    public DynamoSpiEntity load(String id, String sk) {
        log.info("Inicia busqueda (load) por ID y SK en DynamoDB");
        log.info("id: {}", id);
        log.info("sk: {}", sk);
        try {
            var table = client.table(tableName, TABLE_SCHEMA_DYNAMO_SPI);
            DynamoSpiEntity dynamoSpiEntity = table.getItem(Key.builder().partitionValue(id).sortValue(sk).build());

            if (dynamoSpiEntity == null) {
                return null;
            }

            log.info("Objeto recuperado: {}", Util.object2String(dynamoSpiEntity));
            return dynamoSpiEntity;
        } catch (Exception e) {
            log.error("Error en la busqueda por ID y SK DynamoDB: {}", e.getMessage());
            throw new ATHException(ResponseServiceEnum.ERROR_TEC_EXCEPTION_DYNAMO.getServerStatusCode(),
                    ResponseServiceEnum.ERROR_TEC_EXCEPTION_DYNAMO.getStatusDesc(),
                    ResponseServiceEnum.ERROR_TEC_EXCEPTION_DYNAMO.getStatusCode());
        }
    }

    /**
     * Método que elimina un objeto de tipo DynamoSpiEntity desde DynamoDB.
     * <p>
     * Recibe el objeto a eliminar.
     * <p>
     * Recibe los parámetros en el método updateKey clase UpdateKeyServiceImpl.
     *
     * @param dynamoSpiEntity El objeto a eliminar
     */
    public void delete(DynamoSpiEntity dynamoSpiEntity) {
        log.info("Inicia borrado (delete) en DynamoDB");
        log.info("table: {}", tableName);
        log.info("Registro a eliminar: {}", Util.object2String(dynamoSpiEntity));
        try {
            var table = client.table(tableName, TABLE_SCHEMA_DYNAMO_SPI);
            table.deleteItem(dynamoSpiEntity);
            log.info("Finaliza borrado (delete) de la llave en DynamoDB de forma exitosa");
        } catch (Exception e) {
            log.error("Error al eliminar en DynamoDB: {}", e.getMessage());
            throw new ATHException(ResponseServiceEnum.ERROR_TEC_EXCEPTION_DYNAMO.getServerStatusCode(),
                    ResponseServiceEnum.ERROR_TEC_EXCEPTION_DYNAMO.getStatusDesc(),
                    ResponseServiceEnum.ERROR_TEC_EXCEPTION_DYNAMO.getStatusCode());
        }
    }

    /**
     * Método que guarda o actualiza un objeto de tipo DynamoSpiEntity en DynamoDB.
     * <p>
     * Recibe el objeto a guardar o actualizar.
     * <p>
     * Recibe los parámetros en los métodos migrateKeys y grantConsentAndSave
     * clase MigrationKeyServiceImpl y los métodos updateProduct y updateKey clase
     * UpdateKeyServiceImpl.
     *
     * @param dynamoSpiEntity El objeto a guardar o actualizar
     */
    public void save(DynamoSpiEntity dynamoSpiEntity) {
        log.info("Inicia guardado/actualizacion (save) en DynamoDB");
        log.info("table: {}", tableName);
        log.info("Registro a guardar: {}", Util.object2String(dynamoSpiEntity));
        try {
            var table = client.table(tableName, TABLE_SCHEMA_DYNAMO_SPI);
            table.putItem(dynamoSpiEntity);
            log.info("Finaliza guardado/actualizacion (save) de la llave en DynamoDB de forma exitosa");
        } catch (Exception e) {
            log.error("Error al guardar en DynamoDB: {}", e.getMessage());
            throw new ATHException(ResponseServiceEnum.ERROR_TEC_EXCEPTION_DYNAMO.getServerStatusCode(),
                    ResponseServiceEnum.ERROR_TEC_EXCEPTION_DYNAMO.getStatusDesc(),
                    ResponseServiceEnum.ERROR_TEC_EXCEPTION_DYNAMO.getStatusCode());
        }
    }
}
