package co.com.avc.service;

import co.com.ath.commons.util.ATHException;
import co.com.ath.commons.util.Util;
import co.com.ath.commons.util.constants.MessagesEnum;
import co.com.avc.constants.ConstantsEnum;
import co.com.avc.constants.ResponseCodeEnum;
import co.com.avc.mapper.IndexBatchMapper;
import co.com.avc.mapper.IndexRejectedMapper;
import co.com.avc.models.MessageDto;
import co.com.avc.models.MessageDtoBatch;
import co.com.avc.models.dynamo.DynamoSpiDto;
import co.com.ath.opensearch.logs.constants.ActionConstants;
import co.com.ath.opensearch.logs.constants.IndexConstants;
import co.com.ath.opensearch.logs.entity.index_batch.OSIndexBatch;
import co.com.ath.opensearch.logs.entity.index_rejected.OSIndexRejected;
import co.com.ath.redebanconn.model.HeadersRq;
import co.com.avc.service.interfaces.IUpdateOpenSearchService;
import io.micronaut.context.ApplicationContext;
import io.micronaut.serde.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.opensearch.client.opensearch.core.IndexResponse;
import org.opensearch.client.opensearch.core.SearchTemplateResponse;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
public class UpdateOpenSearchServiceImpl implements IUpdateOpenSearchService {

    private final OpenSearchClient openSearchClient;

    private final ObjectMapper objectMapper = ApplicationContext.run().getBean(ObjectMapper.class);
    ;
    private final StringWriter errors = new StringWriter();

    private final IndexBatchMapper indexBatchMapper;

    private final IndexRejectedMapper indexRejectedMapper;

    private final int paramterRetry;


    @Override
    public void addElement(Object document, IndexConstants indexConstant) {


        try {
            Map<String, Object> jsonMap = objectMapper.readValue(Util.object2String(document), HashMap.class);

            log.info("Documento: {}", Util.object2StringWithNulls(document));

            IndexRequest<Map<String, Object>> request = IndexRequest.of(i -> i
                    .index(indexConstant.getValue())
                    .document(jsonMap));

            IndexResponse response = openSearchClient.index(request);
            log.info("El objeto se guardo exitosamente, objeto con el id: {}", response.id());
        } catch (Exception e) {
            log.error("Error al guardar el siguiente registro en OpenSearch: {}",
                    Util.object2StringWithNulls(document));
            log.error("{}{}", ConstantsEnum.ERROR_SAVE.getValue(), e.getMessage(), e);
            throw new ATHException(MessagesEnum.DEFAULT_ERROR_RESPONSE.getCode(),
                    MessagesEnum.DEFAULT_ERROR_RESPONSE.getMessage(),
                    MessagesEnum.DEFAULT_ERROR_RESPONSE.getHttpCode());
        }
    }

    @Override
    public void updateElement(Map<String, Object> jsonMap, String id) {

        log.info("ID OP EN ACTUALIZACION: {}", id);
        try {
            log.info("Realiza actualización Opensearch");

            openSearchClient.update(j -> j
                            .index(IndexConstants.SONDA_INDEX.getValue())
                            .id(id)
                            .doc(jsonMap)
                    , Map.class);

        } catch (Exception e) {
            e.printStackTrace(new PrintWriter(errors));
            log.error("Error al actualizar el siguiente registro en OpenSearch: {}",
                    jsonMap.toString());
            log.error("{}{}", ConstantsEnum.ERROR_CONNECTION.getValue(), errors);
            throw new ATHException(MessagesEnum.DEFAULT_ERROR_RESPONSE.getCode(),
                    MessagesEnum.DEFAULT_ERROR_RESPONSE.getMessage(),
                    MessagesEnum.DEFAULT_ERROR_RESPONSE.getHttpCode());
        }
    }

    public long searchKey(DynamoSpiDto dynamoSpiDto) {


        SearchTemplateResponse<HashMap> searchResponse;
        long hitsSize;

        Map<String, JsonData> params = new HashMap<>();

        params.put(ConstantsEnum.OP_PARAMETER_KEY_TYPE.getValue(),
                JsonData.of(dynamoSpiDto.getKey().getKeyType()));

        params.put(ConstantsEnum.OP_PARAMETER_KEY_VALUE.getValue(),
                JsonData.of(dynamoSpiDto.getKey().getKeyId()));

        try {
            searchResponse = openSearchClient.searchTemplate(s -> s
                            .index(IndexConstants.DYNAMO_INDEX.getValue())
                            .id(ConstantsEnum.INDEX_KEY_TEMPLATE.getValue())
                            .params(params)
                    ,
                    HashMap.class);

            hitsSize = searchResponse.hits().total().value();

        } catch (Exception e) {
            e.printStackTrace(new PrintWriter(errors));
            log.error("{}{}", ConstantsEnum.ERROR_CONNECTION.getValue(), errors.toString());
            throw new ATHException(MessagesEnum.DEFAULT_ERROR_RESPONSE.getCode(),
                    MessagesEnum.DEFAULT_ERROR_RESPONSE.getMessage(),
                    MessagesEnum.DEFAULT_ERROR_RESPONSE.getHttpCode());
        }
        return hitsSize;

    }

