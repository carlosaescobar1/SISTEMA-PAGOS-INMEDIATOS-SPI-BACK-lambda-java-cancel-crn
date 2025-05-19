package co.com.avc.models.parameter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParamVaultUploadTest {

    @Test
    void shouldSetAndGetVaultNameValue() {
        ParamVaultUpload paramVaultUpload = new ParamVaultUpload();
        paramVaultUpload.setVaultName("VaultNameValue");
        assertEquals("VaultNameValue", paramVaultUpload.getVaultName());
    }

}