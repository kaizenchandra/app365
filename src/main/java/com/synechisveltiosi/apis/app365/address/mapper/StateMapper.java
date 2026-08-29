package com.synechisveltiosi.apis.app365.address.mapper;

import com.synechisveltiosi.apis.app365.address.entity.State;
import com.synechisveltiosi.apis.app365.common.dto.places.StateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StateMapper {

    StateMapper INSTANCE = Mappers.getMapper(StateMapper.class);

    @Mapping(source = "stateId", target = "id")
    StateResponse from(State state);
}
