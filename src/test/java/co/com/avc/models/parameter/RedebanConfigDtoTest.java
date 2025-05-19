package co.com.avc.models.parameter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class RedebanConfigDtoTest {

    @Test
    void shouldSetAndGetOriginDtoListValue() {
        RedebanConfigDto redebanConfigDto = new RedebanConfigDto();
        OriginDto originDto1 = new OriginDto();
        OriginDto originDto2 = new OriginDto();
        List<OriginDto> originDtoList = List.of(originDto1, originDto2);
        redebanConfigDto.setOriginDtoList(originDtoList);
        assertEquals(originDtoList, redebanConfigDto.getOriginDtoList());
    }

    @Test
    void shouldSetAndGetParamIpValue() {
        RedebanConfigDto redebanConfigDto = new RedebanConfigDto();
        ParamIp paramIp = new ParamIp();
        redebanConfigDto.setParamIp(paramIp);
        assertEquals(paramIp, redebanConfigDto.getParamIp());
    }

    @Test
    void shouldSetAndGetParamBankUUIDValue() {
        RedebanConfigDto redebanConfigDto = new RedebanConfigDto();
        ParamBankUUID paramBankUUID = new ParamBankUUID();
        redebanConfigDto.setParamBankUUID(paramBankUUID);
        assertEquals(paramBankUUID, redebanConfigDto.getParamBankUUID());
    }

    @Test
    void shouldHandleNullValuesForOriginDtoList() {
        RedebanConfigDto redebanConfigDto = new RedebanConfigDto();
        redebanConfigDto.setOriginDtoList(null);
        assertNull(redebanConfigDto.getOriginDtoList());
    }

    @Test
    void shouldHandleNullValuesForParamIp() {
        RedebanConfigDto redebanConfigDto = new RedebanConfigDto();
        redebanConfigDto.setParamIp(null);
        assertNull(redebanConfigDto.getParamIp());
    }

    @Test
    void shouldHandleNullValuesForParamBankUUID() {
        RedebanConfigDto redebanConfigDto = new RedebanConfigDto();
        redebanConfigDto.setParamBankUUID(null);
        assertNull(redebanConfigDto.getParamBankUUID());
    }
}