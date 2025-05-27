package co.com.avc.service;

import co.com.ath.commons.util.ATHException;
import co.com.ath.commons.util.Util;
import co.com.ath.commons.util.constants.MessagesEnum;
import co.com.avc.constants.BatchEnum;
import co.com.avc.constants.ConstantsEnum;
import co.com.avc.constants.ResponseCodeEnum;
import co.com.avc.mapper.IndexBatchMapper;
import co.com.avc.mapper.IndexRejectedMapper;
import co.com.avc.models.MessageDto;
import co.com.avc.models.MessageDtoBatch;
import co.com.avc.models.dynamoAth.DynamoSpiDto;
import co.com.ath.opensearch.logs.constants.ActionConstants;
import co.com.ath.opensearch.logs.constants.IndexConstants;
import co.com.ath.opensearch.logs.entity.index_batch.OSIndexBatch;
import co.com.ath.opensearch.logs.entity.index_rejected.OSIndexRejected;
import co.com.ath.redebanconn.model.HeadersRq;
import co.com.avc.service.interfaces.IUpdateOpenSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.ApplicationContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.OpenSearchException;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.opensearch.client.opensearch.core.IndexResponse;
import org.opensearch.client.opensearch.core.SearchTemplateResponse;
import org.opensearch.client.opensearch.core.UpdateResponse;
import org.opensearch.client.opensearch.core.search.Hit;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static co.com.avc.constants.BatchEnum.*;

@Slf4j
@AllArgsConstructor
public class UpdateOpenSearchServiceImpl implements IUpdateOpenSearchService {

    private final OpenSearchClient openSearchClient;

    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper = new ObjectMapper();

    private final StringWriter errors = new StringWriter();

    private final IndexBatchMapper indexBatchMapper;

    private final IndexRejectedMapper indexRejectedMapper;

    private final int paramterRetry;


