package co.com.ath;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import io.micronaut.function.aws.runtime.AbstractMicronautLambdaRuntime;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.io.StringWriter;
import java.net.MalformedURLException;

/**
 * LambdaHandlerRuntime
 * <p>
 * Desarrollo ATH - Modelo Agregador ISO 20022
 * <p>
 * Creado él:
 * <p>
 * Autor		: David L. Reinoso Rey
 * <p>
 * Requerimiento: Desarrollo de Modelo Agregador ISO 20022
 * <p>
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 * <p>
 * Clase main de la lambda
 *
 * @author David L. Reinoso Rey
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class LambdaHandlerRuntime extends AbstractMicronautLambdaRuntime<SQSEvent, Void, SQSEvent, Void> {

    /**
     * Variable encargada de manejar mensajes de error para los catch
     */
    @Inject
    static StringWriter errors;

    /**
     * Variable constante de ERROR
     */
    @Inject
    static final String ERROR = "Error: ";

    public static void main(String[] args) throws MalformedURLException {
        log.info("Entro a LambdaHandlerRuntime main()");
        new LambdaHandlerRuntime().run(args);
    }

    /**
     * Método encargado de invocar el LambdaHandler
     *
     * @param args
     * @return
     */
    @Override
    protected RequestHandler<SQSEvent, Void> createRequestHandler(String... args) {
        return new LambdaHandler();
    }
}