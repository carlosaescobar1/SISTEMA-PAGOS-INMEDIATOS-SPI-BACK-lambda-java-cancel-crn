package co.com.avc.models.parameter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VaultServicesTimeOutTest {

    @Test
    void shouldSetAndGetRedDeleteTimeOutValue() {
        VaultServicesTimeOut vaultServicesTimeOut = new VaultServicesTimeOut();
        vaultServicesTimeOut.setRedDeleteTimeOut(30);
        assertEquals(30, vaultServicesTimeOut.getRedDeleteTimeOut());
    }

    @Test
    void shouldHandleZeroValueForRedDeleteTimeOut() {
        VaultServicesTimeOut vaultServicesTimeOut = new VaultServicesTimeOut();
        vaultServicesTimeOut.setRedDeleteTimeOut(0);
        assertEquals(0, vaultServicesTimeOut.getRedDeleteTimeOut());
    }

    @Test
    void shouldHandleNegativeValueForRedDeleteTimeOut() {
        VaultServicesTimeOut vaultServicesTimeOut = new VaultServicesTimeOut();
        vaultServicesTimeOut.setRedDeleteTimeOut(-10);
        assertEquals(-10, vaultServicesTimeOut.getRedDeleteTimeOut());
    }
}