package co.com.avc.mapper;

import co.com.ath.commons.util.Util;
import co.com.avc.constants.ConstantsEnum;
import co.com.ath.cornerconn.constants.PersonTypeEnum;
import co.com.avc.models.MessageDto;
import co.com.avc.models.MessageDtoBatch;
import co.com.avc.models.MessageDtoDynamo;
import co.com.avc.models.SqsDto;
import co.com.avc.models.dynamoAth.DynamoSpiDto;
import co.com.ath.redebanconn.constants.RedDefaultValuesEnum;
import co.com.ath.redebanconn.model.*;
import co.com.ath.redebanconn.model.enrollment.EnrollmentRq;
import lombok.extern.slf4j.Slf4j;
/**
 * RequestMapper
 * <p>
 * mapea datos de entrada desde mensajes SQS (SqsDto) a objetos
 * como MessageDto, DynamoSpiDto y EnrollmentRq para procesar solicitudes
 * <p>
 * Desarrollo ATH - SPBVI
 * <p>
 * Creado él: 09 de septiembre de 2024
 *
 * @author Luis F. Herreño Mateus
 * @version 1.0
 * @since 1.0
 * <p>
 * Requerimiento: SPBVI - Sistema de pagos de bajo valor inmediatos
 * <p>
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 */
@Slf4j
public class RequestMapper {

    /**
     * Instancia del objeto del body de redeban
     */
    private final EnrollmentRq enrollmentRq = new EnrollmentRq();

    /**
     * Instandia del objeto de dynamo
     */
    private DynamoSpiDto dynamoSpiDto = new DynamoSpiDto();

    /**
     * @param sqsDto
     * @return
     */
    public MessageDto messageDtoMapper(SqsDto sqsDto) {
        MessageDto messageDto = new MessageDto();
        //Obtiene el subject del sqs que lelga y verifica  si es sonda-batch o migración
        if (sqsDto.getSubject().equalsIgnoreCase(ConstantsEnum.SUBJECT_BATCH.getValue())) {
            log.info("messageDtoMapper ingreso if sonda");
            //Se convierte de string a un objeto de la clase MessageDtoBatch
            MessageDtoBatch messageDtoBatch = (MessageDtoBatch) Util.string2objectWhitNulls(sqsDto.getMessage(), MessageDtoBatch.class);
            messageDto.setMessageDtoBatch(messageDtoBatch);

            //Si no es sonda-batch, se verifica si el subject es migración, y se transforma de string a un objeto de la clase MessageDtoDynamo
        } else if (sqsDto.getSubject().contains(ConstantsEnum.SUBJECT_MIGRATION.getValue())) {
            log.info("messageDtoMapper ingreso if migración");
            MessageDtoDynamo messageDtoDynamo = new MessageDtoDynamo();
            //Encapsula el objeto de la clase MessageDtoDynamo
            messageDtoDynamo.setFileName(sqsDto.getSubject());
            //Se convierte de string a un objeto de la clase DynamoSpiDto
            DynamoSpiDto dynamoSpiDto1 = (DynamoSpiDto) Util.string2objectWhitNulls(sqsDto.getMessage(), DynamoSpiDto.class);
            //Se asigna el objeto de la clase DynamoSpiDto al objeto de la clase MessageDtoDynamo
            messageDtoDynamo.setDynamoSpiDto(dynamoSpiDto1);
            messageDto.setMessageDtoDynamo(messageDtoDynamo);
        }
        log.info("messageDtoMapper: {}", Util.object2StringWithNulls(messageDto));
        return messageDto;
    }
    //Aqui llego el messageDto que contiene el dynamoSpiDto
    public DynamoSpiDto redirectDynamoData(MessageDto messageDto, String subject, String dateOperation) {

        if (subject.equalsIgnoreCase(ConstantsEnum.SUBJECT_BATCH.getValue())) {
            dynamoSpiDto = (DynamoSpiDto) Util.string2objectWhitNulls(
                    messageDto.getMessageDtoBatch().getOsIndexBatch().getRqServiceObject(),
                    DynamoSpiDto.class);
        } else {
            dynamoSpiDto = messageDto.getMessageDtoDynamo().getDynamoSpiDto();
        }
        dynamoSpiDto.setVaultNameRec(ConstantsEnum.REDEBAN.getValue());
        dynamoSpiDto.setEffDtCreate(dateOperation);

        log.info("redirectDynamoData: " + Util.object2StringWithNulls(dynamoSpiDto));

        return dynamoSpiDto;
    }


