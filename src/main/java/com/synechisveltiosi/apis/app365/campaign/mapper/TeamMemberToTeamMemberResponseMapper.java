package com.synechisveltiosi.apis.app365.campaign.mapper;

import com.synechisveltiosi.apis.app365.campaign.dto.TeamMemberResponse;
import com.synechisveltiosi.apis.app365.campaign.entity.TeamMember;
import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class TeamMemberToTeamMemberResponseMapper extends AbstractMapper<TeamMember, TeamMemberResponse> {

    @Override
    public TeamMemberResponse map(TeamMember teamMember) {
        return TeamMemberMapper.INSTANCE.from(teamMember);
    }
}
