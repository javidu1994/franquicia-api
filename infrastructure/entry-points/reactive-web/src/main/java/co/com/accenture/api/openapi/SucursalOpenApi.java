package co.com.accenture.api.openapi;

import co.com.accenture.api.dto.SucursalDTO;
import co.com.accenture.api.dto.SucursalRequestDTO;
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
public class SucursalOpenApi {

    private final String SUCCESS = "Success";
    private final String SUCCESS_CODE = String.valueOf(HttpStatus.OK.value());
    private final String CREATED_CODE = String.valueOf(HttpStatus.CREATED.value());
    private final String BAD_REQUEST = HttpStatus.BAD_REQUEST.getReasonPhrase();
    private final String BAD_REQUEST_CODE = String.valueOf(HttpStatus.BAD_REQUEST.value());
    private final String NOT_FOUND = HttpStatus.NOT_FOUND.getReasonPhrase();
    private final String NOT_FOUND_CODE = String.valueOf(HttpStatus.NOT_FOUND.value());
    private final String INTERNAL_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
    private final String INTERNAL_ERROR_CODE = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());

    public Builder saveSucursal(Builder builder) {
        return builder
                .operationId("saveSucursal")
                .description("Crea una nueva Sucursal")
                .tag("Sucursal")
                .requestBody(requestBodyBuilder()
                        .required(true)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(SucursalRequestDTO.class))))
                .response(responseBuilder().responseCode(CREATED_CODE).description("Sucursal creada")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(SucursalDTO.class))))
                .response(responseBuilder().responseCode(BAD_REQUEST_CODE).description(BAD_REQUEST)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))))
                .response(responseBuilder().responseCode(INTERNAL_ERROR_CODE).description(INTERNAL_ERROR)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))));
    }

    public Builder getAllSucursales(Builder builder) {
        return builder
                .operationId("getAllSucursales")
                .description("Obtener sucursales")
                .tag("Sucursales")
                .response(responseBuilder().responseCode(SUCCESS_CODE).description(SUCCESS)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(SucursalDTO.class))))
                .response(responseBuilder().responseCode(INTERNAL_ERROR_CODE).description(INTERNAL_ERROR)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))));
    }

    public Builder getSucursalById(Builder builder) {
        return builder
                .operationId("getSucursalById")
                .description("Obtener Sucursal by id")
                .tag("Sucursal")
                .response(responseBuilder().responseCode(SUCCESS_CODE).description(SUCCESS)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(SucursalDTO.class))))
                .response(responseBuilder().responseCode(INTERNAL_ERROR_CODE).description(INTERNAL_ERROR)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))))
                .response(responseBuilder().responseCode(NOT_FOUND_CODE).description(NOT_FOUND)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_NDJSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponse.class))));
    }

}
