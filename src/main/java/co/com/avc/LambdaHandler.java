package co.com.avc;

import co.com.ath.commons.util.ATHException;
import co.com.ath.commons.util.Util;
import co.com.avc.constants.ConstantsEnum;

import co.com.avc.mapper.*;
import co.com.avc.models.MessageDto;
import co.com.avc.models.dynamo.DynamoSpiDto;
import co.com.avc.models.parameter.ParameterStoreDto;
import co.com.avc.models.SecretManagerDto;
import co.com.avc.models.SqsDto;
import co.com.ath.opensearch.logs.service.OpensearchLogService;
import co.com.ath.opensearch.sync.service.BlackListServiceImpl;
import co.com.ath.opensearch.sync.service.IBlackListService;
import co.com.ath.opensearch.sync.service.IOpensearchService;
import co.com.ath.opensearch.sync.service.OpensearchService;


import co.com.avc.service.interfaces.IOpenSearchSynchService;
import co.com.avc.service.interfaces.IUpdateOpenSearchService;
import co.com.avc.service.interfaces.IValidateService;
import co.com.avc.repository.*;
import co.com.avc.service.*;
import co.com.avc.util.IpSelectorUtil;
import co.com.avc.util.SnsSelectorUtil;
import co.com.avc.util.TimeLineUtil;
import co.com.avc.util.VaultSelectorUtil;
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
     * UUID de la petición
     */
    private String rqUUID;

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
     * Mapper del request principal de la cámara
     */
    private final RequestMapper requestMapper = new RequestMapper();

    /**
     * Servicio para obtener los valores del los parámetros
     */
    private final ParameterStoreRepository parameterStoreRepository = new ParameterStoreRepository();

    /**
     * Instancia del modelo de datos de los parámetros
     */
    private final ParameterStoreDto parameterStoreDto = parameterStoreRepository.getParameters();

    /**
     * Método para seleccionar la Ip según al entidad
     */
    private final IpSelectorUtil ipSelectorUtil = new IpSelectorUtil(parameterStoreDto.getRedebanConfigDto().getParamIp());

    /**
     * Mapper para crear el objeto del header de la petición
     */
    private final HeadersMapper headersMapper = new HeadersMapper(
            parameterStoreDto.getRedebanConfigDto().getParamBankUUID(),
            ipSelectorUtil,
            parameterStoreDto.getRedebanConfigDto().getOriginDtoList());

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
            blackListService
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

    private final VaultSelectorUtil vaultSelectorUtil = new VaultSelectorUtil(parameterStoreDto.getParamActiveVault());

    private final IValidateService validateUtil = new ValidateServiceImpl(
            openSearchSynchService,
            updateOpenSearchService
    );

    private final IndexTimeLineMapper indexTimeLineMapper = new IndexTimeLineMapper(ipSelectorUtil);

    private final TimeLineUtil timeLineUtil = new TimeLineUtil(opensearchLogService,
            indexTimeLineMapper,
            snsSelectorUtil
    );

    private final IRedEnrollmentTransvService redEnrollmentTransvService = new RedEnrollmentTransvServiceImpl(
            headersMapper,
            requestMapper,
            updateOpenSearchService,
            openSearchSynchService,
            timeLineUtil,
            indexBatchMapper,
            parameterStoreDto.getParamFlowConfig(),
            vaultSelectorUtil,
            parameterStoreDto.getVaultServicesTimeOut(),
            dynamoRepository
    );


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
            redirect(input);
        } catch (ATHException athExp) {
            athExp.printStackTrace(new PrintWriter(errors));

            updateOpenSearchService
                    .processOpensearchAction(
                            eventRq, fileName,
                            ConstantsEnum.ERROR_ATH_SERVICE.getValue(),
                            athExp.getMessage(),
                            rqID, rqUUID);

            log.error("{}{}", ConstantsEnum.ERROR_ATH_SERVICE.getValue(), errors);

        } catch (Exception exp) {
            exp.printStackTrace(new PrintWriter(errors));
            updateOpenSearchService
                    .processOpensearchAction(
                            eventRq, fileName,
                            ConstantsEnum.ERROR_ATH_SERVICE.getValue(),
                            exp.getMessage(),
                            rqID, rqUUID);

            log.error("{}{}", ConstantsEnum.ERROR_SERVICE.getValue(), errors);

        }
//        finally {
//            timeLineUtil.sendLogRs(dynamoSpiDto, rqUUID);
//        }

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
    private void redirect(SQSEvent input) {
        log.info("Entra a redirect");
        log.info("Table Name" + parameterStoreDto.getParamDynamo().getNameTable());

        //Empiza a iterar sobre la lista creada de los mensajes que llegan por sqs
        List<SQSEvent.SQSMessage> mensajes = input.getRecords();
        for (SQSEvent.SQSMessage msg : mensajes) {
            // Verifíca si el cuerpo del mensaje no está vacío.
            if (msg.getBody() != null && !msg.getBody().isEmpty()) {

                SecureRandom random = new SecureRandom();
                //Numerico
                rqID = String.valueOf(random.nextInt(99999999));
                //Alfanumerico
                rqUUID = UUID.randomUUID().toString();
                //Se obtiene la fecha de operación
                dateOperation = Util.createDate();

                //Mapeo del cuerpo del mensaje en un InputRequest
                SqsDto sqsDto =
                        (SqsDto) Util.string2object(msg.getBody(), SqsDto.class);

                log.info("se recibe el mensaje: {}", Util.object2String(sqsDto));
                //Se mapea el sqsDto a un MessageDto, usando REQUEST MAPPER, el cual es importante
                MessageDto messageDto = requestMapper.messageDtoMapper(sqsDto);

                eventRq = messageDto;
                //Se contruye el objeto de la clase DynamoSpiDto, para interactuar con DynamoDB.
                dynamoSpiDto = requestMapper.redirectDynamoData(messageDto, sqsDto.getSubject(), dateOperation);
                // el subject es un sujeto de un correo electronio, es decir es otro modelo para enviar la infor
                //Identificar si el mensaje es de tipo batch o sqs, batch es de cargues masivos
                fileName = (messageDto.getMessageDtoBatch() != null ?
                        messageDto.getMessageDtoBatch().getOsIndexBatch().getFileName()
                        : sqsDto.getSubject()
                );

                //Validación de llaves permitidas
                if (validateUtil.validateBannedKeys(dynamoSpiDto, fileName, rqID, rqUUID)) {

                    //Servicio de creación en camara y dirAval
                    redEnrollmentTransvService.redEnrollService(messageDto, sqsDto.getSubject(),
                            dynamoSpiDto, rqID, dateOperation, rqUUID);
                }
                log.info("Finalizo el proceso de la lambda");
            }
        }
    }
}
