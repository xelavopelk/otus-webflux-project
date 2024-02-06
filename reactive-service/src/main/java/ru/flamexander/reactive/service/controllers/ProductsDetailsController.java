package ru.flamexander.reactive.service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.flamexander.reactive.service.dtos.DetailedProductDto;
import ru.flamexander.reactive.service.dtos.DetailsRequest;
import ru.flamexander.reactive.service.dtos.ProductDetailsDto;
import ru.flamexander.reactive.service.services.JoinerById;
import ru.flamexander.reactive.service.services.ProductDetailsService;
import ru.flamexander.reactive.service.services.ProductsService;

@RestController
@RequestMapping("/api/v1/detailed")
@RequiredArgsConstructor
public class ProductsDetailsController {
    private final ProductDetailsService productDetailsService;
    private final ProductsService productsService;
    private final static Integer BUFFER_SIZE = 3;

    @GetMapping("/demo")
    public Flux<ProductDetailsDto> getManySlowProducts() {
        Mono<ProductDetailsDto> p1 = productDetailsService.getProductDetailsById(1L);
        Mono<ProductDetailsDto> p2 = productDetailsService.getProductDetailsById(2L);
        Mono<ProductDetailsDto> p3 = productDetailsService.getProductDetailsById(3L);
        return p1.mergeWith(p2).mergeWith(p3);
    }

    @GetMapping("/{id}")
    public Mono<DetailedProductDto> getProductById(@PathVariable Long id) {
        var resDb = productsService.findById(id);
        var resSrv = resDb.flatMap(p -> productDetailsService.getProductDetailsById(p.getId()));
        var res = Mono.zip(resDb, resSrv)
                .map(tpl -> new DetailedProductDto(tpl.getT1().getId(), tpl.getT1().getName(),
                        tpl.getT2() != null ? tpl.getT2().getDescription() : null)
                );
        return res;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<DetailedProductDto> getIds(@RequestBody DetailsRequest request) {
        if (request.getIds().isEmpty()) {
            return Flux.empty();
        } else {
            var resDb = productsService.findByIds(request.getIds());
            var resSrv = productDetailsService.getProductDetailsByIds(request.getIds());
            var converter = new JoinerById();
            return converter.join(resDb, resSrv);
        }
    }

    @GetMapping("/all")
    public Flux<DetailedProductDto> getAll() {
        var resDb = productsService.findAll();
        var resSrv = resDb.buffer(BUFFER_SIZE)
                .map(batch -> productDetailsService.getProductDetailsByProducts(batch))
                .flatMap(Flux::merge);
        var converter = new JoinerById();
        return converter.join(resDb, resSrv);
    }

}
