package co.com.accenture.api;

import co.com.accenture.api.dto.*;
import co.com.accenture.api.exception.ValidationException;
import co.com.accenture.model.producto.Producto;
import co.com.accenture.model.producto.dto.ProductoStockMayorDTO;
import co.com.accenture.usecase.producto.ProductoUseCase;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductoHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoHandler.class);
    private final ProductoUseCase productoUseCase;
    private final Validator validator;
    private final ObjectMapper objectMapper;

    public Mono<ServerResponse> listenPostSaveProducto(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ProductoRequestDTO.class)
                .doOnNext(p -> LOGGER.debug("listenPostSaveProducto con datos: {} ", p.toString()))
                .flatMap(this::validateRequest)
                .map(producto -> objectMapper.map(producto, Producto.class))
                .flatMap(productoUseCase::save)
                .flatMap(saved -> ServerResponse.created(serverRequest.uriBuilder().path("/{idProducto}")
                                .build(saved.getIdProducto()))
                        .contentType(MediaType.APPLICATION_NDJSON)
                        .bodyValue(saved)
                );
    }

    private Mono<ProductoRequestDTO> validateRequest(ProductoRequestDTO requestDTO) {
        Errors errors = new BeanPropertyBindingResult(requestDTO, ProductoRequestDTO.class.getName());
        validator.validate(requestDTO, errors);

        if (errors.hasErrors()) {
            List<ValidationError> fieldErrors = errors.getFieldErrors()
                    .stream()
                    .map(err -> new ValidationError(err.getField(), err.getDefaultMessage()))
                    .collect(Collectors.toList());
            LOGGER.info("Error con listenPostSaveProducto: {}", fieldErrors);
            throw new ValidationException(fieldErrors);
        }
        return Mono.just(requestDTO);
    }

    public Mono<ServerResponse> listenGETFindById(ServerRequest serverRequest) {
        String id = serverRequest.queryParam("id").orElse("");
        if (id.isEmpty()) {
            return ServerResponse.badRequest()
                    .bodyValue("El id es requerido");
        }
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(productoUseCase.findById(Long.parseLong(id)), ProductoDTO.class);
    }

    public Mono<ServerResponse> listenGETAllProductos(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(productoUseCase.findAll(), ProductoDTO.class);
    }

    public Mono<ServerResponse> listenDeleteById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        if (id.isEmpty()) {
            return ServerResponse.badRequest()
                    .bodyValue("El id del producto es requerido");
        }
        return productoUseCase.deleteById(Long.parseLong(id))
                .then(ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_NDJSON)
                        .bodyValue("Producto eliminado correctamente!"));
    }

    public Mono<ServerResponse> listenUpdateStock(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ProductoUpdateStockDTO.class)
                .doOnNext(dto -> LOGGER.debug("listenUpdateStock con datos: {} ", dto))
                .flatMap(dto -> productoUseCase
                        .updateStockProducto(dto.getIdProducto(), dto.getCantidad()))
                .then(ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_NDJSON)
                        .bodyValue("Stock del producto actualizado correctamente!"));
    }

    public Mono<ServerResponse> listenGETProductosStockMayor(ServerRequest serverRequest) {
        String idFranquicia = serverRequest.pathVariable("idFranquicia");
        if (idFranquicia.isEmpty()) {
            return ServerResponse.badRequest()
                    .bodyValue("El id de la franquicia es requerido");
        }
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(productoUseCase.getProductosStockMayor(Long.parseLong(idFranquicia)), ProductoStockMayorDTO.class);
    }
}
