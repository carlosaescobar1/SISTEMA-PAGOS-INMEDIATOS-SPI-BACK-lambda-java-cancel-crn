package co.com.avc.models.parameter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParamFlowConfigTest {

    @Test
    void shouldSetAndGetConsentFlowValue() {
        ParamFlowConfig paramFlowConfig = new ParamFlowConfig();
        paramFlowConfig.setConsentFlow("ConsentFlowValue");
        assertEquals("ConsentFlowValue", paramFlowConfig.getConsentFlow());
    }

    @Test
    void shouldSetAndGetVaultSyncFlowValue() {
        ParamFlowConfig paramFlowConfig = new ParamFlowConfig();
        paramFlowConfig.setVaultSyncFlow("VaultSyncFlowValue");
        assertEquals("VaultSyncFlowValue", paramFlowConfig.getVaultSyncFlow());
    }

    @Test
    void shouldHandleNullValuesForConsentFlow() {
        ParamFlowConfig paramFlowConfig = new ParamFlowConfig();
        paramFlowConfig.setConsentFlow(null);
        assertNull(paramFlowConfig.getConsentFlow());
    }

    @Test
    void shouldHandleNullValuesForVaultSyncFlow() {
        ParamFlowConfig paramFlowConfig = new ParamFlowConfig();
        paramFlowConfig.setVaultSyncFlow(null);
        assertNull(paramFlowConfig.getVaultSyncFlow());
    }
}