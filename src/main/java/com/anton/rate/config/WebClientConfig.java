package com.anton.rate.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final RateConfig rateConfig;
    @Bean
    public WebClient webClient () {
        return WebClient.builder().baseUrl(rateConfig.getMockUrl()).build();
    }

}
