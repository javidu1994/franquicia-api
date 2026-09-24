package co.com.accenture.usecase.producto;

import co.com.accenture.model.producto.Producto;
import co.com.accenture.model.producto.gateways.ProductoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

public class ProductoUseCase {

    private final ProductoRepository productoRepository;
    private static final Logger LOGGER = Logger.getLogger(ProductoUseCase.class.getName());

    public ProductoUseCase(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Mono<Producto> save(Producto producto) {
        return Mono.just(producto)
                .doOnNext(l -> LOGGER.info("saveProducto con datos: {} " + producto.toString()))
                .flatMap(f -> productoRepository.save(producto));
    }

    public Mono<Producto> findById(Long id) { return productoRepository.findById(id); }

    public Flux<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Mono<Void> delete
}