    public long searchBatch(DynamoSpiDto dynamoSpiDto) {

        SearchTemplateResponse<HashMap> searchResponse;
        long hitsSize;

        Map<String, JsonData> params = new HashMap<>();

        params.put(ConstantsEnum.OP_PARAMETER_KEY_VALUE.getValue(),
                JsonData.of(dynamoSpiDto.getKey().getKeyId()));

        try {
            searchResponse = openSearchClient.searchTemplate(s -> s
                            .index(IndexConstants.SONDA_INDEX.getValue())
                            .id(ConstantsEnum.INDEX_BATCH_BY_KEY_TEMPLATE.getValue())
                            .params(params)
                    ,
                    HashMap.class);

            hitsSize = searchResponse.hits().total().value();

        } catch (Exception e) {
            e.printStackTrace(new PrintWriter(errors));
            log.error("{}{}", ConstantsEnum.ERROR_CONNECTION.getValue(), errors.toString());
            throw new ATHException(MessagesEnum.DEFAULT_ERROR_RESPONSE.getCode(),
                    MessagesEnum.DEFAULT_ERROR_RESPONSE.getMessage(),
                    MessagesEnum.DEFAULT_ERROR_RESPONSE.getHttpCode());
        }
        return hitsSize;

    }

    public Map<String, Object> updateRetryMapper(
            OSIndexBatch osIndexBatch,
            String fileName,
            String errorType,
            String errorDesc,
            String rqId
    ) {

        int newRetry = (int) osIndexBatch.getRetryBatch() + 1;
        log.info("Reintento actual: {}", newRetry);

        Map<String, Object> jsonMap = new HashMap<>();

        if (newRetry < paramterRetry) {

            jsonMap.put("retryBatch", newRetry);
            jsonMap.put("blockedBatch", ConstantsEnum.UNLOCK_BATCH.getValue());

        } else {

            jsonMap.put("blockedBatch", ConstantsEnum.BLOCK_BATCH.getValue());
            jsonMap.put("statusBatch", ConstantsEnum.FAILED_BATCH.getValue());
            jsonMap.put("retryBatch", newRetry);

            DynamoSpiDto dynamoSpiDto = (DynamoSpiDto)
                    Util.string2objectWhitNulls(osIndexBatch.getRqServiceObject(),
                            DynamoSpiDto.class);

            saveIndexRejected(dynamoSpiDto, fileName, errorType, errorDesc, rqId, osIndexBatch.getRqId());

        }

        return jsonMap;
    }

    public Map<String, Object> updateSuccessRetryMapper(MessageDtoBatch messageDtoBatch) {


        int newRetry = (int) messageDtoBatch.getOsIndexBatch().getRetryBatch() + 1;

        log.info("Reintento actual: {}", newRetry);

        Map<String, Object> jsonMap = new HashMap<>();


        jsonMap.put("blockedBatch", ConstantsEnum.BLOCK_BATCH.getValue());
        jsonMap.put("statusBatch", ConstantsEnum.SUCCESS_BATCH.getValue());
        jsonMap.put("retryBatch", newRetry);

        updateElement(jsonMap, messageDtoBatch.getIdOpensearch());

        return jsonMap;
    }

    public Map<String, Object> updateBadTryFirstAttemp(OSIndexBatch osIndexBatch, String idOpensearch) {

        log.info("Inicio updateBadTryFirstAttemp");

        int newRetry = (int) osIndexBatch.getRetryBatch() + 1;

        log.info("Reintento actual: {}", newRetry);

        Map<String, Object> jsonMap = new HashMap<>();

        jsonMap.put("blockedBatch", ConstantsEnum.BLOCK_BATCH.getValue());
        jsonMap.put("statusBatch", ConstantsEnum.FAILED_BATCH.getValue());
        jsonMap.put("retryBatch", newRetry);

        updateElement(jsonMap, idOpensearch);

        return jsonMap;
    }


