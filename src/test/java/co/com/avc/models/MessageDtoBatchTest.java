package co.com.avc.models;

import co.com.ath.opensearch.logs.entity.index_batch.OSIndexBatch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageDtoBatchTest {

    @Test
    public void testMessageDtoBatchGettersAndSetters() {
        // Crear instancia de OSIndexBatch
        OSIndexBatch osIndexBatch = new OSIndexBatch();

        // Crear instancia de MessageDtoBatch
        MessageDtoBatch messageDtoBatch = new MessageDtoBatch();

        // Establecer valores
        String idOpensearch = "test-id";
        messageDtoBatch.setIdOpensearch(idOpensearch);
        messageDtoBatch.setOsIndexBatch(osIndexBatch);

        // Verificar valores
        assertEquals(idOpensearch, messageDtoBatch.getIdOpensearch());
        assertEquals(osIndexBatch, messageDtoBatch.getOsIndexBatch());
    }
}