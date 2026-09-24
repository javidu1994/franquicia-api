package co.com.accenture.api;

import co.com.accenture.api.config.ProductoPath;
import co.com.accenture.api.openapi.ProductoOpenApi;
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
public class ProductoRouterRest {

    private final ProductoPath productoPath;
    private final ProductoHandler productoHandler;

    @Bean
    public RouterFunction<ServerResponse> productoRoutes(ProductoHandler handler) {
        return SpringdocRouteBuilder.route()
                .POST(productoPath.getProductos(), productoHandler::listenPostSaveProducto, ProductoOpenApi::saveProducto)
                .GET(productoPath.getProductos(), productoHandler::listenGETAllProductos, ProductoOpenApi::getAllProductos)
                .GET(productoPath.getProductos() + "/{id}", productoHandler::listenGETFindById,
                        ProductoOpenApi::getProductoById)
                .DELETE(productoPath.getProductos() + "/{id}", productoHandler::listenDeleteById,
                        ProductoOpenApi::deleteProductoById)
                .PUT(productoPath.getProductos() + productoPath.getProductosStock(), productoHandler::listenUpdateStock,
                        ProductoOpenApi::updateStockProducto)
                .GET(productoPath.getProductos() + productoPath.getProductosStockMayor(),
                        productoHandler::listenGETProductosStockMayor, ProductoOpenApi::getProductosStockMayor)
                .build();
    }
}
