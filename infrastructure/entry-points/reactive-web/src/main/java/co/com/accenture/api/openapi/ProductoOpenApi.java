package co.com.accenture.api.openapi;

import co.com.accenture.api.dto.*;
import co.com.accenture.model.producto.dto.ProductoStockMayorDTO;
import lombok.experimental.UtilityClass;
import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.ErrorResponse;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;

@UtilityClass
public class ProductoOpenApi {

    private final String SUCCESS = "Success";
    private final String SUCCESS_CODE = String.valueOf(HttpStatus.OK.value());
    private final String CREATED_CODE = String.valueOf(HttpStatus.CREATED.value());
    private final String BAD_REQUEST = HttpStatus.BAD_REQUEST.getReasonPhrase();
    private final String BAD_REQUEST_CODE = String.valueOf(HttpStatus.BAD_REQUEST.value());
    private final String NOT_FOUND = HttpStatus.NOT_FOUND.getReasonPhrase();
    private final String NOT_FOUND_CODE = String.valueOf(HttpStatus.NOT_FOUND.value());
    private final String INTERNAL_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
    private final String INTERNAL_ERROR_CODE = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());

    public Builder saveProducto(Builder builder) {
        return builder
                .operationId("saveProducto")
                .description("Crea un nuevo Producto")
                .tag("Producto")
                .requestBody(requestBodyBuilder()
                        .required(true)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ProductoRequestDTO.class))))
                .response(responseBuilder().responseCode(CREATED_CODE).description("Producto creado")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ProductoDTO.class))))
                .response(responseBuilder().responseCode(BAD_REQUEST_CODE).description(BAD_REQUEST)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))))
                .response(responseBuilder().responseCode(INTERNAL_ERROR_CODE).description(INTERNAL_ERROR)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))));
    }

    public Builder getAllProductos(Builder builder) {
        return builder
                .operationId("getAllProductos")
                .description("Obtener productos")
                .tag("Productos")
                .response(responseBuilder().responseCode(SUCCESS_CODE).description(SUCCESS)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ProductoDTO.class))))
                .response(responseBuilder().responseCode(INTERNAL_ERROR_CODE).description(INTERNAL_ERROR)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))));
    }

    public Builder getProductoById(Builder builder) {
        return builder
                .operationId("getProductoById")
                .description("Obtener Producto by id")
                .tag("Producto")
                .response(responseBuilder().responseCode(SUCCESS_CODE).description(SUCCESS)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ProductoDTO.class))))
                .response(responseBuilder().responseCode(INTERNAL_ERROR_CODE).description(INTERNAL_ERROR)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))))
                .response(responseBuilder().responseCode(NOT_FOUND_CODE).description(NOT_FOUND)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))));
    }

    public Builder deleteProductoById(Builder builder) {
        return builder
                .operationId("deleteProductoById")
                .description("Delete Producto by id")
                .tag("Producto")
                .response(responseBuilder().responseCode(SUCCESS_CODE).description(SUCCESS)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(String.class))))
                .response(responseBuilder().responseCode(INTERNAL_ERROR_CODE).description(INTERNAL_ERROR)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))))
                .response(responseBuilder().responseCode(NOT_FOUND_CODE).description(NOT_FOUND)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))));
    }

    public Builder updateStockProducto(Builder builder) {
        return builder
                .operationId("updateStockProducto")
                .description("Update Stock Producto")
                .tag("Producto")
                .requestBody(requestBodyBuilder()
                        .required(true)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ProductoUpdateStockDTO.class))))
                .response(responseBuilder().responseCode(SUCCESS_CODE).description(SUCCESS)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(String.class))))
                .response(responseBuilder().responseCode(INTERNAL_ERROR_CODE).description(INTERNAL_ERROR)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))))
                .response(responseBuilder().responseCode(NOT_FOUND_CODE).description(NOT_FOUND)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))));
    }

    public Builder getProductosStockMayor(Builder builder) {
        return builder
                .operationId("getProductosStockMayor")
                .description("Obtener productos con mayor stock")
                .tag("Productos")
                .response(responseBuilder().responseCode(SUCCESS_CODE).description(SUCCESS)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ProductoStockMayorDTO.class))))
                .response(responseBuilder().responseCode(INTERNAL_ERROR_CODE).description(INTERNAL_ERROR)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))));
    }

    public Builder updateProducto(Builder builder) {
        return builder
                .operationId("updateProducto")
                .description("Update Producto")
                .tag("Producto")
                .requestBody(requestBodyBuilder()
                        .required(true)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ProductoUpdateDTO.class))))
                .response(responseBuilder().responseCode(SUCCESS_CODE).description(SUCCESS)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(String.class))))
                .response(responseBuilder().responseCode(INTERNAL_ERROR_CODE).description(INTERNAL_ERROR)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))))
                .response(responseBuilder().responseCode(NOT_FOUND_CODE).description(NOT_FOUND)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))));
    }
}
