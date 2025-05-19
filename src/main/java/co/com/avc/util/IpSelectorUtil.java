package co.com.avc.util;

import co.com.avc.constants.BankIdEnum;
import co.com.avc.models.parameter.ParamIp;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class IpSelectorUtil {

    Map<String, String> selector;

    public IpSelectorUtil(ParamIp paramIp) {
        selector = new HashMap<>();
        selector.put(BankIdEnum.BANCO_AV_VILLAS.getValue(), paramIp.getBavv());
        selector.put(BankIdEnum.BANCO_DE_BOGOTA.getValue(), paramIp.getBbog());
        selector.put(BankIdEnum.BANCO_DE_OCCIDENTE.getValue(), paramIp.getBocc());
        selector.put(BankIdEnum.BANCO_POPULAR.getValue(), paramIp.getBpop());
        selector.put(BankIdEnum.DALE.getValue(), paramIp.getDale());
    }

    public String getIp(String bankId) {
        return selector.getOrDefault(bankId, null);
    }


}