    /*public EnrollmentRq bodyMapper(DynamoSpiDto dynamoSpiDto, String subject) {

        Person person = new Person();

        Product product = new Product();

        Account account = new Account();
        account.setBankId(dynamoSpiDto.getAcctInfo().getBankId());
        account.setAccountNo(dynamoSpiDto.getAcctInfo().getAcctId());
        account.setTypeAccount(dynamoSpiDto.getAcctInfo().getAcctType());

        product.setAccount(account);

        enrollmentRq.setProduct(product);
        person.setFirstName(dynamoSpiDto.getCustInf().getCustFirstName());
        person.setMiddleName(
                (!dynamoSpiDto.getCustInf().getCustSecondName().trim().isEmpty())
                        ? dynamoSpiDto.getCustInf().getCustSecondName() : null
        );
        person.setFirstSurName(dynamoSpiDto.getCustInf().getCustFirstLastName());
        person.setMiddleSurName(
                (!dynamoSpiDto.getCustInf().getCustSecondLastName().trim().isEmpty())
                        ? dynamoSpiDto.getCustInf().getCustSecondLastName() : null
        );
        person.setDocumentNumber(dynamoSpiDto.getCustInf().getCustIdent().getCustIdentNum());
        person.setDocumentType(dynamoSpiDto.getCustInf().getCustIdent().getCustIdentType());

        Customer customer = new Customer();

        enrollmentRq.setTermsAndConditions(true);
        customer.setPartyIdentifier(dynamoSpiDto.getKey().getKeyId());
        customer.setPartySystemIdentifier(dynamoSpiDto.getKey().getKeyType());

        PersonContact personContact = new PersonContact();

        if (dynamoSpiDto.getKey().getKeyType().equals(ConstantsEnum.CEL.getValue())) {
            personContact.setMobileNumber(dynamoSpiDto.getKey().getKeyId());
        } else {
            personContact.setMobileNumber(RedDefaultValuesEnum.RED_DEFAULT_MOVIL_NUMBER.getValue());
        }

        customer.setPerson(person);

        person.setPersonContact(personContact);
        enrollmentRq.setRequestDateTime(dynamoSpiDto.getEffDtCreate());

        enrollmentRq.setCustomer(customer);


        log.info("Body redeban sin traducir: {}", Util.object2StringWithNulls(enrollmentRq));
        return enrollmentRq;
    }*/




    //Este EnrollmentRq es la clase principal del modelo de la libreria en redeban,
    //Clase EnrollmentRq que es el modelo del body del request de redeban
    public EnrollmentRq bodyMapper(DynamoSpiDto dynamoSpiDto) {
        log.info("Entra a bodyMapper");

        enrollmentRq.setProduct(getProduct(dynamoSpiDto));
        enrollmentRq.setCustomer(getCustomer(dynamoSpiDto));
        enrollmentRq.setTermsAndConditions(true);
        enrollmentRq.setRequestDateTime(dynamoSpiDto.getEffDtCreate());

        log.info("Body redeban sin traducir: {}", Util.object2StringWithNulls(enrollmentRq));
        return enrollmentRq;
    }

    private Product getProduct(DynamoSpiDto dynamoSpiDto) {
        Product product = new Product();
        product.setAccount(getAccount(dynamoSpiDto));
        return product;
    }

