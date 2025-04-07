package com.anton.rate.repository;

import com.anton.rate.entity.Crypto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CryptoCurrencyRepository extends R2dbcRepository<Crypto, Long> {
    @Query("SELECT f.currency, f.rate FROM crypto f JOIN ( SELECT currency, MAX(created_at) AS last_created_at FROM crypto GROUP BY currency ) AS l ON f.currency = l.currency AND f.created_at = l.last_created_at;")
    Flux<Crypto> findLastCurrency();
}
