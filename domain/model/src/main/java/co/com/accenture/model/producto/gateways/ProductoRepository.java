package co.com.accenture.model.producto.gateways;

import co.com.accenture.model.producto.Producto;
import co.com.accenture.model.producto.dto.ProductoStockMayorDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoRepository {

    Mono<Producto> save(Producto franquicia);

    Mono<Producto> findById(Long idProducto);

    Flux<Producto> findAll();

    Mono<Void> deleteById(Long idProducto);

    Flux<ProductoStockMayorDTO> getProductosStockMayor(Long idFranquicia);
}
