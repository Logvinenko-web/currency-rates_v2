package com.anton.rate.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "spring.config")
@Configuration
public class RateConfig {

    private String mockUrl;
    private Url url;
    private Api api;

    @Data
    public static class Url {
        private String fiat;
        private String crypto;
    }

    @Data
    public static class Api {
        private String key;
    }
}
