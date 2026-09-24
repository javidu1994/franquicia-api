package co.com.accenture.r2dbc;

import co.com.accenture.model.producto.Producto;
import co.com.accenture.model.producto.dto.ProductoStockMayorDTO;
import co.com.accenture.model.producto.gateways.ProductoRepository;
import co.com.accenture.r2dbc.entity.ProductoEntity;
import co.com.accenture.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ProductoRepositoryAdapter
        extends ReactiveAdapterOperations<Producto, ProductoEntity, Long, ProductoReactiveRepository>
        implements ProductoRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductoRepositoryAdapter.class);

    public ProductoRepositoryAdapter(ProductoReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Producto.class));
    }

    @Override
    public Mono<Producto> save(Producto producto) {
        return super.save(producto)
                .doOnSuccess(p -> LOGGER.debug("save producto: {}", p));
    }

    @Override
    public Mono<Producto> findById(Long id) {
        return super.findById(id)
                .doOnNext(p -> LOGGER.debug("findById con id: {}", id));
    }

    @Override
    public Flux<Producto> findAll() {
        return super.findAll()
                .doOnNext(p -> LOGGER.debug("productos retornados: {}", p))
                .map(p -> p);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return super.repository.deleteById(id)
                .doOnSuccess(p -> LOGGER.debug("Producto eliminado con id: {}", id));
    }

    @Override
    public Flux<ProductoStockMayorDTO> getProductosStockMayor(Long idFranquicia) {
        return super.repository.productosMayorStockSucursal(idFranquicia)
                .doOnNext(p -> LOGGER.debug("Productos con mayor stock retornados: {}", p));
    }

}
