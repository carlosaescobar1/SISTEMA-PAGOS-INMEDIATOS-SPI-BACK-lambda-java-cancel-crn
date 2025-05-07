package co.com.ath.service;

import co.com.ath.entity.DynamoSpiEntity;

public interface IOpenSearchSynchService {

    void openSearchSyncEnroll(DynamoSpiEntity dynamoSpiEntity);
    void openSearchSyncBlackListValidate(String key);

}
