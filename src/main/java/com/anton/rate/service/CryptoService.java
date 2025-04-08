package com.anton.rate.service;

import com.anton.rate.config.RateConfig;
import com.anton.rate.entity.Crypto;
import com.anton.rate.entity.LastCurrency;
import com.anton.rate.mapper.CryptoMapper;
import com.anton.rate.model.CryptoDto;
import com.anton.rate.model.CryptoResponse;
import com.anton.rate.repository.CryptoCurrencyRepository;
import com.anton.rate.repository.LastCurrencyRepository;
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
    private final LastCurrencyRepository lastCurrencyRepository;
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
                return repository.findLastCurrency("crypto");
            })
            .flatMap(crypto ->
                repository.save(crypto)
                    .then(
                        lastCurrencyRepository.findByCurrency(crypto.getCurrency())
                            .flatMap(existing -> {
                                existing.setRate(crypto.getRate());
                                return lastCurrencyRepository.save(existing);
                            })
                            .switchIfEmpty(
                                lastCurrencyRepository.save(
                                    LastCurrency.builder()
                                        .currency(crypto.getCurrency())
                                        .rate(crypto.getRate())
                                        .source("crypto")
                                        .build()
                                )
                            )
                    )
                    .thenReturn(crypto));

    }

    public Mono<List<CryptoDto>> getCryptoRates () {

        return fetchAndSaveCryptoRates()
            .map(CryptoMapper.INSTANCE::toCryptoDto)
            .collectList()
            .defaultIfEmpty(List.of());
    }

}
