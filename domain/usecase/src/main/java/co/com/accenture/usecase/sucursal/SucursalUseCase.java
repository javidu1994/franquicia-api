package co.com.accenture.usecase.sucursal;

import co.com.accenture.model.sucursal.Sucursal;
import co.com.accenture.model.sucursal.gateways.SucursalRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

public class SucursalUseCase {

    private final SucursalRepository sucursalRepository;
    private static final Logger LOGGER = Logger.getLogger(SucursalUseCase.class.getName());

    public SucursalUseCase(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    public Mono<Sucursal> save(Sucursal sucursal) {
        return Mono.just(sucursal)
                .doOnNext(l -> LOGGER.info("saveSucursal con datos: {} " + sucursal.toString()))
                .flatMap(f -> sucursalRepository.save(sucursal));
    }

    public Mono<Sucursal> findById(Long id) { return sucursalRepository.findById(id); }

    public Flux<Sucursal> findAll() {
        return sucursalRepository.findAll();
    }
}
