package com.anton.rate.repository;

import com.anton.rate.entity.Fiat;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FiatCurrencyRepository extends R2dbcRepository<Fiat, Long> {

}
