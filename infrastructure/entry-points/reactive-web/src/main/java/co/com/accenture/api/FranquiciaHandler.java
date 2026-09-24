package co.com.accenture.api;

import co.com.accenture.api.dto.FranquiciaDTO;
import co.com.accenture.api.dto.FranquiciaRequestDTO;
import co.com.accenture.api.dto.ValidationError;
import co.com.accenture.api.exception.ValidationException;
import co.com.accenture.model.franquicia.Franquicia;
import co.com.accenture.usecase.franquicia.FranquiciaUseCase;
import lombok.RequiredArgsConstructor;
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
import org.reactivecommons.utils.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FranquiciaHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(FranquiciaHandler.class);
    private final FranquiciaUseCase franquiciaUseCase;
    private final Validator validator;
    private final ObjectMapper objectMapper;

    public Mono<ServerResponse> listenPostSaveFranquicia(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(FranquiciaRequestDTO.class)
                .doOnNext(f -> LOGGER.debug("listenPostSaveFranquicia con datos: {} ", f.toString()))
                .flatMap(this::validateRequest)
                .map(franquicia -> objectMapper.map(franquicia, Franquicia.class))
                .flatMap(franquiciaUseCase::save)
                .flatMap(saved -> ServerResponse.created(serverRequest.uriBuilder().path("/{idFranquicia}")
                                .build(saved.getIdFranquicia()))
                        .contentType(MediaType.APPLICATION_NDJSON)
                        .bodyValue(saved)
                );
    }

    private Mono<FranquiciaRequestDTO> validateRequest(FranquiciaRequestDTO requestDTO) {
        Errors errors = new BeanPropertyBindingResult(requestDTO, FranquiciaRequestDTO.class.getName());
        validator.validate(requestDTO, errors);

        if (errors.hasErrors()) {
            List<ValidationError> fieldErrors = errors.getFieldErrors()
                    .stream()
                    .map(err -> new ValidationError(err.getField(), err.getDefaultMessage()))
                    .collect(Collectors.toList());
            LOGGER.info("Error con listenPostSaveFranquicia: {}", fieldErrors);
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
                .body(franquiciaUseCase.findById(Long.parseLong(id)), FranquiciaDTO.class);
    }

    public Mono<ServerResponse> listenGETAllFranquicias(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(franquiciaUseCase.findAll(), FranquiciaDTO.class);
    }
}
