package com.anton.rate.service;

import com.anton.rate.config.RateConfig;
import com.anton.rate.entity.Fiat;
import com.anton.rate.mapper.FiatMapper;
import com.anton.rate.model.FiatDto;
import com.anton.rate.model.FiatResponse;
import com.anton.rate.repository.FiatCurrencyRepository;
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
public class FiatService {

    private final WebClient webClient;
    private final FiatCurrencyRepository repository;
    private final LastCurrencyRepository lastCurrencyRepository;
    private final RateConfig rateConfig;

    public Flux<Fiat> fetchAndSaveFiatRates () {
        log.info("Received request to fetch fiat rates");
        return webClient.get()
            .uri(rateConfig.getUrl().getFiat())
            .header("X-API-KEY", rateConfig.getApi().getKey())
            .retrieve()
            .bodyToFlux(FiatResponse.class)
            .doOnNext(fiat -> log.info("Received fiat data: {}", fiat))
            .map(FiatMapper.INSTANCE::toFiatEntity)
            .onErrorResume(e -> {
                log.info("Error while fetching fiat data: {}", e.getMessage());
                return repository.findLastCurrency("fiat");
            })
            .flatMap(fiat ->
                repository.save(fiat)
                    .then(lastCurrencyRepository.saveOrUpdateLatestRate(fiat.getCurrency(), fiat.getRate(), "fiat"))
                    .thenReturn(fiat));
    }

    public Mono<List<FiatDto>> getFiatRates () {

        return fetchAndSaveFiatRates()
            .map(FiatMapper.INSTANCE::toFiatDto)
            .collectList()
            .defaultIfEmpty(List.of());
    }
}
