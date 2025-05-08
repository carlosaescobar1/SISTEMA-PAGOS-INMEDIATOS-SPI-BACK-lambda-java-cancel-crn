package co.com.avc.mapper;

import co.com.avc.entity.*;
import co.com.avc.models.dynamo.*;
import co.com.avc.entity.DynamoSpiEntity;
import co.com.avc.entity.KeyEntity;
import co.com.avc.models.dynamo.*;

public class DynamoMapperImpl implements IDynamoMapper {
    @Override
    public DynamoSpiEntity dynamoDtoToDynamoEntity(DynamoSpiDto dynamoSpiDto) {
        if (dynamoSpiDto == null) {
            return null;
        }

        DynamoSpiEntity dynamoSpiEntity = new DynamoSpiEntity();

        dynamoSpiEntity.setId(dynamoSpiDto.getId());
        dynamoSpiEntity.setSk(dynamoSpiDto.getSk());
        dynamoSpiEntity.setKey(keyDtoToKeyEntity(dynamoSpiDto.getKey()));
        dynamoSpiEntity.setCustInf(custInfDtoToCustInfEntity(dynamoSpiDto.getCustInf()));
        dynamoSpiEntity.setCustType(dynamoSpiDto.getCustType());
        dynamoSpiEntity.setIdentSerialNum(dynamoSpiDto.getIdentSerialNum());
        dynamoSpiEntity.setAcctInfo(acctInfoDtoToAcctInfoEntity(dynamoSpiDto.getAcctInfo()));
        dynamoSpiEntity.setEffDtCreate(dynamoSpiDto.getEffDtCreate());
        dynamoSpiEntity.setEffDtModify(dynamoSpiDto.getEffDtModify());
        dynamoSpiEntity.setEffDtConsent(dynamoSpiDto.getEffDtConsent());
        dynamoSpiEntity.setStatusKey(dynamoSpiDto.getStatusKey());
        dynamoSpiEntity.setPreferredIndicator(dynamoSpiDto.getPreferredIndicator());
        dynamoSpiEntity.setVaultNameRec(dynamoSpiDto.getVaultNameRec());
        dynamoSpiEntity.setConsent(dynamoSpiDto.getConsent());
        dynamoSpiEntity.setStatusDirectory(dynamoSpiDto.getStatusDirectory());
        dynamoSpiEntity.setSignerAlias(dynamoSpiDto.getSignerAlias());

        return dynamoSpiEntity;
    }

    protected KeyEntity keyDtoToKeyEntity(KeyDto keyDto) {
        if (keyDto == null) {
            return null;
        }

        KeyEntity keyEntity = new KeyEntity();

        keyEntity.setKeyType(keyDto.getKeyType());
        keyEntity.setKeyId(keyDto.getKeyId());

        return keyEntity;
    }

    protected CustIdentEntity custIdentDtoToCustIdentEntity(CustIdentDto custIdentDto) {
        if (custIdentDto == null) {
            return null;
        }

        CustIdentEntity custIdentEntity = new CustIdentEntity();

        custIdentEntity.setCustIdentType(custIdentDto.getCustIdentType());
        custIdentEntity.setCustIdentNum(custIdentDto.getCustIdentNum());

        return custIdentEntity;
    }

    protected CustInfEntity custInfDtoToCustInfEntity(CustInfDto custInfDto) {
        if (custInfDto == null) {
            return null;
        }

        CustInfEntity custInfEntity = new CustInfEntity();

        custInfEntity.setCustFirstName(custInfDto.getCustFirstName());
        custInfEntity.setCustSecondName(custInfDto.getCustSecondName());
        custInfEntity.setCustFirstLastName(custInfDto.getCustFirstLastName());
        custInfEntity.setCustSecondLastName(custInfDto.getCustSecondLastName());
        custInfEntity.setCustLegalName(custInfDto.getCustLegalName());
        custInfEntity.setCustIdent(custIdentDtoToCustIdentEntity(custInfDto.getCustIdent()));

        return custInfEntity;
    }

    protected AcctInfoEntity acctInfoDtoToAcctInfoEntity(AcctInfoDto acctInfoDto) {
        if (acctInfoDto == null) {
            return null;
        }

        AcctInfoEntity acctInfoEntity = new AcctInfoEntity();

        acctInfoEntity.setAcctType(acctInfoDto.getAcctType());
        acctInfoEntity.setAcctId(acctInfoDto.getAcctId());
        acctInfoEntity.setBankId(acctInfoDto.getBankId());

        return acctInfoEntity;
    }
}
