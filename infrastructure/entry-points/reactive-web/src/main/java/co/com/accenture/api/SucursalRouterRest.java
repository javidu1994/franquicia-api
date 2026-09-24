package co.com.accenture.api;

import co.com.accenture.api.config.SucursalPath;
import co.com.accenture.api.openapi.SucursalOpenApi;
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
public class SucursalRouterRest {

    private final SucursalPath sucursalPath;
    private final SucursalHandler sucursalHandler;

    @Bean
    public RouterFunction<ServerResponse> sucursalRoutes(SucursalHandler handler) {
        return SpringdocRouteBuilder.route()
                .POST(sucursalPath.getSucursales(), sucursalHandler::listenPostSaveSucursal, SucursalOpenApi::saveSucursal)
                .GET(sucursalPath.getSucursales(), sucursalHandler::listenGETAllSucursales, SucursalOpenApi::getAllSucursales)
                .GET(sucursalPath.getSucursales() + "/{id}", sucursalHandler::listenGETFindById,
                        SucursalOpenApi::getSucursalById)
                .build();
    }
}
