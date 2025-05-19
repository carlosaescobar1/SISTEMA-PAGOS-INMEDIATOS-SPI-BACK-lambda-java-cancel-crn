package co.com.avc.models.parameter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParamDynamoTest {

    @Test
    void shouldSetAndGetEndPointValue() {
        ParamDynamo paramDynamo = new ParamDynamo();
        paramDynamo.setEndPoint("http://dynamo-endpoint");
        assertEquals("http://dynamo-endpoint", paramDynamo.getEndPoint());
    }

    @Test
    void shouldSetAndGetNameTableValue() {
        ParamDynamo paramDynamo = new ParamDynamo();
        paramDynamo.setNameTable("DynamoTable");
        assertEquals("DynamoTable", paramDynamo.getNameTable());
    }

    @Test
    void shouldHandleNullEndPointValue() {
        ParamDynamo paramDynamo = new ParamDynamo();
        paramDynamo.setEndPoint(null);
        assertNull(paramDynamo.getEndPoint());
    }

    @Test
    void shouldHandleNullNameTableValue() {
        ParamDynamo paramDynamo = new ParamDynamo();
        paramDynamo.setNameTable(null);
        assertNull(paramDynamo.getNameTable());
    }
}