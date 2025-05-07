package co.com.ath.mapper;

import co.com.ath.commons.util.Util;
import co.com.ath.constants.ConstantsEnum;
import co.com.ath.models.dynamo.DynamoSpiDto;
import co.com.ath.opensearch.logs.constants.TypeServiceConstants;
import co.com.ath.opensearch.logs.entity.index_timeline.OSIndexTimeline;
import co.com.ath.redebanconn.model.Customer;
import co.com.ath.redebanconn.model.HeadersRq;
import co.com.ath.redebanconn.model.enrollment.EnrollmentRq;
import co.com.ath.util.IpSelectorUtil;

public class IndexTimeLineMapper {

    /**
     * Instancia del objeto que representa el indice index_timeline de Open search
     */
    private OSIndexTimeline osIndexTimeline = new OSIndexTimeline();

    private final IpSelectorUtil ipSelectorUtil;

    public IndexTimeLineMapper(IpSelectorUtil ipSelectorUtil) {
        this.ipSelectorUtil = ipSelectorUtil;
    }

    /**
     * Método que mapea el objeto de entrada de la solicitud de registro (Enrollment)
     * al objeto que representa el índice de time_line en Open search.
     *
     * @param enrollmentEnterpriseRq modelo que representa el request de la
     *                               petición de creación en línea.
     * @param typeService            Variable que representa el tipo de log que se quiere guardar
     *                               Request (Rq) o Response (Rs) usado para asignar la fecha
     *                               de la operación.
     * @return Objeto OSIndexTimeline con la información mapeada.
     */
    /*public OSIndexTimeline mapEnrollmentToTimeLine(EnrollmentEnterpriseRq enrollmentEnterpriseRq,
                                                   HeadersRq headersRq,
                                                   TypeServiceConstants typeService) {

        osIndexTimeline.setRqId(getRqId(headersRq));

        osIndexTimeline.setKeyType(getKeyType(enrollmentEnterpriseRq));

        osIndexTimeline.setKeyId(getKeyId(enrollmentEnterpriseRq));

        osIndexTimeline.setDateOperation(getDateOperation(headersRq, typeService));

        osIndexTimeline.setCompanyId(getCompanyId(enrollmentEnterpriseRq));

        osIndexTimeline.setBankId(getBankId(enrollmentEnterpriseRq));

        osIndexTimeline.setChanel(getChanel());

        osIndexTimeline.setIp(getIp(headersRq));

        return osIndexTimeline;
    }*/

    /**
     * Método que mapea el objeto de entrada de la solicitud de registro (Enrollment)
     * al objeto que representa el índice de time_line en Open search.
     *
     * @param enrollmentRq modelo que representa el request de la
     *                     petición de creación en línea.
     * @param typeService  Variable que representa el tipo de log que se quiere guardar
     *                     Request (Rq) o Response (Rs) usado para asignar la fecha
     *                     de la operación.
     * @return Objeto OSIndexTimeline con la información mapeada.
     */
    public OSIndexTimeline mapEnrollmentToTimeLine(EnrollmentRq enrollmentRq,
                                                   HeadersRq headersRq,
                                                   TypeServiceConstants typeService) {

        osIndexTimeline.setRqId(getRqId(headersRq));

        osIndexTimeline.setKeyType(getKeyType(enrollmentRq.getCustomer()));

        osIndexTimeline.setKeyId(getKeyId(enrollmentRq.getCustomer()));

        osIndexTimeline.setDateOperation(getDateOperation(headersRq, typeService));

        osIndexTimeline.setCompanyId(getCompanyId(enrollmentRq));

        osIndexTimeline.setBankId(getBankId(enrollmentRq));

        osIndexTimeline.setChanel(getChanel());

        osIndexTimeline.setIp(getIp(headersRq));

        return osIndexTimeline;
    }

