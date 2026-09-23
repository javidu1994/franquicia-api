package co.com.accenture.r2dbc;

import co.com.accenture.r2dbc.entity.ProductoEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoReactiveRepository
        extends ReactiveCrudRepository<ProductoEntity, Long>, ReactiveQueryByExampleExecutor<ProductoEntity> {

    Mono<ProductoEntity> findById(Long idProducto);

    Flux<ProductoEntity> findAll();
}
