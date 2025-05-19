package co.com.avc.models.parameter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParamIpTest {

    @Test
    void shouldSetAndGetBavvValue() {
        ParamIp paramIp = new ParamIp();
        paramIp.setBavv("192.168.1.1");
        assertEquals("192.168.1.1", paramIp.getBavv());
    }

    @Test
    void shouldSetAndGetBbogValue() {
        ParamIp paramIp = new ParamIp();
        paramIp.setBbog("192.168.1.2");
        assertEquals("192.168.1.2", paramIp.getBbog());
    }

    @Test
    void shouldSetAndGetBoccValue() {
        ParamIp paramIp = new ParamIp();
        paramIp.setBocc("192.168.1.3");
        assertEquals("192.168.1.3", paramIp.getBocc());
    }

    @Test
    void shouldSetAndGetBpopValue() {
        ParamIp paramIp = new ParamIp();
        paramIp.setBpop("192.168.1.4");
        assertEquals("192.168.1.4", paramIp.getBpop());
    }

    @Test
    void shouldSetAndGetDaleValue() {
        ParamIp paramIp = new ParamIp();
        paramIp.setDale("192.168.1.5");
        assertEquals("192.168.1.5", paramIp.getDale());
    }

    @Test
    void shouldHandleNullValuesForBavv() {
        ParamIp paramIp = new ParamIp();
        paramIp.setBavv(null);
        assertNull(paramIp.getBavv());
    }

    @Test
    void shouldHandleNullValuesForBbog() {
        ParamIp paramIp = new ParamIp();
        paramIp.setBbog(null);
        assertNull(paramIp.getBbog());
    }

    @Test
    void shouldHandleNullValuesForBocc() {
        ParamIp paramIp = new ParamIp();
        paramIp.setBocc(null);
        assertNull(paramIp.getBocc());
    }

    @Test
    void shouldHandleNullValuesForBpop() {
        ParamIp paramIp = new ParamIp();
        paramIp.setBpop(null);
        assertNull(paramIp.getBpop());
    }

    @Test
    void shouldHandleNullValuesForDale() {
        ParamIp paramIp = new ParamIp();
        paramIp.setDale(null);
        assertNull(paramIp.getDale());
    }
}