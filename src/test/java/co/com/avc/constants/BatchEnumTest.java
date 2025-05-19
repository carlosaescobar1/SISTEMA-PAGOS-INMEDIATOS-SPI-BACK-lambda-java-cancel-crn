package co.com.avc.constants;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BatchEnumTest {

    @Test
    void shouldReturnCorrectValueForBatchIndex() {
        assertEquals("index_batch", BatchEnum.BATCH_INDEX.getValue());
    }

    @Test
    void shouldReturnCorrectValueForBatchIndexByKeyTemplate() {
        assertEquals("TemplateBatchByKey", BatchEnum.BATCH_INDEX_BY_KEY_TEMPLATE.getValue());
    }

    @Test
    void shouldReturnCorrectValueForBatchBlock() {
        assertEquals("1", BatchEnum.BATCH_BLOCK.getValue());
    }

    @Test
    void shouldReturnCorrectValueForBatchUnlock() {
        assertEquals("0", BatchEnum.BATCH_UNLOCK.getValue());
    }

    @Test
    void shouldReturnCorrectValueForBatchFailed() {
        assertEquals("Fallido", BatchEnum.BATCH_FAILED.getValue());
    }

    @Test
    void shouldReturnCorrectValueForBatchSuccess() {
        assertEquals("Exitoso", BatchEnum.BATCH_SUCCESS.getValue());
    }

    @Test
    void shouldReturnCorrectValueForBatchPending() {
        assertEquals("Pendiente", BatchEnum.BATCH_PENDING.getValue());
    }

    @Test
    void shouldReturnCorrectValueForBatchCancel() {
        assertEquals("Cancelado", BatchEnum.BATCH_CANCEL.getValue());
    }

    @Test
    void shouldReturnCorrectValueForBatchStatus() {
        assertEquals("statusBatch", BatchEnum.BATCH_STATUS.getValue());
    }

    @Test
    void shouldReturnCorrectValueForBatchSubject() {
        assertEquals("batch", BatchEnum.BATCH_SUBJECT.getValue());
    }

    @Test
    void shouldReturnCorrectValueForBatchCancelSubject() {
        assertEquals("CANC", BatchEnum.BATCH_CANCEL_SUBJECT.getValue());
    }

    @Test
    void shouldReturnCorrectValueForBatchEventConsent() {
        assertEquals("consent", BatchEnum.BATCH_EVENT_CONSENT.getValue());
    }
}