package co.com.accenture.api;

import co.com.accenture.api.config.FranquiciaPath;
import co.com.accenture.api.openapi.FranquiciaOpenApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.webflux.core.fn.SpringdocRouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FranquiciaRouterRest {

    private final FranquiciaPath franquiciaPath;
    private final FranquiciaHandler franquiciaHandler;

    @Bean
    public RouterFunction<ServerResponse> franquiciaRoutes(FranquiciaHandler handler) {
        return SpringdocRouteBuilder.route()
                .POST(franquiciaPath.getFranquicias(), franquiciaHandler::listenPostSaveFranquicia, FranquiciaOpenApi::saveFranquicia)
                .GET(franquiciaPath.getFranquicias(), franquiciaHandler::listenGETAllFranquicias, FranquiciaOpenApi::getAllFranquicias)
                .GET(franquiciaPath.getFranquicias() + "/{id}", franquiciaHandler::listenGETFindById,
                        FranquiciaOpenApi::getFranquiciaById)
                .build();
    }
}
