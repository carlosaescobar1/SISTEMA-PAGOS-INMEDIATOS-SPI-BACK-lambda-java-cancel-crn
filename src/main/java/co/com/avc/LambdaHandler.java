package co.com.avc;

import co.com.ath.commons.util.ATHException;
import co.com.ath.commons.util.Util;
import co.com.ath.opensearch.sync.service.BlackListServiceImpl;
import co.com.ath.opensearch.sync.service.IBlackListService;
import co.com.avc.constants.ConstantsEnum;
import co.com.avc.entity.Ath.DynamoSpiEntity;
import co.com.avc.mapper.*;
import co.com.avc.models.*;
import co.com.avc.models.dynamoAth.DynamoSpiDto;
import co.com.avc.models.parameter.ParamVaultUpload;
import co.com.avc.models.parameter.ParameterStoreDto;
import co.com.ath.opensearch.logs.service.OpensearchLogService;
import co.com.ath.opensearch.sync.service.IOpensearchService;
import co.com.ath.opensearch.sync.service.OpensearchService;
import co.com.avc.repository.*;
import co.com.avc.service.*;
import co.com.avc.service.interfaces.IOpenSearchSynchService;
import co.com.avc.service.interfaces.ICorCancelBatchTransvService;
import co.com.avc.service.interfaces.ICorCancelMigTransvService;
import co.com.avc.service.interfaces.IUpdateOpenSearchService;
import co.com.avc.util.IpSelectorUtil;
import co.com.avc.util.SnsSelectorUtil;
import co.com.avc.util.TimeLineUtil;
import co.com.avc.util.VaultSelectorUtil;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.function.aws.MicronautRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.client.opensearch.OpenSearchClient;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

import static co.com.avc.constants.BatchEnum.BATCH_CANCEL_SUBJECT;
import static co.com.avc.constants.BatchEnum.BATCH_SUBJECT;
import static co.com.avc.constants.ConstantsEnum.KEY_ID_START_WITH;

