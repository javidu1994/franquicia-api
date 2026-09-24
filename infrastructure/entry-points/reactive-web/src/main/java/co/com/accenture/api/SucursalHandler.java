package co.com.accenture.api;

import co.com.accenture.api.dto.SucursalDTO;
import co.com.accenture.api.dto.SucursalRequestDTO;
import co.com.accenture.api.dto.ValidationError;
import co.com.accenture.api.exception.ValidationException;
import co.com.accenture.model.sucursal.Sucursal;
import co.com.accenture.usecase.sucursal.SucursalUseCase;
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
public class SucursalHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(SucursalHandler.class);
    private final SucursalUseCase sucursalUseCase;
    private final Validator validator;
    private final ObjectMapper objectMapper;

    public Mono<ServerResponse> listenPostSaveSucursal(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(SucursalRequestDTO.class)
                .doOnNext(f -> LOGGER.debug("listenPostSaveSucursal con datos: {} ", f.toString()))
                .flatMap(this::validateRequest)
                .map(sucursal -> objectMapper.map(sucursal, Sucursal.class))
                .flatMap(sucursalUseCase::save)
                .flatMap(saved -> ServerResponse.created(serverRequest.uriBuilder().path("/{idSucursal}")
                                .build(saved.getIdSucursal()))
                        .contentType(MediaType.APPLICATION_NDJSON)
                        .bodyValue(saved)
                );
    }

    private Mono<SucursalRequestDTO> validateRequest(SucursalRequestDTO requestDTO) {
        Errors errors = new BeanPropertyBindingResult(requestDTO, SucursalRequestDTO.class.getName());
        validator.validate(requestDTO, errors);

        if (errors.hasErrors()) {
            List<ValidationError> fieldErrors = errors.getFieldErrors()
                    .stream()
                    .map(err -> new ValidationError(err.getField(), err.getDefaultMessage()))
                    .collect(Collectors.toList());
            LOGGER.info("Error con listenPostSaveSucursal: {}", fieldErrors);
            throw new ValidationException(fieldErrors);
        }
        return Mono.just(requestDTO);
    }

    public Mono<ServerResponse> listenGETFindById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        if (id.isEmpty()) {
            return ServerResponse.badRequest()
                    .bodyValue("El id es requerido");
        }
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(sucursalUseCase.findById(Long.parseLong(id)), SucursalDTO.class);
    }

    public Mono<ServerResponse> listenGETAllSucursales(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(sucursalUseCase.findAll(), SucursalDTO.class);
    }
}
