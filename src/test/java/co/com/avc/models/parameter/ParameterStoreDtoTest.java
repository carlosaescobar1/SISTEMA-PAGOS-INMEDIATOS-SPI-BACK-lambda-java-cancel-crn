package co.com.avc.models.parameter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParameterStoreDtoTest {

    @Test
    void shouldSetAndGetArnSnsOpenSearchValue() {
        ParameterStoreDto parameterStoreDto = new ParameterStoreDto();
        ArnSnsOpenSearch arnSnsOpenSearch = new ArnSnsOpenSearch();
        parameterStoreDto.setArnSnsOpenSearch(arnSnsOpenSearch);
        assertEquals(arnSnsOpenSearch, parameterStoreDto.getArnSnsOpenSearch());
    }

    @Test
    void shouldSetAndGetVaultServicesTimeOutValue() {
        ParameterStoreDto parameterStoreDto = new ParameterStoreDto();
        VaultServicesTimeOut vaultServicesTimeOut = new VaultServicesTimeOut();
        parameterStoreDto.setVaultServicesTimeOut(vaultServicesTimeOut);
        assertEquals(vaultServicesTimeOut, parameterStoreDto.getVaultServicesTimeOut());
    }

    @Test
    void shouldSetAndGetArnSecretOpenSearchValue() {
        ParameterStoreDto parameterStoreDto = new ParameterStoreDto();
        parameterStoreDto.setArnSecretOpenSearch("arn:secret:test");
        assertEquals("arn:secret:test", parameterStoreDto.getArnSecretOpenSearch());
    }

    @Test
    void shouldSetAndGetParamDynamoValue() {
        ParameterStoreDto parameterStoreDto = new ParameterStoreDto();
        ParamDynamo paramDynamo = new ParamDynamo();
        parameterStoreDto.setParamDynamo(paramDynamo);
        assertEquals(paramDynamo, parameterStoreDto.getParamDynamo());
    }

    @Test
    void shouldSetAndGetParamActiveVaultValue() {
        ParameterStoreDto parameterStoreDto = new ParameterStoreDto();
        ParamActiveVault paramActiveVault = new ParamActiveVault();
        parameterStoreDto.setParamActiveVault(paramActiveVault);
        assertEquals(paramActiveVault, parameterStoreDto.getParamActiveVault());
    }

    @Test
    void shouldSetAndGetRedebanConfigDtoValue() {
        ParameterStoreDto parameterStoreDto = new ParameterStoreDto();
        RedebanConfigDto redebanConfigDto = new RedebanConfigDto();
        parameterStoreDto.setRedebanConfigDto(redebanConfigDto);
        assertEquals(redebanConfigDto, parameterStoreDto.getRedebanConfigDto());
    }

    @Test
    void shouldSetAndGetRegionValue() {
        ParameterStoreDto parameterStoreDto = new ParameterStoreDto();
        parameterStoreDto.setRegion("us-east-1");
        assertEquals("us-east-1", parameterStoreDto.getRegion());
    }

    @Test
    void shouldSetAndGetParamFlowConfigValue() {
        ParameterStoreDto parameterStoreDto = new ParameterStoreDto();
        ParamFlowConfig paramFlowConfig = new ParamFlowConfig();
        parameterStoreDto.setParamFlowConfig(paramFlowConfig);
        assertEquals(paramFlowConfig, parameterStoreDto.getParamFlowConfig());
    }

    @Test
    void shouldSetAndGetMaxRetryBatchValue() {
        ParameterStoreDto parameterStoreDto = new ParameterStoreDto();
        parameterStoreDto.setMaxRetryBatch(5);
        assertEquals(5, parameterStoreDto.getMaxRetryBatch());
    }

    @Test
    void shouldSetAndGetBlackListScoreValue() {
        ParameterStoreDto parameterStoreDto = new ParameterStoreDto();
        parameterStoreDto.setBlackListScore(0.85);
        assertEquals(0.85, parameterStoreDto.getBlackListScore());
    }

    @Test
    void shouldSetAndGetOpSearchQuerySizeValue() {
        ParameterStoreDto parameterStoreDto = new ParameterStoreDto();
        parameterStoreDto.setOpSearchQuerySize(100);
        assertEquals(100, parameterStoreDto.getOpSearchQuerySize());
    }

    @Test
    void shouldHandleNullValuesForArnSecretOpenSearch() {
        ParameterStoreDto parameterStoreDto = new ParameterStoreDto();
        parameterStoreDto.setArnSecretOpenSearch(null);
        assertNull(parameterStoreDto.getArnSecretOpenSearch());
    }

    @Test
    void shouldHandleNullValuesForRegion() {
        ParameterStoreDto parameterStoreDto = new ParameterStoreDto();
        parameterStoreDto.setRegion(null);
        assertNull(parameterStoreDto.getRegion());
    }
}