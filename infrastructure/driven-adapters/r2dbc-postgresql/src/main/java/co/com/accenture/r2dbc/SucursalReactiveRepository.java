package co.com.accenture.r2dbc;

import co.com.accenture.r2dbc.entity.SucursalEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SucursalReactiveRepository
        extends ReactiveCrudRepository<SucursalEntity, Long>, ReactiveQueryByExampleExecutor<SucursalEntity> {

    Mono<SucursalEntity> findById(Long idSucursal);

    Flux<SucursalEntity> findAll();
}
