package com.anton.rate.repository;

import com.anton.rate.entity.Fiat;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface FiatCurrencyRepository extends R2dbcRepository<Fiat, Long> {
    @Query("SELECT * FROM fiat ORDER BY created_at DESC LIMIT 3")
    Flux<Fiat> findTop3ByOrderByCreatedAtDesc();

}
