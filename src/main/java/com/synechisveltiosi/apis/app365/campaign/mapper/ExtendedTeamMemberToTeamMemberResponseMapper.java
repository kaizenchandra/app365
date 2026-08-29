package com.synechisveltiosi.apis.app365.campaign.mapper;

import com.synechisveltiosi.apis.app365.campaign.dto.TeamMemberResponse;
import com.synechisveltiosi.apis.app365.campaign.entity.ExtendedTeamMember;
import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class ExtendedTeamMemberToTeamMemberResponseMapper extends AbstractMapper<ExtendedTeamMember, TeamMemberResponse> {

    @Override
    public TeamMemberResponse map(ExtendedTeamMember extendedTeamMember) {
        return ExtendedTeamMemberMapper.INSTANCE.from(extendedTeamMember);
    }
}