/**
 * Handler
 * <p>
 * Desarrollo ATH - SPBVI
 * <p>
 * Creado él: 14 de agosto de 2024
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
@Introspected
public class LambdaHandler extends MicronautRequestHandler<SQSEvent, Void> {

    /**
     * Fecha generada
     */
    private String dateOperation;

    /**
     * Id de la petición
     */
    private String rqID;


    /**
     * Mensaje recibido
     */
    private MessageDto eventRq;

    /**
     * Modelo de datos de dynamo
     */
    private DynamoSpiDto dynamoSpiDto;

    /**
     * Nombre del archivo de entrada
     */
    private String fileName;



    /**
     * Servicio para obtener los valores del los parámetros
     */
    private final ParameterStoreRepository parameterStoreRepository = new ParameterStoreRepository();

    /**
     * Instancia del modelo de datos de los parámetros
     */
    private final ParameterStoreDto parameterStoreDto = parameterStoreRepository.getParameters();

    /**
     * Método para seleccionar la Ip según al entidad, BANCOS -Revisar----------------------------------------------------------------------->
     */
    private final IpSelectorUtil ipSelectorUtil = new IpSelectorUtil(parameterStoreDto.getRedebanConfigDto().getParamIp());

    /**
     * Mapper para crear el objeto del header de la petición
     */


    /**
     * Servicio para obtener los valores de los secretos
     */
    private final SecretManagerRepository secretManagerRepository = new SecretManagerRepository(parameterStoreDto.getArnSecretOpenSearch());

    /**
     * Modelo para almacenar los datos de los secretos
     */
    private final SecretManagerDto secretManagerDto = secretManagerRepository.getSecrets();

    /**
     * Instancia del servicio que tiene el método de conexión a opensearch
     */
    private final OpenSearchConnectionRepository openSearchConnectionRepository = new OpenSearchConnectionRepository(secretManagerDto);

    /**
     * Instancia del cliente de opensearch
     */
    private final OpenSearchClient client = openSearchConnectionRepository.createHttpRequest();

    /**
     * Instancia del servicio de llaves no permitidas de la librería
     */
    private final IBlackListService blackListService = new BlackListServiceImpl(parameterStoreDto.getBlackListScore());

    /**
     * Instancia del servicio que contiene las operaciones con opensearch
     */
    private final IOpensearchService opensearchService = new OpensearchService(client);

    /**
     * Instancia de servicio que contiene los métodos de sincronización de llaves
     */
    private final IOpenSearchSynchService openSearchSynchService = new OpenSearchSynchServiceImpl(
            opensearchService,
            client,
            parameterStoreDto.getOpSearchQuerySize()
    );

    /**
     * Instancia del mappers del índice de rechazados
     */
    private final IndexRejectedMapper indexRejectedMapper = new IndexRejectedMapper();

    private final IndexBatchMapper indexBatchMapper = new IndexBatchMapper();

    private final IUpdateOpenSearchService updateOpenSearchService = new UpdateOpenSearchServiceImpl(
            client,
            indexBatchMapper,
            indexRejectedMapper,
            parameterStoreDto.getMaxRetryBatch()
    );


    private final SnsSelectorUtil snsSelectorUtil = new SnsSelectorUtil(parameterStoreDto.getArnSnsOpenSearch());

        private final OpensearchLogService opensearchLogService = new OpensearchLogService();

    private final DynamoBuilderRepository dynamoBuilderRepository = new DynamoBuilderRepository(parameterStoreDto.getRegion());

    private final DynamoRepository dynamoRepository = new DynamoRepository(
            dynamoBuilderRepository.getClient(),
            parameterStoreDto.getParamDynamo().getNameTable());

    private final IndexTimeLineMapper indexTimeLineMapper = new IndexTimeLineMapper(ipSelectorUtil);

    private final TimeLineUtil timeLineUtil = new TimeLineUtil(opensearchLogService,
            indexTimeLineMapper,
            snsSelectorUtil
    );

    /**
     * Instancia del servicio Corner------------------------------------------------------------------------------------------->
     */
    private final VaultSelectorUtil vaultSelectorUtil = new VaultSelectorUtil(parameterStoreDto.getParamActiveVault());
    private final ParamVaultUpload paramVaultUpload = vaultSelectorUtil.selectorVault();
    private final HeadersMapper headersMapper = new HeadersMapper();
    private final ICorCancelMigTransvService cancelTransvService =
            new CorCancelMigTransvServiceImpl(
                    timeLineUtil, indexBatchMapper, parameterStoreDto.getParamFlowConfig(), vaultSelectorUtil,
                    parameterStoreDto.getVaultServicesTimeOut(), dynamoRepository, updateOpenSearchService,
                    openSearchSynchService, paramVaultUpload);

    private final ICorCancelBatchTransvService cancelBatchTransvService = new CorCancelBatchTransvServiceImpl(
             timeLineUtil, indexBatchMapper,
            parameterStoreDto.getParamFlowConfig(),headersMapper, vaultSelectorUtil,
            parameterStoreDto.getVaultServicesTimeOut(), dynamoRepository, updateOpenSearchService,
            openSearchSynchService, paramVaultUpload);

    private final CorCancelCntServiceImpl cancelCntService = new CorCancelCntServiceImpl(
             timeLineUtil, indexBatchMapper, parameterStoreDto.getParamFlowConfig(), vaultSelectorUtil,
            parameterStoreDto.getVaultServicesTimeOut(), dynamoRepository, updateOpenSearchService,
            openSearchSynchService, paramVaultUpload);



    /**
     * Instancia del convertidor de SQS a sonda
     */
    private final RqBatchMapper rqBatchMapper = new RqBatchMapper();

    private final RqMigrationMapper rqMigrationMapper = new RqMigrationMapper();

    private final RqDynamoSpiMapper rqDynamoSpiMapper = new RqDynamoSpiMapper();





    /**
     * Variable en donde se almacenan los errores generados en caso de presentarse alguna excepción
     * durante el proceso de la lambda.
     */
    StringWriter errors = new StringWriter();

    /**
     * Método encargado de recibir la solicitud enviada por el ApiGateway,
     * mapea la respuesta hacia la API tanto en casos exitosos como
     * fallidos
     *
     * @param input APIGatewayProxyRequestEvent - Evento desencadenado por el consumo
     *              del APIGateway.
     * @return APIGatewayProxyResponseEvent evento de respuesta enviado a la API.
     */
    @Override
    public Void execute(SQSEvent input) {
        try {
            // Redirige los mensajes SQS al método correspondiente
            redirect(input);
        } catch (ATHException athExp) {
            // Manejo de excepciones específicas de ATH
            athExp.printStackTrace(new PrintWriter(errors));
            updateOpenSearchService.processOpensearchAction(
                    null, fileName, ConstantsEnum.ERROR_ATH_SERVICE.getValue(),
                    athExp.getMessage(), rqID);
            log.error("{}{}", ConstantsEnum.ERROR_ATH_SERVICE.getValue(), errors);
        } catch (Exception exp) {
            // Manejo de excepciones generales
            exp.printStackTrace(new PrintWriter(errors));
            updateOpenSearchService.processOpensearchAction(
                    null, fileName, ConstantsEnum.ERROR_ATH_SERVICE.getValue(),
                    exp.getMessage(), rqID);
            log.error("{}{}", ConstantsEnum.ERROR_SERVICE.getValue(), errors);
        }
        return null;
    }

    /**
     * Método encargado de redireccionar las solicitudes entrantes al APIGateway
     * dependiendo del método http con el cual se genera la solicitud
     * Método POST -> Procesará la solicitud de creación de llaves.
     * Método PUT -> Procesará la solicitud de actualización de llaves.
     *
     * @param input APIGatewayProxyRequestEvent - Evento desencadenado por el consumo
     *              del APIGateway.
     * @return Object objeto genérico que contiene la respuesta de la solicitud.
     */
    public void redirect(SQSEvent input) {

        List<SQSEvent.SQSMessage> sqsRecordList = input.getRecords();

        try {
            for (SQSEvent.SQSMessage sqsRecord : sqsRecordList) {

                if (sqsRecord.getBody() != null && !sqsRecord.getBody().isEmpty()) {
                    log.info("Body del sqs: {}", sqsRecord.getBody());
                    SecureRandom secureRandom = new SecureRandom();

                    rqID = String.format("%08d", secureRandom.nextInt(100000000));
                    //rqUUID = UUID.randomUUID().toString();
                    SqsDto sqsDto = (SqsDto) Util.string2object(sqsRecord.getBody(), SqsDto.class);

                    if (sqsDto.getSubject().equalsIgnoreCase(BATCH_SUBJECT.getValue())) {

                        MessageDtoBatch messageDtoBatch = rqBatchMapper.messageDtoMapper(sqsDto);

                        dynamoSpiDto = rqDynamoSpiMapper.redirectDynamoBatch(messageDtoBatch);

                        cancelBatchTransvService.corCancelBatchService(messageDtoBatch,
                                sqsDto.getSubject(), dynamoSpiDto, rqID,
                                 parameterStoreDto);
                    } else if (sqsDto.getSubject().contains(BATCH_CANCEL_SUBJECT.getValue())) {

                        MessageDtoKeysCancel messageDtoKeysCancel = rqMigrationMapper.messageDtoMapperCancel(sqsDto);
                        String keyIdSk;

                        keyIdSk =  messageDtoKeysCancel.getKeyId();
                        DynamoSpiEntity dynamoSpiEntity = dynamoRepository.load(keyIdSk, keyIdSk);
                        boolean dynEntity;

                        if (dynamoSpiEntity == null) {
                            //No se encontro registro en dynamo,
                            dynEntity = false;
                            dynamoSpiDto = rqDynamoSpiMapper.redirectDynamoCnt(messageDtoKeysCancel);
                            cancelCntService.corCancelMigService(
                                    sqsDto.getSubject(), dynamoSpiDto, rqID,
                                     messageDtoKeysCancel, dynEntity);
                        } else {
                            // Se encontro registro en dynamo
                            dynamoSpiDto = IDynamoMapper.INSTANCE.dynamoEntityToDynamoDto(dynamoSpiEntity);
                            dynEntity = true;
                            cancelCntService.corCancelMigService(
                                    sqsDto.getSubject(), dynamoSpiDto, rqID,
                                     messageDtoKeysCancel, dynEntity);
                        }
                    } else {
                        MessageDtoDynamo messageDtoDynamo = rqMigrationMapper.messageDtoMapper(sqsDto);
                        dynamoSpiDto = rqDynamoSpiMapper.redirectDynamoMig(messageDtoDynamo);

                        cancelTransvService.corCancelMigService(sqsDto.getSubject(), dynamoSpiDto, rqID,
                                 messageDtoDynamo.getFileName());
                    }
                }
            }
        } catch (Exception e) {
            /** Manejo de excepciones**/
            e.printStackTrace(new PrintWriter(errors));
            log.error("Error procesando la información del SQS: " + errors);


        }
    }

}
