package co.com.avc.util;

import co.com.avc.constants.BankIdEnum;
import co.com.avc.models.parameter.ArnSnsOpenSearch;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SnsSelectorUtil {

    private final Map<String, String> snsArnMap;

    public SnsSelectorUtil(ArnSnsOpenSearch arnSnsOpenSearch) {
        snsArnMap = new HashMap<>();
        snsArnMap.put(BankIdEnum.BANCO_AV_VILLAS.getValue(), arnSnsOpenSearch.getArnSnsOpenSearchBAVV());
        snsArnMap.put(BankIdEnum.BANCO_DE_BOGOTA.getValue(), arnSnsOpenSearch.getArnSnsOpenSearchBBOG());
        snsArnMap.put(BankIdEnum.BANCO_DE_OCCIDENTE.getValue(), arnSnsOpenSearch.getArnSnsOpenSearchBOCC());
        snsArnMap.put(BankIdEnum.BANCO_POPULAR.getValue(), arnSnsOpenSearch.getArnSnsOpenSearchBPOP());
        snsArnMap.put(BankIdEnum.DALE.getValue(), arnSnsOpenSearch.getArnSnsOpenSearchDALE());
    }

    public String selectSNS(String bankId) {
        return snsArnMap.getOrDefault(bankId, null);
    }

}
