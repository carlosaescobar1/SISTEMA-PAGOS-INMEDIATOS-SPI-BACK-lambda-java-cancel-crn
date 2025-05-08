package co.com.avc.constants;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ParameterStoreEnum {

    /**
     * Ruta del parámetro general
     */
    PARAMETER_GENERAL_PATH_URL("/SPI/AWUE1ATHSPI-LAMBDA-TRANSV/"),

    /**
     * Ruta del parámetro que almacena la url del json de las cámaras disponibles
     */
    PARAM_JSON_VAULT("/SPI/AWUE1ATHSPI-LAMBDA-TRANSV/jsonActiveVault"),

    /**
     * Ruta del parámetro que almacena la url del SNS que activara la sonda.
     */
    PARAM_ARN_SNS_LOGS_OPEN_SEARCH("/SPI/AWUE1ATHSPI-LAMBDA-TRANSV/jsonArnSnsLogsOpenSearch"),

    /**
     * Ruta del parámetro que almacena los tiempos de espera máximos para el consumo del servicio
     * de creación redeban.
     */
    PARAM_VAULT_SERVICE_TIMEOUT("/SPI/AWUE1ATHSPI-LAMBDA-TRANSV/vaultServicesTimeOut"),

    /**
     * Ruta del parámetro que almacena la url del json de la region
     */
    PARAM_REGION("/SPI/AWUE1ATHSPI-LAMBDA-TRANSV/awsRegion"),

    /**
     * Ruta del parámetro que almacena la informacion del Dynamo
     */
    PARAM_DYNAMO("/SPI/AWUE1ATHSPI-LAMBDA-TRANSV/jsonDynamoDirAval"),

    /**
     * Ruta del parámetro que almacena la url del json de la configuracion de REDEBAN
     */
    PARAM_REDEBAN_CONFIG("/SPI/AWUE1ATHSPI-LAMBDA-TRANSV/redebanConfig"), //REQUIERE CAMBIO A CORNER

    /**
     * Ruta del parámetro que almacena la cantidad maxima de intentos para el batch
     */
    PARAM_MAX_RETRY_BATCH("/SPI/AWUE1ATHSPI-LAMBDA-TRANSV/maxRetryBatch"),

    /**
     * Ruta del parámetro que almacena el arn de OpenSearch
     */
    PARAM_ARN_SECRET_OPEN_SEARCH("/SPI/AWUE1ATHSPI-LAMBDA-TRANSV/arnSecretOpensearch"),

    /**
     * Ruta del parámetro que almacena informacion del JSON
     */
    PARAM_FLOW_CONFIG("/SPI/AWUE1ATHSPI-LAMBDA-TRANSV/jsonFlowConfig"),

    /**
     * Ruta del parámetro que almacena informacion de listas negras
     */
    PARAM_BLACK_LIST_MAX_SCORE("/SPI/AWUE1ATHSPI-LAMBDA-TRANSV/blackListScore"),



    //Añadido despues de la revisión
    PARAMETER_CANCELLATION_PATH_URL("/SPI/AWUE1ATHSPI-LAMBDA-TRANSV-010/"),
    PARAM_OS_QUERY_SIZE("/SPI/AWUE1ATHSPI-LAMBDA-TRANSV-010/osQuerySize");
    private final String value;

}
