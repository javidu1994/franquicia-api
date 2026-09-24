package co.com.accenture.r2dbc;

import co.com.accenture.model.sucursal.Sucursal;
import co.com.accenture.r2dbc.entity.SucursalEntity;
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
class SucursalRepositoryAdapterTest {

    @Mock
    private SucursalReactiveRepository repository;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private Sucursal sucursal;

    @Mock
    private SucursalEntity sucursalEntity;

    private SucursalRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new SucursalRepositoryAdapter(repository, mapper);
    }

    @Test
    void shouldSaveSucursal() {
        // Arrange
        when(mapper.map(
                sucursal,
                SucursalEntity.class
        )).thenReturn(sucursalEntity);

        when(repository.save(sucursalEntity))
                .thenReturn(Mono.just(sucursalEntity));

        when(mapper.map(
                sucursalEntity,
                Sucursal.class
        )).thenReturn(sucursal);

        // Act
        Mono<Sucursal> result = adapter.save(sucursal);

        // Assert
        StepVerifier.create(result)
                .expectNext(sucursal)
                .verifyComplete();

        verify(mapper).map(
                sucursal,
                SucursalEntity.class
        );

        verify(repository).save(sucursalEntity);

        verify(mapper).map(
                sucursalEntity,
                Sucursal.class
        );
    }

    @Test
    void shouldPropagateErrorWhenSaveFails() {
        // Arrange
        RuntimeException exception =
                new RuntimeException("Error al guardar sucursal");

        when(mapper.map(
                sucursal,
                SucursalEntity.class
        )).thenReturn(sucursalEntity);

        when(repository.save(sucursalEntity))
                .thenReturn(Mono.error(exception));

        // Act
        Mono<Sucursal> result = adapter.save(sucursal);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(error ->
                        error instanceof RuntimeException
                                && error.getMessage().equals(
                                "Error al guardar sucursal"))
                .verify();

        verify(repository).save(sucursalEntity);

        verify(mapper, never()).map(
                sucursalEntity,
                Sucursal.class
        );
    }

    @Test
    void shouldFindSucursalById() {
        // Arrange
        Long id = 1L;

        when(repository.findById(id))
                .thenReturn(Mono.just(sucursalEntity));

        when(mapper.map(
                sucursalEntity,
                Sucursal.class
        )).thenReturn(sucursal);

        // Act
        Mono<Sucursal> result = adapter.findById(id);

        // Assert
        StepVerifier.create(result)
                .expectNext(sucursal)
                .verifyComplete();

        verify(repository).findById(id);

        verify(mapper).map(
                sucursalEntity,
                Sucursal.class
        );
    }

    @Test
    void shouldFindAllSucursales() {
        // Arrange
        SucursalEntity entity1 = mock(SucursalEntity.class);
        SucursalEntity entity2 = mock(SucursalEntity.class);

        Sucursal sucursal1 = mock(Sucursal.class);
        Sucursal sucursal2 = mock(Sucursal.class);

        when(repository.findAll())
                .thenReturn(Flux.just(entity1, entity2));

        when(mapper.map(
                entity1,
                Sucursal.class
        )).thenReturn(sucursal1);

        when(mapper.map(
                entity2,
                Sucursal.class
        )).thenReturn(sucursal2);

        // Act
        Flux<Sucursal> result = adapter.findAll();

        // Assert
        StepVerifier.create(result)
                .expectNext(sucursal1)
                .expectNext(sucursal2)
                .verifyComplete();

        verify(repository).findAll();

        verify(mapper).map(
                entity1,
                Sucursal.class
        );

        verify(mapper).map(
                entity2,
                Sucursal.class
        );
    }

    @Test
    void shouldReturnEmptyWhenThereAreNoSucursales() {
        // Arrange
        when(repository.findAll())
                .thenReturn(Flux.empty());

        // Act
        Flux<Sucursal> result = adapter.findAll();

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findAll();

        verifyNoInteractions(mapper);
    }
}