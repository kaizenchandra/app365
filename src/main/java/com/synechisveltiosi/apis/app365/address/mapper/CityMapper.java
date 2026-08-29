package com.synechisveltiosi.apis.app365.address.mapper;

import com.synechisveltiosi.apis.app365.address.entity.City;
import com.synechisveltiosi.apis.app365.common.dto.places.CityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CityMapper {

    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    @Mapping(source = "cityId", target = "id")
    CityResponse from(City city);
}
