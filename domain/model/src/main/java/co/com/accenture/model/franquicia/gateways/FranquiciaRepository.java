package co.com.accenture.model.franquicia.gateways;

import co.com.accenture.model.franquicia.Franquicia;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranquiciaRepository {

    Mono<Franquicia> save(Franquicia franquicia);

    Mono<Franquicia> findById(Long idFranquicia);

    Flux<Franquicia> findAll();
}