    private String getRqId(HeadersRq headersRq) {
        return headersRq != null
                && headersRq.getRequestId() != null
                ? headersRq.getRequestId()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }

    /*private String getKeyType(EnrollmentEnterpriseRq enrollmentEnterpriseRq) {
        return enrollmentEnterpriseRq != null
                && enrollmentEnterpriseRq.getComeList() != null
                && enrollmentEnterpriseRq.getComeList().getCommerce() != null
                && !enrollmentEnterpriseRq.getComeList().getCommerce().isEmpty()
                && enrollmentEnterpriseRq.getComeList().getCommerce().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())) != null
                && enrollmentEnterpriseRq.getComeList().getCommerce().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())).getPartySystemIdentifier() != null
                ? enrollmentEnterpriseRq.getComeList().getCommerce().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())).getPartySystemIdentifier()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }*/

    /*private String getKeyId(EnrollmentEnterpriseRq enrollmentEnterpriseRq) {
        return enrollmentEnterpriseRq != null
                && enrollmentEnterpriseRq.getComeList() != null
                && enrollmentEnterpriseRq.getComeList().getCommerce() != null
                && !enrollmentEnterpriseRq.getComeList().getCommerce().isEmpty()
                && enrollmentEnterpriseRq.getComeList().getCommerce().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())) != null
                && enrollmentEnterpriseRq.getComeList().getCommerce().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())).getPartyIdentifier() != null
                ? enrollmentEnterpriseRq.getComeList().getCommerce().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())).getPartyIdentifier()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }*/

    private String getDateOperation(HeadersRq headersRq,
                                    TypeServiceConstants typeService) {
        if (typeService.equals(TypeServiceConstants.REQUEST)) {
            return headersRq != null
                    && headersRq.getDate() != null
                    ? headersRq.getDate()
                    : Util.createDate();
        } else {
            return Util.createDate();
        }
    }

    /*private String getCompanyId(EnrollmentEnterpriseRq enrollmentEnterpriseRq) {
        return enrollmentEnterpriseRq != null
                && enrollmentEnterpriseRq.getComeList() != null
                && enrollmentEnterpriseRq.getComeList().getCommerce() != null
                && !enrollmentEnterpriseRq.getComeList().getCommerce().isEmpty()
                && enrollmentEnterpriseRq.getComeList().getCommerce().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())) != null
                && enrollmentEnterpriseRq.getComeList().getCommerce().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())).getProducts() != null
                && enrollmentEnterpriseRq.getComeList().getCommerce().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())).getProducts().getAccounts() != null
                && enrollmentEnterpriseRq.getComeList().getCommerce().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())).getProducts().getAccounts().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())) != null
                && enrollmentEnterpriseRq.getComeList().getCommerce().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())).getProducts().getAccounts().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())).getBankId() != null
                ? enrollmentEnterpriseRq.getComeList().getCommerce().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())).getProducts().getAccounts().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())).getBankId()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }*/

    /*private String getBankId(EnrollmentEnterpriseRq enrollmentEnterpriseRq) {
        return enrollmentEnterpriseRq != null
                && enrollmentEnterpriseRq.getComeList() != null
                && enrollmentEnterpriseRq.getComeList().getCommerce() != null
                && !enrollmentEnterpriseRq.getComeList().getCommerce().isEmpty()
                && enrollmentEnterpriseRq.getComeList().getCommerce().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())) != null
                && enrollmentEnterpriseRq.getComeList().getCommerce().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())).getProducts() != null
                && enrollmentEnterpriseRq.getComeList().getCommerce().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())).getProducts().getAccounts() != null
                && enrollmentEnterpriseRq.getComeList().getCommerce().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())).getProducts().getAccounts().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())) != null
                && enrollmentEnterpriseRq.getComeList().getCommerce().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())).getProducts().getAccounts().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())).getBankId() != null
                ? enrollmentEnterpriseRq.getComeList().getCommerce().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())).getProducts().getAccounts().get(Integer.parseInt(ConstantsEnum.FIRST_ARRAY_ITEM.getValue())).getBankId()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }*/

