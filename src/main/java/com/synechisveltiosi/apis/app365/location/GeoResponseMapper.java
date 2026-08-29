package com.synechisveltiosi.apis.app365.location;

import com.synechisveltiosi.apis.app365.common.dto.places.GeoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GeoResponseMapper {

    GeoResponseMapper INSTANCE = Mappers.getMapper(GeoResponseMapper.class);

    @Mappings({
            @Mapping(source = "latitude", target = "lat"),
            @Mapping(source = "longitude", target = "lon")
    })
    GeoResponse toGeo(Location location);
}
