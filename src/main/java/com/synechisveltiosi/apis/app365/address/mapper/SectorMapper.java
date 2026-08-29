package com.synechisveltiosi.apis.app365.address.mapper;

import com.synechisveltiosi.apis.app365.address.entity.Sector;
import com.synechisveltiosi.apis.app365.common.dto.places.SectorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SectorMapper {

    SectorMapper INSTANCE = Mappers.getMapper(SectorMapper.class);

    @Mapping(source = "sectorId", target = "id")
    SectorResponse from(Sector sector);
}
