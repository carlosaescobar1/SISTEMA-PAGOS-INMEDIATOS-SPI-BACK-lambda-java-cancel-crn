package co.com.avc.mapper;

import co.com.avc.entity.DynamoSpiEntity;
import co.com.avc.models.dynamo.DynamoSpiDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface IDynamoMapper {
    IDynamoMapper INSTANCE = Mappers.getMapper( IDynamoMapper.class );
    DynamoSpiEntity dynamoDtoToDynamoEntity (DynamoSpiDto dynamoSpiDto);
    DynamoSpiDto dynamoEntityToDynamoDto(DynamoSpiEntity dynamoSpiEntity);

}
