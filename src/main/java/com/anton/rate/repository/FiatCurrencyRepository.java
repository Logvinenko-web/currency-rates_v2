package com.anton.rate.repository;

import com.anton.rate.entity.Fiat;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface FiatCurrencyRepository extends R2dbcRepository<Fiat, Long> {
    @Query("SELECT f.currency, f.rate FROM fiat f JOIN ( SELECT currency, MAX(created_at) AS last_created_at FROM fiat GROUP BY currency ) AS l ON f.currency = l.currency AND f.created_at = l.last_created_at;")
    Flux<Fiat> findLastCurrency();

}
