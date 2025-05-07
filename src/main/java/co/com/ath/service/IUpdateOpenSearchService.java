package co.com.ath.service;

import co.com.ath.models.MessageDto;
import co.com.ath.models.dynamo.DynamoSpiDto;
import co.com.ath.opensearch.logs.constants.IndexConstants;
import co.com.ath.redebanconn.model.HeadersRq;

import java.util.Map;

/**
 * IUpdateOpenSearchService
 * <p>
 * Desarrollo ATH - Sistema de pagos de Bajo Valor Inmediatos (SPBVI)
 * <p>
 * Creado el : 28 de agosto de 2024
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
public interface IUpdateOpenSearchService {

    void addElement(Object document, IndexConstants indexConstant);

    void updateElement(Map<String, Object> jsonMap, String id);

    long searchKey(DynamoSpiDto dynamoSpiDto);

    long searchBatch(DynamoSpiDto dynamoSpiDto);

    void processOpensearchAction(
            MessageDto messageDto,
            DynamoSpiDto dynamoSpiDto,
            HeadersRq headersRq,
            String fileName,
            String errorType,
            String errorDesc,
            String rqId,
            String subject
    );

    void saveIndexRejected(
            DynamoSpiDto dynamoSpiDto,
            String fileName,
            String errorType,
            String errorDesc,
            String rqId,
            String rqUUID
    );

    void processOpensearchAction(
            MessageDto messageDto,
            String fileName,
            String errorType,
            String errorDesc,
            String rqId,
            String rqUUID
    );

    void processSuccessBatchAction(MessageDto messageDto);


    void processSuccessDirAvalAction(MessageDto messageDto,
                                     String fileName,
                                     String errorType,
                                     String errorDesc,
                                     String rqId,
                                     String rqUUID);


}
