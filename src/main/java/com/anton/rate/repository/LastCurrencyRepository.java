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

    @Modifying
    @Query("""
    INSERT INTO last_currency (currency, rate, source) 
    VALUES (:currency, :rate, :source)
    ON CONFLICT (currency) 
    DO UPDATE SET rate = EXCLUDED.rate, updated_at = NOW();
    """)
    Mono<Void> saveOrUpdateLatestRate(@Param("currency") String currency,
        @Param("rate") Double rate,
        @Param("source") String source);

}
