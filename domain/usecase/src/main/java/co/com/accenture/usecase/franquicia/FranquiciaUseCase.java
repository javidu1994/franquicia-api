package co.com.accenture.usecase.franquicia;

import co.com.accenture.model.franquicia.Franquicia;
import co.com.accenture.model.franquicia.gateways.FranquiciaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

public class FranquiciaUseCase {

    private final FranquiciaRepository franquiciaRepository;
    private static final Logger LOGGER = Logger.getLogger(FranquiciaUseCase.class.getName());

    public FranquiciaUseCase(FranquiciaRepository franquiciaRepository) {
        this.franquiciaRepository = franquiciaRepository;
    }

    public Mono<Franquicia> save(Franquicia franquicia) {
        return Mono.empty()
                .doOnNext(l -> LOGGER.info("saveFranquicia con datos: {} " + franquicia.toString()))
                .flatMap(f -> franquiciaRepository.save(franquicia));
    }

    public Mono<Franquicia> findById(Long id) { return franquiciaRepository.findById(id); }

    public Flux<Franquicia> findAll() {
        return franquiciaRepository.findAll();
    }
}
