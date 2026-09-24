package co.com.accenture.r2dbc;

import co.com.accenture.model.producto.dto.ProductoStockMayorDTO;
import co.com.accenture.r2dbc.entity.ProductoEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoReactiveRepository
        extends ReactiveCrudRepository<ProductoEntity, Long>, ReactiveQueryByExampleExecutor<ProductoEntity> {

    Mono<ProductoEntity> findById(Long idProducto);

    Flux<ProductoEntity> findAll();

    @Query("""
    SELECT
        s.nombre AS nombre_sucursal,
        p.nombre AS nombre_producto,
        p.cantidad AS cantidad
    FROM sucursal s
    JOIN producto p
        ON p.id_sucursal = s.id_sucursal
    WHERE s.id_franquicia = :idFranquicia AND p.cantidad = (
        SELECT MAX(p2.cantidad)
        FROM producto p2
        WHERE p2.id_sucursal = s.id_sucursal
    )
    """)
    Flux<ProductoStockMayorDTO> productosMayorStockSucursal(@Param("idFranquicia") Long idFranquicia);
}
