package com.anton.rate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import reactor.core.publisher.Mono;


@Configuration
@EnableR2dbcAuditing
public class AuditingConfig implements ReactiveAuditorAware<String> {

    @Override
    public Mono<String> getCurrentAuditor () {
        return Mono.empty();
    }
}
