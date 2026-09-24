package co.com.accenture.usecase.producto;

import co.com.accenture.model.producto.Producto;
import co.com.accenture.model.producto.dto.ProductoStockMayorDTO;
import co.com.accenture.model.producto.gateways.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoUseCaseTest {

    @Mock
    private ProductoRepository productoRepository;

    private ProductoUseCase productoUseCase;

    @BeforeEach
    void setUp() {
        productoUseCase = new ProductoUseCase(productoRepository);
    }

    @Test
    void shouldSaveProducto() {
        // Arrange
        Producto producto = new Producto();
        producto.setIdProducto(1L);
        producto.setNombre("Producto 1");
        producto.setCantidad(100);

        when(productoRepository.save(producto))
                .thenReturn(Mono.just(producto));

        // Act
        Mono<Producto> result = productoUseCase.save(producto);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(p ->
                        p.getIdProducto().equals(1L)
                                && p.getNombre().equals("Producto 1")
                                && p.getCantidad().equals(100))
                .verifyComplete();

        verify(productoRepository).save(producto);
    }

    @Test
    void shouldFindProductoById() {
        // Arrange
        Long id = 1L;

        Producto producto = new Producto();
        producto.setIdProducto(id);
        producto.setNombre("Producto 1");
        producto.setCantidad(100);

        when(productoRepository.findById(id))
                .thenReturn(Mono.just(producto));

        // Act
        Mono<Producto> result = productoUseCase.findById(id);

        // Assert
        StepVerifier.create(result)
                .expectNext(producto)
                .verifyComplete();

        verify(productoRepository).findById(id);
    }

    @Test
    void shouldFindAllProductos() {
        // Arrange
        Producto producto1 = new Producto();
        producto1.setIdProducto(1L);
        producto1.setNombre("Producto 1");
        producto1.setCantidad(100);

        Producto producto2 = new Producto();
        producto2.setIdProducto(2L);
        producto2.setNombre("Producto 2");
        producto2.setCantidad(200);

        when(productoRepository.findAll())
                .thenReturn(Flux.just(producto1, producto2));

        // Act
        Flux<Producto> result = productoUseCase.findAll();

        // Assert
        StepVerifier.create(result)
                .expectNext(producto1)
                .expectNext(producto2)
                .verifyComplete();

        verify(productoRepository).findAll();
    }

    @Test
    void shouldDeleteProductoById() {
        // Arrange
        Long id = 1L;

        when(productoRepository.deleteById(id))
                .thenReturn(Mono.empty());

        // Act
        Mono<Void> result = productoUseCase.deleteById(id);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(productoRepository).deleteById(id);
    }

    @Test
    void shouldUpdateStockProducto() {
        // Arrange
        Long idProducto = 1L;
        Integer cantidadNueva = 500;

        Producto producto = new Producto();
        producto.setIdProducto(idProducto);
        producto.setNombre("Producto 1");
        producto.setCantidad(100);

        when(productoRepository.findById(idProducto))
                .thenReturn(Mono.just(producto));

        when(productoRepository.save(any(Producto.class)))
                .thenReturn(Mono.just(producto));

        // Act
        Mono<Void> result =
                productoUseCase.updateStockProducto(
                        idProducto,
                        cantidadNueva
                );

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        ArgumentCaptor<Producto> productoCaptor =
                ArgumentCaptor.forClass(Producto.class);

        verify(productoRepository).findById(idProducto);
        verify(productoRepository).save(productoCaptor.capture());

        Producto productoGuardado = productoCaptor.getValue();

        assertEquals(idProducto, productoGuardado.getIdProducto());
        assertEquals(cantidadNueva, productoGuardado.getCantidad());
        assertEquals("Producto 1", productoGuardado.getNombre());
    }

    @Test
    void shouldPropagateErrorWhenUpdatingStockFails() {
        // Arrange
        Long idProducto = 1L;
        Integer cantidadNueva = 500;

        Producto producto = new Producto();
        producto.setIdProducto(idProducto);
        producto.setNombre("Producto 1");
        producto.setCantidad(100);

        RuntimeException exception =
                new RuntimeException("Error al actualizar stock");

        when(productoRepository.findById(idProducto))
                .thenReturn(Mono.just(producto));

        when(productoRepository.save(any(Producto.class)))
                .thenReturn(Mono.error(exception));

        // Act
        Mono<Void> result =
                productoUseCase.updateStockProducto(
                        idProducto,
                        cantidadNueva
                );

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(error ->
                        error instanceof RuntimeException
                                && error.getMessage()
                                .equals("Error al actualizar stock"))
                .verify();

        verify(productoRepository).findById(idProducto);
        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    void shouldGetProductosStockMayor() {
        // Arrange
        Long idFranquicia = 1L;

        ProductoStockMayorDTO producto1 =
                new ProductoStockMayorDTO(
                        "Franquicia 1",
                        "Producto 1",
                        500
                );

        ProductoStockMayorDTO producto2 =
                new ProductoStockMayorDTO(
                        "Franquicia 1",
                        "Producto 2",
                        300
                );

        when(productoRepository.getProductosStockMayor(idFranquicia))
                .thenReturn(Flux.just(producto1, producto2));

        // Act
        Flux<ProductoStockMayorDTO> result =
                productoUseCase.getProductosStockMayor(idFranquicia);

        // Assert
        StepVerifier.create(result)
                .expectNext(producto1)
                .expectNext(producto2)
                .verifyComplete();

        verify(productoRepository)
                .getProductosStockMayor(idFranquicia);
    }

    @Test
    void shouldUpdateProductoName() {
        // Arrange
        Long id = 1L;
        String nuevoNombre = "Producto Actualizado";

        Producto producto = new Producto();
        producto.setIdProducto(id);
        producto.setNombre("Producto Original");
        producto.setCantidad(100);

        when(productoRepository.findById(id))
                .thenReturn(Mono.just(producto));

        when(productoRepository.save(producto))
                .thenReturn(Mono.just(producto));

        // Act
        Mono<Producto> result =
                productoUseCase.update(id, nuevoNombre);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(p ->
                        p.getIdProducto().equals(id)
                                && p.getNombre()
                                .equals(nuevoNombre)
                                && p.getCantidad().equals(100))
                .verifyComplete();

        verify(productoRepository).findById(id);
        verify(productoRepository).save(producto);

        assertEquals(nuevoNombre, producto.getNombre());
    }

}
