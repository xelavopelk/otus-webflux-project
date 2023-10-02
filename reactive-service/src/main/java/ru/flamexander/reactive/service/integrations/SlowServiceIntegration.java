package ru.flamexander.reactive.service.integrations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.flamexander.reactive.service.dtos.SomeDataDto;
import ru.flamexander.reactive.service.exceptions.AppException;

@Component
public class SlowServiceIntegration {
    private static final Logger logger = LoggerFactory.getLogger(SlowServiceIntegration.class.getName());

    private final WebClient slowServiceWebClient;

    public SlowServiceIntegration(WebClient slowServiceWebClient) {
        this.slowServiceWebClient = slowServiceWebClient;
    }

    public Mono<SomeDataDto> getSomeDataById(Long id) {
        logger.info("SEND REQUEST FOR SOME-DATA-ID: {}", id);
        return slowServiceWebClient.get()
                .uri("/api/v1/data/{id}", id)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.isError(),
                        clientResponse -> Mono.error(new AppException("SLOW_SERVICE_INTEGRATION_ERROR"))
                )
                .bodyToMono(SomeDataDto.class)
                .log();
    }
}
