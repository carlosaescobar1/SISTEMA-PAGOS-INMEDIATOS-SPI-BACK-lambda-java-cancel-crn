package co.com.avc.models.parameter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParamBankUUIDTest {

    @Test
    void shouldSetAndGetBavvValue() {
        ParamBankUUID paramBankUUID = new ParamBankUUID();
        paramBankUUID.setBavv("UUID-BAVV");
        assertEquals("UUID-BAVV", paramBankUUID.getBavv());
    }

    @Test
    void shouldSetAndGetBbogValue() {
        ParamBankUUID paramBankUUID = new ParamBankUUID();
        paramBankUUID.setBbog("UUID-BBOG");
        assertEquals("UUID-BBOG", paramBankUUID.getBbog());
    }

    @Test
    void shouldSetAndGetBoccValue() {
        ParamBankUUID paramBankUUID = new ParamBankUUID();
        paramBankUUID.setBocc("UUID-BOCC");
        assertEquals("UUID-BOCC", paramBankUUID.getBocc());
    }

    @Test
    void shouldSetAndGetBpopValue() {
        ParamBankUUID paramBankUUID = new ParamBankUUID();
        paramBankUUID.setBpop("UUID-BPOP");
        assertEquals("UUID-BPOP", paramBankUUID.getBpop());
    }

    @Test
    void shouldSetAndGetDaleValue() {
        ParamBankUUID paramBankUUID = new ParamBankUUID();
        paramBankUUID.setDale("UUID-DALE");
        assertEquals("UUID-DALE", paramBankUUID.getDale());
    }

    @Test
    void shouldHandleNullValuesForBavv() {
        ParamBankUUID paramBankUUID = new ParamBankUUID();
        paramBankUUID.setBavv(null);
        assertNull(paramBankUUID.getBavv());
    }

    @Test
    void shouldHandleNullValuesForBbog() {
        ParamBankUUID paramBankUUID = new ParamBankUUID();
        paramBankUUID.setBbog(null);
        assertNull(paramBankUUID.getBbog());
    }

    @Test
    void shouldHandleNullValuesForBocc() {
        ParamBankUUID paramBankUUID = new ParamBankUUID();
        paramBankUUID.setBocc(null);
        assertNull(paramBankUUID.getBocc());
    }

    @Test
    void shouldHandleNullValuesForBpop() {
        ParamBankUUID paramBankUUID = new ParamBankUUID();
        paramBankUUID.setBpop(null);
        assertNull(paramBankUUID.getBpop());
    }

    @Test
    void shouldHandleNullValuesForDale() {
        ParamBankUUID paramBankUUID = new ParamBankUUID();
        paramBankUUID.setDale(null);
        assertNull(paramBankUUID.getDale());
    }
}