package co.com.avc.mapper;

import co.com.avc.constants.ConstantsEnum;
import co.com.avc.constants.DefaultValueEnum;
import co.com.avc.models.parameter.OriginDto;
import co.com.avc.models.parameter.ParamBankUUID;
import co.com.ath.redebanconn.model.HeadersRq;
import co.com.avc.util.BankUUIDSelectorUItil;
import co.com.avc.util.IpSelectorUtil;
import co.com.avc.util.OriginSelectorUtil;
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
