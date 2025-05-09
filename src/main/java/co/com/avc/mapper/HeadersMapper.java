package co.com.avc.mapper;


import co.com.avc.constants.DefaultValueEnum;
import co.com.ath.cornerconn.models.CornersHeadersRq;
import lombok.AllArgsConstructor;


import java.util.List;

@AllArgsConstructor
public class HeadersMapper {

    private final CornersHeadersRq headersRq = new CornersHeadersRq();

    public CornersHeadersRq headersMapper() {


        headersRq.setContentType(DefaultValueEnum.APPLICATION_JSON.getValue());

        return headersRq;

    }

}
