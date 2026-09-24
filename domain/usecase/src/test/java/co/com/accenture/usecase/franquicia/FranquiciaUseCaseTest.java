package co.com.accenture.usecase.franquicia;

import co.com.accenture.model.franquicia.Franquicia;
import co.com.accenture.model.franquicia.gateways.FranquiciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FranquiciaUseCaseTest {

    @Mock
    private FranquiciaRepository franquiciaRepository;

    private FranquiciaUseCase franquiciaUseCase;

    @BeforeEach
    void setUp() {
        franquiciaUseCase = new FranquiciaUseCase(franquiciaRepository);
    }

    @Test
    void shouldSaveFranquicia() {
        // Arrange
        Franquicia franquicia = new Franquicia();
        franquicia.setIdFranquicia(1L);
        franquicia.setNombre("Franquicia 1");

        when(franquiciaRepository.save(franquicia))
                .thenReturn(Mono.just(franquicia));

        // Act
        Mono<Franquicia> result = franquiciaUseCase.save(franquicia);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(f ->
                        f.getIdFranquicia().equals(1L)
                                && f.getNombre().equals("Franquicia 1"))
                .verifyComplete();

        verify(franquiciaRepository).save(franquicia);
    }

    @Test
    void shouldFindFranquiciaById() {
        // Arrange
        Long id = 1L;

        Franquicia franquicia = new Franquicia();
        franquicia.setIdFranquicia(id);
        franquicia.setNombre("Franquicia 1");

        when(franquiciaRepository.findById(id))
                .thenReturn(Mono.just(franquicia));

        // Act
        Mono<Franquicia> result = franquiciaUseCase.findById(id);

        // Assert
        StepVerifier.create(result)
                .expectNext(franquicia)
                .verifyComplete();

        verify(franquiciaRepository).findById(id);
    }

    @Test
    void shouldFindAllFranquicias() {
        // Arrange
        Franquicia franquicia1 = new Franquicia();
        franquicia1.setIdFranquicia(1L);
        franquicia1.setNombre("Franquicia 1");

        Franquicia franquicia2 = new Franquicia();
        franquicia2.setIdFranquicia(2L);
        franquicia2.setNombre("Franquicia 2");

        when(franquiciaRepository.findAll())
                .thenReturn(Flux.just(franquicia1, franquicia2));

        // Act
        Flux<Franquicia> result = franquiciaUseCase.findAll();

        // Assert
        StepVerifier.create(result)
                .expectNext(franquicia1)
                .expectNext(franquicia2)
                .verifyComplete();

        verify(franquiciaRepository).findAll();
    }


    @Test
    void shouldUpdateFranquicia() {
        // Arrange
        Long id = 1L;
        String nuevoNombre = "Franquicia Actualizada";

        Franquicia franquicia = new Franquicia();
        franquicia.setIdFranquicia(id);
        franquicia.setNombre("Franquicia Original");

        Franquicia franquiciaActualizada = new Franquicia();
        franquiciaActualizada.setIdFranquicia(id);
        franquiciaActualizada.setNombre(nuevoNombre);

        when(franquiciaRepository.findById(id))
                .thenReturn(Mono.just(franquicia));

        when(franquiciaRepository.save(franquicia))
                .thenReturn(Mono.just(franquiciaActualizada));

        // Act
        Mono<Franquicia> result =
                franquiciaUseCase.update(id, nuevoNombre);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(f ->
                        f.getIdFranquicia().equals(id)
                                && f.getNombre().equals(nuevoNombre))
                .verifyComplete();

        verify(franquiciaRepository).findById(id);
        verify(franquiciaRepository).save(franquicia);
    }
}

