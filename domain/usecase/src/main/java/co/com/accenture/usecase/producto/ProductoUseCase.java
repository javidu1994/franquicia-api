package co.com.accenture.usecase.producto;

import co.com.accenture.model.producto.Producto;
import co.com.accenture.model.producto.dto.ProductoStockMayorDTO;
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

    public Mono<Void> deleteById(Long id) { return productoRepository.deleteById(id); }

    public Mono<Void> updateStockProducto(Long idProducto, Integer cantidad) {
        return productoRepository.findById(idProducto)
                .flatMap(p -> {
                    p.setCantidad(cantidad);
                    return Mono.just(p);
                })
                .flatMap(this::save)
                .doOnSuccess(l -> LOGGER.info("updateStockProducto con cantidad: " + cantidad))
                .then(Mono.empty());
    }

    public Flux<ProductoStockMayorDTO> getProductosStockMayor(Long idFranquicia) {
        return productoRepository.getProductosStockMayor(idFranquicia)
                .doOnNext(l -> LOGGER.info("getProductosStockMayor en la franquicia con id: " + idFranquicia));

    }
}
