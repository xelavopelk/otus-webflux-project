package ru.flamexander.reactive.service.services;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.flamexander.reactive.service.dtos.DetailedProductDto;
import ru.flamexander.reactive.service.dtos.ProductDetailsDto;
import ru.flamexander.reactive.service.entities.Product;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JoinerById {
    private List<DetailedProductDto> merge(Map<Long, String> details, List<Product> products) {
        return products.stream()
                .map(p -> new DetailedProductDto(p.getId(), p.getName(), details.get(p.getId())))
                .toList();
    }

    public Flux<DetailedProductDto> join(Flux<Product> product, Flux<ProductDetailsDto> details) {
        var dMap = details
                .collectList()
                .map(l -> l.stream().collect(Collectors.toMap(i -> i.getId(), i -> i.getDescription())));
        var pList = product.collectList();
        return Mono.zip(dMap, pList)
                .map(t2 -> merge(t2.getT1(), t2.getT2()))
                .flatMapMany(Flux::fromIterable);

    }
}
