package co.com.avc.models.parameter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OriginDtoTest {

    @Test
    void shouldSetAndGetMobileBankingValue() {
        OriginDto originDto = new OriginDto();
        originDto.setMobileBanking("Mobile Value");
        assertEquals("Mobile Value", originDto.getMobileBanking());
    }

    @Test
    void shouldSetAndGetVirtualBankingValue() {
        OriginDto originDto = new OriginDto();
        originDto.setVirtualBanking("Virtual Value");
        assertEquals("Virtual Value", originDto.getVirtualBanking());
    }

    @Test
    void shouldSetAndGetOfficesValue() {
        OriginDto originDto = new OriginDto();
        originDto.setOffices("Office Value");
        assertEquals("Office Value", originDto.getOffices());
    }

    @Test
    void shouldHandleNullValuesForMobileBanking() {
        OriginDto originDto = new OriginDto();
        originDto.setMobileBanking(null);
        assertNull(originDto.getMobileBanking());
    }

    @Test
    void shouldHandleNullValuesForVirtualBanking() {
        OriginDto originDto = new OriginDto();
        originDto.setVirtualBanking(null);
        assertNull(originDto.getVirtualBanking());
    }

    @Test
    void shouldHandleNullValuesForOffices() {
        OriginDto originDto = new OriginDto();
        originDto.setOffices(null);
        assertNull(originDto.getOffices());
    }
}