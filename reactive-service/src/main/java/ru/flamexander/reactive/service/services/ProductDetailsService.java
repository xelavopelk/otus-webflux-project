package ru.flamexander.reactive.service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.flamexander.reactive.service.dtos.ProductDetailsDto;
import ru.flamexander.reactive.service.integrations.ProductDetailsServiceIntegration;

@Service
@RequiredArgsConstructor
public class ProductDetailsService {
    private final ProductDetailsServiceIntegration productDetailsServiceIntegration;

    public Mono<ProductDetailsDto> getProductDetailsById(Long id) {
        return productDetailsServiceIntegration.getProductDetailsById(id);
    }
}
