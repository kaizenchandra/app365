package com.synechisveltiosi.apis.app365.campaign.mapper;

import com.synechisveltiosi.apis.app365.campaign.dto.TeamMemberResponse;
import com.synechisveltiosi.apis.app365.campaign.entity.ExtendedTeamMember;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
@DecoratedWith(ExtendedTeamMemberMapperDecorator.class)
public interface ExtendedTeamMemberMapper {

    ExtendedTeamMemberMapper INSTANCE = Mappers.getMapper(ExtendedTeamMemberMapper.class);

    @Mapping(source = "memberId", target = "id")
    TeamMemberResponse from(ExtendedTeamMember teamMember);
}
