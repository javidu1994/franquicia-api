package co.com.accenture.r2dbc;

import co.com.accenture.model.franquicia.Franquicia;
import co.com.accenture.model.franquicia.gateways.FranquiciaRepository;
import co.com.accenture.r2dbc.entity.FranquiciaEntity;
import co.com.accenture.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class FranquiciaRepositoryAdapter
        extends ReactiveAdapterOperations<Franquicia, FranquiciaEntity, Long, FranquiciaReactiveRepository>
        implements FranquiciaRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(FranquiciaRepositoryAdapter.class);

    public FranquiciaRepositoryAdapter(FranquiciaReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, f -> mapper.map(f, Franquicia.class));
    }

    @Override
    public Mono<Franquicia> save(Franquicia franquicia) {
        return super.save(franquicia)
                .doOnSuccess(f -> LOGGER.debug("save franquicia: {}", f));
    }

    @Override
    public Mono<Franquicia> findById(Long id) {
        return super.findById(id)
                .doOnNext(f -> LOGGER.debug("findById con id: {}", id));
    }

    @Override
    public Flux<Franquicia> findAll() {
        return super.findAll()
                .doOnNext(f -> LOGGER.debug("franquicias retornadas: {}", f))
                .map(f -> f);
    }

}
