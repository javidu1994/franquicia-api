package co.com.accenture.r2dbc;

import co.com.accenture.model.franquicia.Franquicia;
import co.com.accenture.r2dbc.entity.FranquiciaEntity;
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
class FranquiciaRepositoryAdapterTest {

    @Mock
    private FranquiciaReactiveRepository repository;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private Franquicia franquicia;

    @Mock
    private FranquiciaEntity franquiciaEntity;

    private FranquiciaRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new FranquiciaRepositoryAdapter(repository, mapper);
    }

    @Test
    void shouldSaveFranquicia() {
        // Arrange
        when(mapper.map(
                franquicia,
                FranquiciaEntity.class
        )).thenReturn(franquiciaEntity);

        when(repository.save(franquiciaEntity))
                .thenReturn(Mono.just(franquiciaEntity));

        when(mapper.map(
                franquiciaEntity,
                Franquicia.class
        )).thenReturn(franquicia);

        // Act
        Mono<Franquicia> result = adapter.save(franquicia);

        // Assert
        StepVerifier.create(result)
                .expectNext(franquicia)
                .verifyComplete();

        verify(mapper).map(
                franquicia,
                FranquiciaEntity.class
        );

        verify(repository).save(franquiciaEntity);

        verify(mapper).map(
                franquiciaEntity,
                Franquicia.class
        );
    }

    @Test
    void shouldFindFranquiciaById() {
        // Arrange
        Long id = 1L;

        when(repository.findById(id))
                .thenReturn(Mono.just(franquiciaEntity));

        when(mapper.map(
                franquiciaEntity,
                Franquicia.class
        )).thenReturn(franquicia);

        // Act
        Mono<Franquicia> result = adapter.findById(id);

        // Assert
        StepVerifier.create(result)
                .expectNext(franquicia)
                .verifyComplete();

        verify(repository).findById(id);

        verify(mapper).map(
                franquiciaEntity,
                Franquicia.class
        );
    }

    @Test
    void shouldReturnEmptyWhenFranquiciaDoesNotExist() {
        // Arrange
        Long id = 99L;

        when(repository.findById(id))
                .thenReturn(Mono.empty());

        // Act
        Mono<Franquicia> result = adapter.findById(id);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById(id);

        verify(mapper, never()).map(
                franquiciaEntity,
                Franquicia.class
        );
    }

    @Test
    void shouldFindAllFranquicias() {
        // Arrange
        FranquiciaEntity entity1 = mock(FranquiciaEntity.class);
        FranquiciaEntity entity2 = mock(FranquiciaEntity.class);

        Franquicia franquicia1 = mock(Franquicia.class);
        Franquicia franquicia2 = mock(Franquicia.class);

        when(repository.findAll())
                .thenReturn(Flux.just(entity1, entity2));

        when(mapper.map(entity1, Franquicia.class))
                .thenReturn(franquicia1);

        when(mapper.map(entity2, Franquicia.class))
                .thenReturn(franquicia2);

        // Act
        Flux<Franquicia> result = adapter.findAll();

        // Assert
        StepVerifier.create(result)
                .expectNext(franquicia1)
                .expectNext(franquicia2)
                .verifyComplete();

        verify(repository).findAll();

        verify(mapper).map(entity1, Franquicia.class);
        verify(mapper).map(entity2, Franquicia.class);
    }

    @Test
    void shouldReturnEmptyWhenThereAreNoFranquicias() {
        // Arrange
        when(repository.findAll())
                .thenReturn(Flux.empty());

        // Act
        Flux<Franquicia> result = adapter.findAll();

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findAll();

        verifyNoInteractions(mapper);
    }
}