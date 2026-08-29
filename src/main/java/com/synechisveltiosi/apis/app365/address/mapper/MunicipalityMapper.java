package com.synechisveltiosi.apis.app365.address.mapper;

import com.synechisveltiosi.apis.app365.address.entity.Municipality;
import com.synechisveltiosi.apis.app365.common.dto.places.MunicipalityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MunicipalityMapper {

    MunicipalityMapper INSTANCE = Mappers.getMapper(MunicipalityMapper.class);

    @Mapping(source = "municipalityId", target = "id")
    MunicipalityResponse from(Municipality municipality);
}
