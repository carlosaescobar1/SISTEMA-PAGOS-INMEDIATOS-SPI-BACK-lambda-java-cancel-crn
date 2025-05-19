package co.com.avc.models.parameter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ParamActiveVaultTest {

    @Test
    void shouldSetAndGetVaultRec() {
        ParamActiveVault paramActiveVault = new ParamActiveVault();
        ParamVaultRec vaultRec = new ParamVaultRec();
        paramActiveVault.setVaultRec(vaultRec);
        assertEquals(vaultRec, paramActiveVault.getVaultRec());
    }

    @Test
    void shouldSetAndGetVaultsUpload() {
        ParamActiveVault paramActiveVault = new ParamActiveVault();
        ParamVaultUpload vaultUpload = new ParamVaultUpload();
        paramActiveVault.setVaultsUpload(List.of(vaultUpload));
        assertEquals(1, paramActiveVault.getVaultsUpload().size());
        assertEquals(vaultUpload, paramActiveVault.getVaultsUpload().get(0));
    }



    @Test
    void shouldHandleEmptyVaultsUploadList() {
        ParamActiveVault paramActiveVault = new ParamActiveVault();
        assertTrue(paramActiveVault.getVaultsUpload().isEmpty());
    }

    @Test
    void shouldHandleNullVaultRec() {
        ParamActiveVault paramActiveVault = new ParamActiveVault();
        paramActiveVault.setVaultRec(null);
        assertNull(paramActiveVault.getVaultRec());
    }


}