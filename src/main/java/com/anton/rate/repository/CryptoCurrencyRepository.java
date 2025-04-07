package com.anton.rate.repository;

import com.anton.rate.entity.Crypto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CryptoCurrencyRepository extends R2dbcRepository<Crypto, Long> {

    @Query("SELECT * FROM last_currency WHERE source = :source")
    Flux<Crypto> findLastCurrency(@Param("source") String source);

}
