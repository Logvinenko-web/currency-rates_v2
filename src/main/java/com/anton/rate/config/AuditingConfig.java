package com.anton.rate.config;

import org.springframework.data.domain.ReactiveAuditorAware;
import reactor.core.publisher.Mono;



public class AuditingConfig implements ReactiveAuditorAware<String> {


    @Override
    public Mono<String> getCurrentAuditor () {
        return Mono.empty();
    }
}
