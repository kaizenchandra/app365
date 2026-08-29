package com.synechisveltiosi.apis.app365.electoral.college.mapper;

import com.synechisveltiosi.apis.app365.electoral.college.VoteCenter;
import com.synechisveltiosi.apis.app365.electoral.college.dto.VoteCenterResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
@DecoratedWith(VoteCenterMapperDecorator.class)
public interface VoteCenterMapper {

    VoteCenterMapper INSTANCE = Mappers.getMapper(VoteCenterMapper.class);

    @Mapping(source = "voteCenterId", target = "id")
    VoteCenterResponse from(VoteCenter voteCenter);
}