    private Account getAccount(DynamoSpiDto dynamoSpiDto) {
        Account account = new Account();
        account.setBankId(dynamoSpiDto.getAcctInfo().getBankId());
        account.setAccountNo(dynamoSpiDto.getAcctInfo().getAcctId());
        account.setTypeAccount(dynamoSpiDto.getAcctInfo().getAcctType());
        return account;
    }

    private Customer getCustomer(DynamoSpiDto dynamoSpiDto) {
        Customer customer = new Customer();
        customer.setPartyIdentifier(dynamoSpiDto.getKey().getKeyId());
        customer.setPartySystemIdentifier(dynamoSpiDto.getKey().getKeyType());
        customer.setType(dynamoSpiDto.getCustType());

        if (dynamoSpiDto.getCustType() != null && dynamoSpiDto.getCustType().equalsIgnoreCase(PersonTypeEnum.PJ.getCornerValue())) {
            log.info("Persona JURIDICA, se envia COMMERCE {}", Util.object2String(getCommerce(dynamoSpiDto)));
            customer.setCommerce(getCommerce(dynamoSpiDto));

        } else if (dynamoSpiDto.getCustType() != null && dynamoSpiDto.getCustType().equalsIgnoreCase(PersonTypeEnum.PN.getCornerValue())) {
            log.info("Persona NATURAL, se envia PERSON {}", Util.object2String(getPerson(dynamoSpiDto)));
            customer.setPerson(getPerson(dynamoSpiDto));
        }

        return customer;
    }

    private Person getPerson(DynamoSpiDto dynamoSpiDto) {
        Person person = new Person();
        person.setFirstName(dynamoSpiDto.getCustInf().getCustFirstName());
        person.setMiddleName(
                dynamoSpiDto.getCustInf().getCustSecondName() != null
                        ? dynamoSpiDto.getCustInf().getCustSecondName() : null
        );
        person.setFirstSurName(dynamoSpiDto.getCustInf().getCustFirstLastName());
        person.setMiddleSurName(
                dynamoSpiDto.getCustInf().getCustSecondLastName() != null
                        ? dynamoSpiDto.getCustInf().getCustSecondLastName() : null
        );
        person.setDocumentNumber(dynamoSpiDto.getCustInf().getCustIdent().getCustIdentNum());
        person.setDocumentType(dynamoSpiDto.getCustInf().getCustIdent().getCustIdentType());
        person.setPersonContact(getPersonContact(dynamoSpiDto));
        return person;
    }

    private PersonContact getPersonContact(DynamoSpiDto dynamoSpiDto) {
        PersonContact personContact = new PersonContact();

        if (dynamoSpiDto.getKey().getKeyType().equals(ConstantsEnum.CEL.getValue())) {
            personContact.setMobileNumber(dynamoSpiDto.getKey().getKeyId());
        } else {
            personContact.setMobileNumber(RedDefaultValuesEnum.RED_DEFAULT_MOVIL_NUMBER.getValue());
        }

        return personContact;
    }

    private Commerce getCommerce(DynamoSpiDto dynamoSpiDto) {
        Commerce commerce = new Commerce();
        commerce.setDocumentNumber(dynamoSpiDto.getCustInf().getCustIdent().getCustIdentNum());
        commerce.setDocumentType(dynamoSpiDto.getCustInf().getCustIdent().getCustIdentType());
        commerce.setMerchantId(dynamoSpiDto.getMerchantId() != null ?
                dynamoSpiDto.getMerchantId() : null);
        return commerce;
    }


//    public ConsentRq consentBodyMapper(EnrollmentRq enrollmentRq) {
//
//        consentRq.setKeyType(enrollmentRq.getPartySystemIdentifier());
//        consentRq.setKeyValue(enrollmentRq.getPartyIdentifier());
//
//        return consentRq;
//    }

}
