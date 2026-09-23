package co.com.accenture.model.sucursal.gateways;

import co.com.accenture.model.sucursal.Sucursal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SucursalRepository {

    Mono<Sucursal> save(Sucursal sucursal);

    Mono<Sucursal> findById(Long idSucursal);

    Flux<Sucursal> findAll();
}
