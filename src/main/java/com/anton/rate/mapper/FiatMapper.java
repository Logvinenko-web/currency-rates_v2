package com.anton.rate.mapper;

import com.anton.rate.entity.Fiat;
import com.anton.rate.model.FiatDto;
import com.anton.rate.model.FiatResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FiatMapper {

    FiatMapper INSTANCE = Mappers.getMapper(FiatMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Fiat toFiatEntity(FiatResponse fiatResponse);

    FiatDto toFiatDto(Fiat fiat);
}
