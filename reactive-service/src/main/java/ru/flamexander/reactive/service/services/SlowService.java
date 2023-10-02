package ru.flamexander.reactive.service.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.flamexander.reactive.service.dtos.ProductDto;
import ru.flamexander.reactive.service.dtos.SomeDataDto;
import ru.flamexander.reactive.service.integrations.SlowServiceIntegration;
import ru.flamexander.reactive.service.repositories.ProductRepository;

@Service
public class SlowService {
    private final SlowServiceIntegration slowServiceIntegration;

    public SlowService(SlowServiceIntegration slowServiceIntegration) {
        this.slowServiceIntegration = slowServiceIntegration;
    }

    public Mono<SomeDataDto> getSomeDataById(Long id) {
        return slowServiceIntegration.getSomeDataById(id);
    }
}
