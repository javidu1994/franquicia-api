package co.com.accenture.r2dbc;

import co.com.accenture.model.sucursal.Sucursal;
import co.com.accenture.model.sucursal.gateways.SucursalRepository;
import co.com.accenture.r2dbc.entity.SucursalEntity;
import co.com.accenture.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class SucursalRepositoryAdapter
        extends ReactiveAdapterOperations<Sucursal, SucursalEntity, Long, SucursalReactiveRepository>
        implements SucursalRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(SucursalRepositoryAdapter.class);

    public SucursalRepositoryAdapter(SucursalReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Sucursal.class));
    }

    @Override
    public Mono<Sucursal> save(Sucursal sucursal) {
        return super.save(sucursal)
                .doOnSuccess(s -> LOGGER.debug("save sucursal: {}", s));
    }

    @Override
    public Mono<Sucursal> findById(Long id) {
        return super.findById(id)
                .doOnNext(s -> LOGGER.debug("findById con id: {}", id));
    }

    @Override
    public Flux<Sucursal> findAll() {
        return super.findAll()
                .doOnNext(s -> LOGGER.debug("sucursales retornadas: {}", s))
                .map(s -> s);
    }

}
