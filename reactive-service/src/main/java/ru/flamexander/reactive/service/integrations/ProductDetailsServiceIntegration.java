package ru.flamexander.reactive.service.integrations;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.flamexander.reactive.service.dtos.DetailsRequest;
import ru.flamexander.reactive.service.dtos.ProductDetailsDto;
import ru.flamexander.reactive.service.exceptions.AppException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductDetailsServiceIntegration {
    private static final Logger logger = LoggerFactory.getLogger(ProductDetailsServiceIntegration.class.getName());

    private final WebClient productDetailsServiceWebClient;

    public Mono<ProductDetailsDto> getProductDetailsById(Long id) {
        logger.info("SEND REQUEST FOR PRODUCT_DETAILS-ID: {}", id);
        return productDetailsServiceWebClient.get()
                .uri("/api/v1/details/{id}", id)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.isError(),
                        clientResponse -> Mono.error(new AppException("PRODUCT_DETAILS_SERVICE_INTEGRATION_ERROR"))
                )
                .bodyToMono(ProductDetailsDto.class)
                .log();
    }

    public Flux<ProductDetailsDto> getProductDetailsByIds(List<Long> ids) {
        logger.info("SEND REQUEST FOR PRODUCT_DETAILS-ID: {}", ids.size());
        return productDetailsServiceWebClient.post()
                .uri("/api/v1/details")
                .bodyValue(new DetailsRequest(ids))
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.isError(),
                        clientResponse -> Mono.error(new AppException("PRODUCT_DETAILS_SERVICE_INTEGRATION_ERROR(2)"))
                )
                .bodyToFlux(ProductDetailsDto.class)
                .log();
    }

}
