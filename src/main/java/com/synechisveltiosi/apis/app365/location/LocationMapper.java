package com.synechisveltiosi.apis.app365.location;

import com.synechisveltiosi.apis.app365.common.dto.places.LocationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = GeoResponseMapper.class)
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    @Mapping(source = "location", target = "geo")
    LocationResponse from(Location location);
}
