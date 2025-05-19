package co.com.avc.util;

import co.com.avc.constants.BankIdEnum;
import co.com.avc.models.parameter.ParamBankUUID;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BankUUIDSelectorUItil {

    Map<String, String> selector;

    public BankUUIDSelectorUItil(ParamBankUUID paramBankUUID) {
        selector = new HashMap<>();
        selector.put(BankIdEnum.BANCO_AV_VILLAS.getValue(), paramBankUUID.getBavv());
        selector.put(BankIdEnum.BANCO_DE_BOGOTA.getValue(), paramBankUUID.getBbog());
        selector.put(BankIdEnum.BANCO_DE_OCCIDENTE.getValue(), paramBankUUID.getBocc());
        selector.put(BankIdEnum.BANCO_POPULAR.getValue(), paramBankUUID.getBpop());
        selector.put(BankIdEnum.DALE.getValue(), paramBankUUID.getDale());
    }

    public String getBankUUID(String bankId) {
        return selector.getOrDefault(bankId, null);
    }

}
