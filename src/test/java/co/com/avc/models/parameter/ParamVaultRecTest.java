package co.com.avc.models.parameter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParamVaultRecTest {

    @Test
    void shouldSetAndGetVaultNameRecValue() {
        ParamVaultRec paramVaultRec = new ParamVaultRec();
        paramVaultRec.setVaultNameRec("VaultNameRecValue");
        assertEquals("VaultNameRecValue", paramVaultRec.getVaultNameRec());
    }
}