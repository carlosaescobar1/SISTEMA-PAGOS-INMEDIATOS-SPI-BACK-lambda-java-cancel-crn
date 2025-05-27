package co.com.avc.mapper;

import co.com.ath.commons.util.Util;
import co.com.avc.constants.ConstantsEnum;
import co.com.avc.constants.DefaultValueEnum;
import co.com.ath.redebanconn.model.HeadersRq;
import co.com.avc.util.BankUUIDSelectorUItil;
import co.com.avc.util.IpSelectorUtil;
import co.com.avc.util.OriginSelectorUtil;
import co.com.ath.redebanconn.model.HeadersRq;
import co.com.avc.cornerconn.models.request.CornersHeadersRq;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
@AllArgsConstructor
public class    HeadersMapper {

    private final CornersHeadersRq headersRq = new CornersHeadersRq();


    public CornersHeadersRq headersMapper(String UUID) {


        headersRq.setRquid(UUID);

        return headersRq;

    }

}
