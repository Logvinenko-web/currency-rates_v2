package com.rates.service;

import com.rates.config.RateConfig;
import com.rates.entity.Crypto;
import com.rates.mapper.CryptoMapper;
import com.rates.model.CryptoDto;
import com.rates.model.CryptoResponse;
import com.rates.repository.CryptoCurrencyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class CryptoService {

    private final WebClient webClient;
    private final CryptoCurrencyRepository repository;
    private final RateConfig rateConfig;

    public Flux<Crypto> fetchAndSaveCryptoRates () {
        log.info("Received request to fetch crypto rates");
        return webClient.get()
            .uri(rateConfig.getUrl().getCrypto())
            .retrieve()
            .bodyToFlux(CryptoResponse.class)
            .doOnNext(crypto -> log.info("Received crypto data: {}", crypto))
            .map(CryptoMapper.INSTANCE::toCryptoEntity)
            .onErrorResume(e -> {
                log.info("Error while fetching crypto data: {}", e.getMessage());
                return repository.findTop3ByOrderByCreatedAtDesc();
            })
            .flatMap(repository::save);

    }

    public Mono<List<CryptoDto>> getCryptoRates () {

        return fetchAndSaveCryptoRates()
            .map(CryptoMapper.INSTANCE::toCryptoDto)
            .collectList()
            .defaultIfEmpty(List.of());
    }

}
