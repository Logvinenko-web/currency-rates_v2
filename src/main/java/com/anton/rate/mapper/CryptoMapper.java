package com.anton.rate.mapper;

import com.anton.rate.model.CryptoResponse;
import com.anton.rate.entity.Crypto;
import com.anton.rate.model.CryptoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CryptoMapper {

    CryptoMapper INSTANCE = Mappers.getMapper(CryptoMapper.class);

    @Mapping(source = "name", target = "currency")
    @Mapping(source = "value", target = "rate")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Crypto toCryptoEntity(CryptoResponse cryptoResponse);


    CryptoDto toCryptoDto(Crypto crypto);
}
