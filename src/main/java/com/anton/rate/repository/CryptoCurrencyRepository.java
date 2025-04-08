package com.anton.rate.repository;

import com.anton.rate.entity.Crypto;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoCurrencyRepository extends R2dbcRepository<Crypto, Long> {

}
