package co.com.accenture.model.producto.gateways;

import co.com.accenture.model.producto.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoRepository {

    Mono<Producto> save(Producto franquicia);

    Mono<Producto> findById(Long idProducto);

    Flux<Producto> findAll();
}
