package co.com.avc.service.interfaces;

import co.com.ath.opensearch.logs.entity.index_key.OSIndexKey;
import co.com.avc.entity.DynamoSpiEntity;

public interface IOpenSearchSynchService {

    void openSearchSyncCancel(DynamoSpiEntity dynamoSpiEntity);
    void openSearchSyncCancel(OSIndexKey osIndexKey)  ;

}
