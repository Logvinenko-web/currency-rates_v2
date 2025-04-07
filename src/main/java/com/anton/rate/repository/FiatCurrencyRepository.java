package com.anton.rate.repository;

import com.anton.rate.entity.Crypto;
import com.anton.rate.entity.Fiat;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface FiatCurrencyRepository extends R2dbcRepository<Fiat, Long> {

    @Query("SELECT * FROM last_currency WHERE source = :source")
    Flux<Fiat> findLastCurrency(@Param("source") String source);

}
