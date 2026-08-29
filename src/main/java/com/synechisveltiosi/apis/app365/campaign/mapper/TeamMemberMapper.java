package com.synechisveltiosi.apis.app365.campaign.mapper;

import com.synechisveltiosi.apis.app365.campaign.dto.TeamMemberResponse;
import com.synechisveltiosi.apis.app365.campaign.entity.TeamMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeamMemberMapper {

    TeamMemberMapper INSTANCE = Mappers.getMapper(TeamMemberMapper.class);

    @Mapping(source = "memberId", target = "id")
    TeamMemberResponse from(TeamMember teamMember);
}
