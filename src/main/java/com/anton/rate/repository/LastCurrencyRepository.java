package com.anton.rate.repository;

import com.anton.rate.entity.LastCurrency;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface LastCurrencyRepository extends R2dbcRepository<LastCurrency, Long> {

    Mono<LastCurrency> findByCurrency(String currency);

}
