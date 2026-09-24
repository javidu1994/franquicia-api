package co.com.accenture.r2dbc;

import co.com.accenture.model.producto.Producto;
import co.com.accenture.model.producto.dto.ProductoStockMayorDTO;
import co.com.accenture.r2dbc.entity.ProductoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoRepositoryAdapterTest {

    @Mock
    private ProductoReactiveRepository repository;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private Producto producto;

    @Mock
    private ProductoEntity productoEntity;

    private ProductoRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new ProductoRepositoryAdapter(repository, mapper);
    }

    @Test
    void shouldSaveProducto() {

        // Arrange
        when(mapper.map(
                producto,
                ProductoEntity.class
        )).thenReturn(productoEntity);

        when(repository.save(productoEntity))
                .thenReturn(Mono.just(productoEntity));

        when(mapper.map(
                productoEntity,
                Producto.class
        )).thenReturn(producto);

        // Act
        Mono<Producto> result = adapter.save(producto);

        // Assert
        StepVerifier.create(result)
                .expectNext(producto)
                .verifyComplete();

        verify(mapper).map(
                producto,
                ProductoEntity.class
        );

        verify(repository).save(productoEntity);

        verify(mapper).map(
                productoEntity,
                Producto.class
        );
    }

    @Test
    void shouldFindProductoById() {

        // Arrange
        Long id = 1L;

        when(repository.findById(id))
                .thenReturn(Mono.just(productoEntity));

        when(mapper.map(
                productoEntity,
                Producto.class
        )).thenReturn(producto);

        // Act
        Mono<Producto> result = adapter.findById(id);

        // Assert
        StepVerifier.create(result)
                .expectNext(producto)
                .verifyComplete();

        verify(repository).findById(id);

        verify(mapper).map(
                productoEntity,
                Producto.class
        );
    }

    @Test
    void shouldReturnEmptyWhenProductoDoesNotExist() {

        // Arrange
        Long id = 99L;

        when(repository.findById(id))
                .thenReturn(Mono.empty());

        // Act
        Mono<Producto> result = adapter.findById(id);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(id);

        verify(mapper, never()).map(
                productoEntity,
                Producto.class
        );
    }

    @Test
    void shouldFindAllProductos() {

        // Arrange
        ProductoEntity entity1 =
                mock(ProductoEntity.class);

        ProductoEntity entity2 =
                mock(ProductoEntity.class);

        Producto producto1 =
                mock(Producto.class);

        Producto producto2 =
                mock(Producto.class);

        when(repository.findAll())
                .thenReturn(
                        Flux.just(entity1, entity2)
                );

        when(mapper.map(
                entity1,
                Producto.class
        )).thenReturn(producto1);

        when(mapper.map(
                entity2,
                Producto.class
        )).thenReturn(producto2);

        // Act
        Flux<Producto> result = adapter.findAll();

        // Assert
        StepVerifier.create(result)
                .expectNext(producto1)
                .expectNext(producto2)
                .verifyComplete();

        verify(repository).findAll();

        verify(mapper).map(
                entity1,
                Producto.class
        );

        verify(mapper).map(
                entity2,
                Producto.class
        );
    }

    @Test
    void shouldReturnEmptyWhenThereAreNoProductos() {

        // Arrange
        when(repository.findAll())
                .thenReturn(Flux.empty());

        // Act
        Flux<Producto> result = adapter.findAll();

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findAll();

        verifyNoInteractions(mapper);
    }

    @Test
    void shouldDeleteProductoById() {

        // Arrange
        Long id = 1L;

        when(repository.deleteById(id))
                .thenReturn(Mono.empty());

        // Act
        Mono<Void> result = adapter.deleteById(id);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).deleteById(id);
    }

    @Test
    void shouldGetProductosStockMayor() {

        // Arrange
        Long idFranquicia = 1L;

        ProductoStockMayorDTO producto1 =
                mock(ProductoStockMayorDTO.class);

        ProductoStockMayorDTO producto2 =
                mock(ProductoStockMayorDTO.class);

        when(repository.productosMayorStockSucursal(
                idFranquicia
        )).thenReturn(
                Flux.just(producto1, producto2)
        );

        // Act
        Flux<ProductoStockMayorDTO> result =
                adapter.getProductosStockMayor(idFranquicia);

        // Assert
        StepVerifier.create(result)
                .expectNext(producto1)
                .expectNext(producto2)
                .verifyComplete();

        verify(repository)
                .productosMayorStockSucursal(idFranquicia);
    }
}
