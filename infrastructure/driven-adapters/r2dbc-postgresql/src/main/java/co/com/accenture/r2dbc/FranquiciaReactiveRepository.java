package co.com.accenture.r2dbc;

import co.com.accenture.r2dbc.entity.FranquiciaEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranquiciaReactiveRepository
        extends ReactiveCrudRepository<FranquiciaEntity, Long>, ReactiveQueryByExampleExecutor<FranquiciaEntity> {

    Mono<FranquiciaEntity> findById(Long idFranquicia);

    Flux<FranquiciaEntity> findAll();
}
