package co.com.accenture.usecase.sucursal;

import co.com.accenture.model.sucursal.Sucursal;
import co.com.accenture.model.sucursal.gateways.SucursalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SucursalUseCaseTest {

    @Mock
    private SucursalRepository sucursalRepository;

    private SucursalUseCase sucursalUseCase;

    @BeforeEach
    void setUp() {
        sucursalUseCase = new SucursalUseCase(sucursalRepository);
    }

    @Test
    void shouldSaveSucursal() {
        // Arrange
        Sucursal sucursal = new Sucursal();
        sucursal.setIdSucursal(1L);
        sucursal.setNombre("Sucursal Principal");

        when(sucursalRepository.save(sucursal))
                .thenReturn(Mono.just(sucursal));

        // Act
        Mono<Sucursal> result = sucursalUseCase.save(sucursal);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(s ->
                        s.getIdSucursal().equals(1L)
                                && s.getNombre().equals("Sucursal Principal"))
                .verifyComplete();

        verify(sucursalRepository).save(sucursal);
    }

    @Test
    void shouldPropagateErrorWhenSaveSucursalFails() {
        // Arrange
        Sucursal sucursal = new Sucursal();
        sucursal.setIdSucursal(1L);
        sucursal.setNombre("Sucursal Principal");

        RuntimeException exception =
                new RuntimeException("Error al guardar sucursal");

        when(sucursalRepository.save(sucursal))
                .thenReturn(Mono.error(exception));

        // Act
        Mono<Sucursal> result = sucursalUseCase.save(sucursal);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(error ->
                        error instanceof RuntimeException
                                && error.getMessage()
                                .equals("Error al guardar sucursal"))
                .verify();

        verify(sucursalRepository).save(sucursal);
    }

    @Test
    void shouldFindSucursalById() {
        // Arrange
        Long id = 1L;

        Sucursal sucursal = new Sucursal();
        sucursal.setIdSucursal(id);
        sucursal.setNombre("Sucursal Principal");

        when(sucursalRepository.findById(id))
                .thenReturn(Mono.just(sucursal));

        // Act
        Mono<Sucursal> result = sucursalUseCase.findById(id);

        // Assert
        StepVerifier.create(result)
                .expectNext(sucursal)
                .verifyComplete();

        verify(sucursalRepository).findById(id);
    }

    @Test
    void shouldFindAllSucursales() {
        // Arrange
        Sucursal sucursal1 = new Sucursal();
        sucursal1.setIdSucursal(1L);
        sucursal1.setNombre("Sucursal Principal");

        Sucursal sucursal2 = new Sucursal();
        sucursal2.setIdSucursal(2L);
        sucursal2.setNombre("Sucursal Norte");

        when(sucursalRepository.findAll())
                .thenReturn(Flux.just(sucursal1, sucursal2));

        // Act
        Flux<Sucursal> result = sucursalUseCase.findAll();

        // Assert
        StepVerifier.create(result)
                .expectNext(sucursal1)
                .expectNext(sucursal2)
                .verifyComplete();

        verify(sucursalRepository).findAll();
    }

    @Test
    void shouldUpdateSucursal() {
        // Arrange
        Long id = 1L;
        String nuevoNombre = "Sucursal Actualizada";

        Sucursal sucursal = new Sucursal();
        sucursal.setIdSucursal(id);
        sucursal.setNombre("Sucursal Original");

        when(sucursalRepository.findById(id))
                .thenReturn(Mono.just(sucursal));

        when(sucursalRepository.save(sucursal))
                .thenReturn(Mono.just(sucursal));

        // Act
        Mono<Sucursal> result =
                sucursalUseCase.update(id, nuevoNombre);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(s ->
                        s.getIdSucursal().equals(id)
                                && s.getNombre().equals(nuevoNombre))
                .verifyComplete();

        verify(sucursalRepository).findById(id);
        verify(sucursalRepository).save(sucursal);

        assertEquals(nuevoNombre, sucursal.getNombre());
    }
}