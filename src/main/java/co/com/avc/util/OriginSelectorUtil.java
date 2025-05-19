package co.com.avc.util;

import co.com.avc.constants.BankIdEnum;
import co.com.avc.models.parameter.OriginDto;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class OriginSelectorUtil {

    Map<String, String> selector;

    public OriginSelectorUtil(List<OriginDto> originDto) {
        selector = new HashMap<>();
        selector.put(BankIdEnum.BANCO_AV_VILLAS.getValue(), originDto.get(0).getOffices());
        selector.put(BankIdEnum.BANCO_DE_BOGOTA.getValue(), originDto.get(1).getOffices());
        selector.put(BankIdEnum.BANCO_DE_OCCIDENTE.getValue(), originDto.get(2).getOffices());
        selector.put(BankIdEnum.BANCO_POPULAR.getValue(), originDto.get(3).getOffices());
        selector.put(BankIdEnum.DALE.getValue(), originDto.get(4).getOffices());
    }

    public String originSelector(String bankId) {
        return selector.getOrDefault(bankId, null);
    }

}
