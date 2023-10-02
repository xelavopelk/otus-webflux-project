package ru.flamexander.reactive.service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.flamexander.reactive.service.dtos.ProductDto;
import ru.flamexander.reactive.service.dtos.SomeDataDto;
import ru.flamexander.reactive.service.services.SlowService;

@RestController
@RequestMapping("/api/v1/slow")
public class SomeDataController {
    private final SlowService slowService;

    public SomeDataController(SlowService slowService) {
        this.slowService = slowService;
    }

    @GetMapping("/{id}")
    public Mono<SomeDataDto> getSomeDataById(@PathVariable Long id) {
        return slowService.getSomeDataById(id);
    }

    @GetMapping("/demo")
    public Flux<SomeDataDto> getManySlowProducts() {
        Mono<SomeDataDto> p1 = slowService.getSomeDataById(1L);
        Mono<SomeDataDto> p2 = slowService.getSomeDataById(2L);
        Mono<SomeDataDto> p3 = slowService.getSomeDataById(3L);
        return p1.mergeWith(p2).mergeWith(p3);
    }
}
