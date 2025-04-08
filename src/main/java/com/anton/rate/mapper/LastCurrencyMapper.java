package com.anton.rate.mapper;

import com.anton.rate.entity.Crypto;
import com.anton.rate.entity.Fiat;
import com.anton.rate.entity.LastCurrency;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LastCurrencyMapper {

    LastCurrencyMapper INSTANCE = Mappers.getMapper(LastCurrencyMapper.class);

    Fiat toFiatEntity(LastCurrency lastCurrency);

    Crypto toCryptoEntity(LastCurrency lastCurrency);

}
