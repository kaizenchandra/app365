package com.synechisveltiosi.apis.app365.election.mapper;

import com.synechisveltiosi.apis.app365.election.Election;
import com.synechisveltiosi.apis.app365.election.dto.ElectionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ElectionMapper {

    ElectionMapper INSTANCE = Mappers.getMapper(ElectionMapper.class);

    @Mapping(source = "electionId", target = "id")
    ElectionResponse from(Election election);
}