    @Override
    public void addElement(Object document, IndexConstants indexConstant) {

        Map<String, Object> jsonMap = objectMapper.convertValue(document, HashMap.class);

        IndexRequest<Map<String, Object>> request = IndexRequest.of(i -> i
                .index(indexConstant.getValue())
                .document(jsonMap));
        try {
            IndexResponse response = openSearchClient.index(request);
            log.info("El objeto se guardo exitosamente, objeto con el id: {}", response.id());
        } catch (IOException e) {
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

    public long searchKey(String keyId, String keyType) {


        SearchTemplateResponse<HashMap> searchResponse;
        long hitsSize;

        Map<String, JsonData> params = new HashMap<>();

        params.put(ConstantsEnum.OP_PARAMETER_KEY_TYPE.getValue(),
                JsonData.of(keyType));

        params.put(ConstantsEnum.OP_PARAMETER_KEY_VALUE.getValue(),
                JsonData.of(keyId));

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
            log.error("{}{}", ConstantsEnum.ERROR_CONNECTION.getValue(), errors);
            throw new ATHException(MessagesEnum.DEFAULT_ERROR_RESPONSE.getCode(),
                    MessagesEnum.DEFAULT_ERROR_RESPONSE.getMessage(),
                    MessagesEnum.DEFAULT_ERROR_RESPONSE.getHttpCode());
        }
        return hitsSize;

    }

    @Override
    public long searchBatch(DynamoSpiDto dynamoSpiDto) {

        SearchTemplateResponse<HashMap> searchResponse;
        long hitsSize;

        Map<String, JsonData> params = new HashMap<>();

        params.put(ConstantsEnum.OP_PARAMETER_KEY_VALUE.getValue(),
                JsonData.of(dynamoSpiDto.getKey().getKeyId()));

        try {
            searchResponse = openSearchClient.searchTemplate(s -> s
                            .index(IndexConstants.SONDA_INDEX.getValue())
                            .id(BatchEnum.BATCH_INDEX_BY_KEY_TEMPLATE.getValue())
                            .params(params)
                    ,
                    HashMap.class);

            hitsSize = searchResponse.hits().total().value();


        } catch (Exception e) {
            e.printStackTrace(new PrintWriter(errors));
            log.error("{}{}", ConstantsEnum.ERROR_CONNECTION.getValue(), errors);
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
            jsonMap.put("blockedBatch", BATCH_UNLOCK.getValue());


        } else {
            log.info("Ingreso a indice de Rechazados");
            jsonMap.put("blockedBatch", BATCH_BLOCK.getValue());
            jsonMap.put("statusBatch", BATCH_FAILED.getValue());
            jsonMap.put("retryBatch", newRetry);

            DynamoSpiDto dynamoSpiDto = (DynamoSpiDto)
                    Util.string2objectWhitNulls(osIndexBatch.getRqServiceObject(),
                            DynamoSpiDto.class);

            saveIndexRejected(dynamoSpiDto, fileName, errorType, errorDesc, rqId);

        }

        return jsonMap;
    }

    public Map<String, Object> updateSuccessRetryMapper(MessageDtoBatch messageDtoBatch) {


        int newRetry = (int) messageDtoBatch.getOsIndexBatch().getRetryBatch() + 1;

        log.info("Reintento actual: {}", newRetry);

        Map<String, Object> jsonMap = new HashMap<>();


        jsonMap.put("blockedBatch", BATCH_BLOCK.getValue());
        jsonMap.put("statusBatch", BATCH_SUCCESS.getValue());
        jsonMap.put("retryBatch", newRetry);

        updateElement(jsonMap, messageDtoBatch.getIdOpensearch());

        return jsonMap;
    }

    @Override
    public void saveIndexRejected(
            DynamoSpiDto dynamoSpiDto,
            String fileName,
            String errorType,
            String errorDesc,
            String rqId

    ) {
        OSIndexRejected osIndexRejected = indexRejectedMapper.indexRejected(
                dynamoSpiDto,
                fileName,
                errorType,
                errorDesc,
                rqId
        );
        log.info("OSIndexRejected: {}", Util.object2StringWithNulls(osIndexRejected));
        addElement(osIndexRejected, IndexConstants.REJECTED_INDEX);
    }


    @Override
    public void processSuccessBatchAction(MessageDtoBatch messageDto) {

        if (messageDto != null) {

            updateElement(
                    updateSuccessRetryMapper(
                            messageDto
                    ),
                    messageDto.getIdOpensearch()
            );

        }
    }

    @Override
    public void processOpensearchAction(MessageDtoBatch messageDto, String fileName, String errorType, String errorDesc, String rqId) {

        DynamoSpiDto dynamoSpiDto = getDynamoSpiDto(messageDto);

        OSIndexBatch osIndexBatch = indexBatchMapper.mapCancelRqToIndexBatch(dynamoSpiDto, rqId,
                 ActionConstants.ONLINE_CANCELLATION.getValue(), fileName, messageDto);


        if (messageDto.getOsIndexBatch() != null) {

            updateElement(
                    updateRetryMapper(messageDto.getOsIndexBatch(),
                            fileName, errorType, errorDesc, rqId),
                    messageDto.getIdOpensearch());

        } else {

            addElement(osIndexBatch, IndexConstants.SONDA_INDEX);

        }
    }

    private DynamoSpiDto getDynamoSpiDto(MessageDtoBatch messageDto) {

        return (DynamoSpiDto) Util.string2object(
                messageDto.getOsIndexBatch().getRqServiceObject(),
                DynamoSpiDto.class);

    }


    public void keyProcessor(List<Hit<HashMap>> hits) {

        for (Hit<HashMap> hit : hits) {

            log.info("Registro recuperados de Index Batch: {}", Util.object2String(hit));

            blockLog(hit);

        }
        log.info("Finaliza proceso de actualizacion en  IndexBatch");

    }


    @Override
    public SearchTemplateResponse<HashMap> searchTemplateKey(String keyId, String keyType) {


        SearchTemplateResponse<HashMap> searchResponse;
        List<Hit<HashMap>> hits;
        long hitsSize;

        Map<String, JsonData> params = new HashMap<>();

        params.put(ConstantsEnum.OP_PARAMETER_KEY_TYPE.getValue(),
                JsonData.of(keyType));

        params.put(ConstantsEnum.OP_PARAMETER_KEY_VALUE.getValue(),
                JsonData.of(keyId));
        try {
            searchResponse = openSearchClient.searchTemplate(s -> s
                            .index(IndexConstants.DYNAMO_INDEX.getValue())
                            .id(ConstantsEnum.INDEX_KEY_TEMPLATE.getValue())
                            .params(params)
                    ,
                    HashMap.class);

            hitsSize = searchResponse.hits().total().value();
            hits = searchResponse.hits().hits();
            log.info("hits size" + hitsSize);
        } catch (Exception e) {
            e.printStackTrace(new PrintWriter(errors));
            log.error("{}{}", ConstantsEnum.ERROR_CONNECTION.getValue(), errors);
            throw new ATHException(MessagesEnum.DEFAULT_ERROR_RESPONSE.getCode(),
                    MessagesEnum.DEFAULT_ERROR_RESPONSE.getMessage(),
                    MessagesEnum.DEFAULT_ERROR_RESPONSE.getHttpCode());
        }
        return searchResponse;

    }


    public void blockLog(Hit<HashMap> hit) {

        if (hit.source().get(BATCH_STATUS.getValue()) != null &&
                hit.source().get(BATCH_STATUS.getValue()).toString().
                        equalsIgnoreCase(BATCH_PENDING.getValue())) {

            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put(BATCH_STATUS.getValue(), BATCH_CANCEL.getValue());
            try {
                UpdateResponse<Map> actualiza = openSearchClient.update(j -> j
                                .index(BatchEnum.BATCH_INDEX.getValue())
                                .id(hit.id())
                                .doc(jsonMap)
                                .ifSeqNo(hit.seqNo())
                                .ifPrimaryTerm(hit.primaryTerm())
                        , Map.class);


            } catch (Exception e) {

                e.printStackTrace(new PrintWriter(errors));
                log.error(ConstantsEnum.ERROR_CONNECTION.getValue() + errors);
                throw new ATHException(MessagesEnum.DEFAULT_ERROR_RESPONSE.getCode(),
                        MessagesEnum.DEFAULT_ERROR_RESPONSE.getMessage(),
                        MessagesEnum.DEFAULT_ERROR_RESPONSE.getHttpCode());

            }
        }
    }

    @Override
    public SearchTemplateResponse<HashMap> searchBatch(String keyId) {

        SearchTemplateResponse<HashMap> searchResponse;
        long hitsSize;

        Map<String, JsonData> params = new HashMap<>();

        params.put(ConstantsEnum.OP_PARAMETER_KEY_VALUE.getValue(),
                JsonData.of(keyId));

        try {
            searchResponse = openSearchClient.searchTemplate(s -> s
                            .index(IndexConstants.SONDA_INDEX.getValue())
                            .id(BatchEnum.BATCH_INDEX_BY_KEY_TEMPLATE.getValue())
                            .params(params)
                    ,
                    HashMap.class);

            hitsSize = searchResponse.hits().total().value();


        } catch (Exception e) {
            e.printStackTrace(new PrintWriter(errors));
            log.error("{}{}", ConstantsEnum.ERROR_CONNECTION.getValue(), errors);
            throw new ATHException(MessagesEnum.DEFAULT_ERROR_RESPONSE.getCode(),
                    MessagesEnum.DEFAULT_ERROR_RESPONSE.getMessage(),
                    MessagesEnum.DEFAULT_ERROR_RESPONSE.getHttpCode());
        }
        return searchResponse;

    }



}
