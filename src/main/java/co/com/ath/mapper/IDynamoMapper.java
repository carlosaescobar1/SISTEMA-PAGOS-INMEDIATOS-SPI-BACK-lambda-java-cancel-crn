package co.com.ath.mapper;

import co.com.ath.entity.DynamoSpiEntity;
import co.com.ath.models.dynamo.DynamoSpiDto;
import org.mapstruct.factory.Mappers;

public interface IDynamoMapper {
    IDynamoMapper INSTANCE = Mappers.getMapper(IDynamoMapper.class);

    DynamoSpiEntity dynamoDtoToDynamoEntity(DynamoSpiDto dynamoSpiDto);

}
