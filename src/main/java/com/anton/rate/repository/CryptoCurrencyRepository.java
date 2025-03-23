package com.anton.rate.repository;

import com.anton.rate.entity.Crypto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CryptoCurrencyRepository extends R2dbcRepository<Crypto, Long> {
    @Query("SELECT * FROM crypto ORDER BY created_at DESC LIMIT 3")
    Flux<Crypto> findTop3ByOrderByCreatedAtDesc();
}
