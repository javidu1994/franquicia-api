package co.com.accenture.r2dbc;

import co.com.accenture.model.franquicia.Franquicia;
import co.com.accenture.r2dbc.entity.FranquiciaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FranquiciaReactiveRepositoryAdapterTest {

    @InjectMocks
    FranquiciaRepositoryAdapter repositoryAdapter;

    @Mock
    FranquiciaReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    @Test
    void mustFindValueById() {

        when(repository.findById(1L)).thenReturn(Mono.just(new FranquiciaEntity(1L, "test")));
        when(mapper.map(new FranquiciaEntity(1L, "test"), Franquicia.class))
                .thenReturn(new Franquicia(1L, "test"));

        Mono<Franquicia> result = repositoryAdapter.findById(1L);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals("test"))
                .verifyComplete();
    }

    @Test
    void mustFindAllValues() {
        when(repository.findAll()).thenReturn(Flux.just(new FranquiciaEntity(1L, "test")));
        when(mapper.map("test", Object.class)).thenReturn("test");

        Flux<Franquicia> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals("test"))
                .verifyComplete();
    }

    @Test
    void mustFindByExample() {
        when(repository.findAll(any(Example.class))).thenReturn(Flux.just("test"));
        when(mapper.map("test", Object.class)).thenReturn("test");

        Flux<Franquicia> result = repositoryAdapter.findByExample(new Franquicia(1L, "test"));

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals("test"))
                .verifyComplete();
    }

    @Test
    void mustSaveValue() {
        when(repository.save(new FranquiciaEntity(1L, "test")))
                .thenReturn(Mono.just(new FranquiciaEntity(1L, "test")));
        when(mapper.map("test", Object.class)).thenReturn("test");

        Mono<Franquicia> result = repositoryAdapter.save(new Franquicia(1L, "test"));

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals("test"))
                .verifyComplete();
    }
}
