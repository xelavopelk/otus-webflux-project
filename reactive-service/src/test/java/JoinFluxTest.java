import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.flamexander.reactive.service.dtos.DetailedProductDto;
import ru.flamexander.reactive.service.dtos.ProductDetailsDto;
import ru.flamexander.reactive.service.entities.Product;
import ru.flamexander.reactive.service.services.JoinerById;

public class JoinFluxTest {
    Flux<ProductDetailsDto> get3Details() {
        return Flux.just(new ProductDetailsDto(1L, "Right1"), new ProductDetailsDto(2L, "Right2"), new ProductDetailsDto(3L, "Right3"));
    }

    Flux<Product> get3Products() {
        return Flux.just(new Product(1L, "Left1"), new Product(2L, "Left2"), new Product(3L, "Left3"));
    }

    @Test
    void test3CountSuccess() {
        var left = get3Products();
        StepVerifier.create(left)
                .expectNextCount(3)
                .expectComplete()
                .verify();
    }

    @Test
    void test3Success() {
        var left = get3Products();
        var right = get3Details();
        var s = left.sort();
        StepVerifier.create(left)
                .expectNextCount(3)
                .expectComplete()
                .verify();
    }

    @Test
    void test3JoinSuccess() {
        var left = get3Products();
        var right = get3Details();
        var j = new JoinerById();
        var probe = j.join(left, right);
        StepVerifier.create(probe)
                .expectNext(new DetailedProductDto(1L, "Left1", "Right1"))
                .expectNext(new DetailedProductDto(2L, "Left2", "Right2"))
                .expectNext(new DetailedProductDto(3L, "Left3", "Right3"))
                .expectComplete()
                .verify();
    }

    @Test
    void test3Join2Success() {
        var left = get3Products();
        var right = Flux.just(new ProductDetailsDto(1L, "Right1"), new ProductDetailsDto(2L, "Right2"));
        var j = new JoinerById();
        var probe = j.join(left, right);
        StepVerifier.create(probe)
                .expectNext(new DetailedProductDto(1L, "Left1", "Right1"))
                .expectNext(new DetailedProductDto(2L, "Left2", "Right2"))
                .expectNext(new DetailedProductDto(3L, "Left3", null))
                .expectComplete()
                .verify();
    }

}