    @Override
    public void saveIndexRejected(
            DynamoSpiDto dynamoSpiDto,
            String fileName,
            String errorType,
            String errorDesc,
            String rqId,
            String rqUUID
    ) {
        OSIndexRejected osIndexRejected = indexRejectedMapper.indexRejected(
                dynamoSpiDto,
                fileName,
                errorType,
                errorDesc,
                rqId,
                rqUUID
        );
        log.info("OSIndexRejected: {}", Util.object2StringWithNulls(osIndexRejected));
        addElement(osIndexRejected, IndexConstants.REJECTED_INDEX);
    }

    @Override
    public void processOpensearchAction(MessageDto messageDto, DynamoSpiDto dynamoSpiDto, HeadersRq headersRq,
                                        String fileName, String errorType, String errorDesc, String rqId, String subject) {

        OSIndexBatch osIndexBatch = indexBatchMapper.mapEnrollmentRqToIndexBatch(dynamoSpiDto,
                headersRq, ActionConstants.EVENT_BATCH_ENROLL.getValue(), fileName);

        if (errorType.equals(ResponseCodeEnum.RED_PERSON_ERROR_CREATED_STATUS_CODE.getValue())
                || errorType.equals(ResponseCodeEnum.RED_PERSON_ERROR_BODY_STATUS_CODE.getValue())) {

            log.info("ENTRO A INDICE DE RECHAZADOS");
            saveIndexRejected(dynamoSpiDto, fileName, errorType, errorDesc, rqId, osIndexBatch.getRqId());
            if (subject.equalsIgnoreCase(ConstantsEnum.SUBJECT_BATCH.getValue())) {
                updateBadTryFirstAttemp(osIndexBatch, messageDto.getMessageDtoBatch().getIdOpensearch());
            }

            return;
        }

        if (messageDto.getMessageDtoBatch() != null) {

            updateElement(
                    updateRetryMapper(
                            messageDto.getMessageDtoBatch().getOsIndexBatch(),
                            fileName, errorType, errorDesc, rqId
                    ),
                    messageDto.getMessageDtoBatch().getIdOpensearch()
            );

        } else {

            addElement(osIndexBatch, IndexConstants.SONDA_INDEX);

        }
    }

    @Override
    public void processSuccessBatchAction(MessageDto messageDto) {

        if (messageDto.getMessageDtoBatch() != null) {

            updateElement(
                    updateSuccessRetryMapper(
                            messageDto.getMessageDtoBatch()
                    ),
                    messageDto.getMessageDtoBatch().getIdOpensearch()
            );

        } else {
            return;
        }

    }

    @Override
    public void processSuccessDirAvalAction(MessageDto messageDto,
                                            String fileName,
                                            String errorType,
                                            String errorDesc,
                                            String rqId,
                                            String rqUUID) {

        DynamoSpiDto dynamoSpiDto = getDynamoSpiDto(messageDto);


        OSIndexBatch osIndexBatch = indexBatchMapper.mapEnrollmentRqToIndexBatch(dynamoSpiDto,
                rqUUID,
                ActionConstants.EVENT_BATCH_ENROLL.getValue(),
                fileName);


        if (messageDto.getMessageDtoBatch() != null) {

            updateElement(
                    updateRetryMapper(messageDto.getMessageDtoBatch().getOsIndexBatch(),
                            fileName, errorType, errorDesc, rqId
                    ),
                    messageDto.getMessageDtoBatch().getIdOpensearch());

        } else {

            addElement(osIndexBatch, IndexConstants.SONDA_INDEX);

        }

    }

    @Override
    public void processOpensearchAction(MessageDto messageDto,
                                        String fileName,
                                        String errorType,
                                        String errorDesc,
                                        String rqId,
                                        String rqUUID) {

        DynamoSpiDto dynamoSpiDto = getDynamoSpiDto(messageDto);

        OSIndexBatch osIndexBatch = indexBatchMapper.mapEnrollmentRqToIndexBatch(dynamoSpiDto,
                rqUUID,
                ActionConstants.EVENT_BATCH_ENROLL.getValue(),
                fileName);


        if (messageDto.getMessageDtoBatch() != null) {

            updateElement(
                    updateRetryMapper(messageDto.getMessageDtoBatch().getOsIndexBatch(),
                            fileName, errorType, errorDesc, rqId),
                    messageDto.getMessageDtoBatch().getIdOpensearch());

        } else {

            addElement(osIndexBatch, IndexConstants.SONDA_INDEX);

        }
    }

    private DynamoSpiDto getDynamoSpiDto(MessageDto messageDto) {

        return messageDto.getMessageDtoBatch() != null ?
                (DynamoSpiDto) Util.string2object(
                        messageDto.getMessageDtoBatch().getOsIndexBatch().getRqServiceObject(),
                        DynamoSpiDto.class) :
                messageDto.getMessageDtoDynamo().getDynamoSpiDto();

    }

}
