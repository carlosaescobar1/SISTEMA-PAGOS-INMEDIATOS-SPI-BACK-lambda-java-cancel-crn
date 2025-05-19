package co.com.avc.mapper;

import co.com.avc.entity.Ath.DynamoSpiEntity;
import co.com.ath.opensearch.logs.entity.index_key.OSIndexKey;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
 * IOpenSearchMapper
 * <p>
 * Define un mapeador para convertir un objeto
 * DynamoSpiEntity (entidad de DynamoDB) en un
 * objeto OSIndexKey para su uso en un índice de OpenSearch
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
@Mapper
public interface IOpenSearchMapper {

    IOpenSearchMapper INSTANCE = Mappers.getMapper(IOpenSearchMapper.class);
    OSIndexKey dynamoEntityToIndexKey(DynamoSpiEntity dynamoSpiDto);

}
