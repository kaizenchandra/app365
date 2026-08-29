package com.synechisveltiosi.apis.app365.citizens.mapper;

import com.synechisveltiosi.apis.app365.citizens.dto.CitizenResponse;
import com.synechisveltiosi.apis.app365.citizens.entity.Citizen;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CitizenMapper {

    CitizenMapper INSTANCE = Mappers.getMapper(CitizenMapper.class);

    CitizenResponse from(Citizen citizen);
}
