package co.com.avc.service;

import co.com.ath.opensearch.logs.entity.index_key.OSIndexKey;
import co.com.avc.entity.Ath.DynamoSpiEntity;
import co.com.avc.mapper.IOpenSearchMapper;
import co.com.avc.mapper.IOpenSearchMapperImpl;
import co.com.ath.opensearch.sync.constants.SyncActionEnum;
import co.com.ath.opensearch.sync.service.IBlackListService;
import co.com.ath.opensearch.sync.service.IOpensearchService;
import co.com.avc.service.interfaces.IOpenSearchSynchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.client.opensearch.OpenSearchClient;

/**
 * OpenSearchSynchServiceImpl
 * <p>
 * Desarrollo ATH - SPBVI
 * <p>
 * Creado el: 09 de septiembre de 2024
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
 * <p>
 * Clase encargada de los procesos de sincronización y validacion en listas negras
 */

@Slf4j
@AllArgsConstructor
public class OpenSearchSynchServiceImpl implements IOpenSearchSynchService {

    /**
     * Instancia del servicio de opensearch de la librería de
     * sincronización
     */
    private final IOpensearchService opensearchService;

    /**
     * Instancia del cliente de opensearch
     */
    private final OpenSearchClient openSearchClient;
    private final int opSearchQuerySize;

    /**
     * Instancia del servicio de las listas negras de
     * la librería de sincronización
     */
    //private final IBlackListService blackListService;

    private final IOpenSearchMapper openSearchMapper = new IOpenSearchMapperImpl();
    
    @Override
    public void openSearchSyncCancel(DynamoSpiEntity dynamoSpiEntity) {
        opensearchService.syncKey(IOpenSearchMapper.INSTANCE.dynamoEntityToIndexKey(dynamoSpiEntity),
                SyncActionEnum.REMOVE, opSearchQuerySize);

    }

    @Override
    public void openSearchSyncCancel(OSIndexKey osIndexKey) {
        opensearchService.syncKey(osIndexKey,
                SyncActionEnum.REMOVE, opSearchQuerySize);

    }
}