    private String getIp(HeadersRq headersRq) {
        return headersRq != null
                && headersRq.getForwardedFor() != null
                ? headersRq.getForwardedFor()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }


    private String getKeyType(Customer customer) {
        return customer != null
                && customer.getPartySystemIdentifier() != null
                ? customer.getPartySystemIdentifier()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }

    private String getKeyId(Customer customer) {
        return customer != null
                && customer.getPartyIdentifier() != null
                ? customer.getPartyIdentifier()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }

    private String getCompanyId(EnrollmentRq enrollmentRq) {
        return enrollmentRq != null
                && enrollmentRq.getProduct() != null
                && enrollmentRq.getProduct().getAccount() != null
                && enrollmentRq.getProduct().getAccount().getBankId() != null
                ? enrollmentRq.getProduct().getAccount().getBankId()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }

    private String getBankId(EnrollmentRq enrollmentRq) {
        return enrollmentRq != null
                && enrollmentRq.getProduct() != null
                && enrollmentRq.getProduct().getAccount() != null
                && enrollmentRq.getProduct().getAccount().getBankId() != null
                ? enrollmentRq.getProduct().getAccount().getBankId()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }


    public OSIndexTimeline mapDynamoToTimeLine(DynamoSpiDto dynamoSpiDto, String rqUUID) {

        osIndexTimeline.setRqId(rqUUID);

        osIndexTimeline.setKeyType(getKeyType(dynamoSpiDto));

        osIndexTimeline.setKeyId(getKeyId(dynamoSpiDto));

        osIndexTimeline.setDateOperation(Util.createDate());

        osIndexTimeline.setCompanyId(getCompanyId(dynamoSpiDto));

        osIndexTimeline.setBankId(getBankId(dynamoSpiDto));

        osIndexTimeline.setChanel(getChanel());

        osIndexTimeline.setIp(getIp(dynamoSpiDto));

        return osIndexTimeline;
    }


    private String getKeyType(DynamoSpiDto dynamoSpiDto) {
        return dynamoSpiDto != null
                && dynamoSpiDto.getKey() != null
                && dynamoSpiDto.getKey().getKeyType() != null
                ? dynamoSpiDto.getKey().getKeyType()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }

    private String getKeyId(DynamoSpiDto dynamoSpiDto) {
        return dynamoSpiDto != null
                && dynamoSpiDto.getKey() != null
                && dynamoSpiDto.getKey().getKeyId() != null
                ? dynamoSpiDto.getKey().getKeyId()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }

    private String getCompanyId(DynamoSpiDto dynamoSpiDto) {
        return dynamoSpiDto != null
                && dynamoSpiDto.getAcctInfo() != null
                && dynamoSpiDto.getAcctInfo().getBankId() != null
                ? dynamoSpiDto.getAcctInfo().getBankId()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }

    private String getBankId(DynamoSpiDto dynamoSpiDto) {
        return dynamoSpiDto != null
                && dynamoSpiDto.getAcctInfo() != null
                && dynamoSpiDto.getAcctInfo().getBankId() != null
                ? dynamoSpiDto.getAcctInfo().getBankId()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }


    private String getChanel() {
        return ConstantsEnum.HEADER_CHANNEL.getValue();
    }

    private String getIp(DynamoSpiDto dynamoSpiDto) {
        return dynamoSpiDto != null
                && dynamoSpiDto.getAcctInfo() != null
                && dynamoSpiDto.getAcctInfo().getBankId() != null
                && !dynamoSpiDto.getAcctInfo().getBankId().trim().isEmpty()
                ? ipSelectorUtil.getIp(dynamoSpiDto.getAcctInfo().getBankId())
                : ConstantsEnum.EMPTY_STRING.getValue();
    }


}
