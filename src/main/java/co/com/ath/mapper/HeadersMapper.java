package co.com.ath.mapper;

import co.com.ath.constants.ConstantsEnum;
import co.com.ath.constants.DefaultValueEnum;
import co.com.ath.models.parameter.OriginDto;
import co.com.ath.models.parameter.ParamBankUUID;
import co.com.ath.redebanconn.model.HeadersRq;
import co.com.ath.util.BankUUIDSelectorUItil;
import co.com.ath.util.IpSelectorUtil;
import co.com.ath.util.OriginSelectorUtil;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class HeadersMapper {

    private final HeadersRq headersRq = new HeadersRq();

    private final ParamBankUUID paramBankUUID;
    private final IpSelectorUtil ipSelectorUtil;
    private final List<OriginDto> listOriginDto;

    public HeadersRq headersMapper(String bankId, String rqId, String UUID, String dateOperation) {

        BankUUIDSelectorUItil bankUUIDSelectorUItil = new BankUUIDSelectorUItil(paramBankUUID);
        OriginSelectorUtil originSelectorUtil = new OriginSelectorUtil(listOriginDto);

        headersRq.setDate(dateOperation);
        headersRq.setForwardedFor(ipSelectorUtil.getIp(bankId));
        headersRq.setContentType(DefaultValueEnum.APPLICATION_JSON.getValue());
        headersRq.setRequestId(UUID);
        headersRq.setRbmFrom(bankUUIDSelectorUItil.getBankUUID(bankId));
        headersRq.setOrigin(originSelectorUtil.originSelector(bankId));
        headersRq.setRqUID(rqId);
        headersRq.setChannel(ConstantsEnum.HEADER_CHANNEL.getValue());
        headersRq.setAccept(DefaultValueEnum.APPLICATION_JSON.getValue());

        return headersRq;

    }

}
