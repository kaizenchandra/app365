package com.synechisveltiosi.apis.app365.campaign.mapper;

import com.synechisveltiosi.apis.app365.address.mapper.AddressMapper;
import com.synechisveltiosi.apis.app365.campaign.dto.TeamMemberResponse;
import com.synechisveltiosi.apis.app365.campaign.dto.TeamMemberSummaryResponse;
import com.synechisveltiosi.apis.app365.campaign.entity.ExtendedTeamMember;
import com.synechisveltiosi.apis.app365.citizens.entity.Citizen;
import com.synechisveltiosi.apis.app365.citizens.entity.CitizenAddress;

public abstract class ExtendedTeamMemberMapperDecorator implements ExtendedTeamMemberMapper {

    private final ExtendedTeamMemberMapper mapper;

    public ExtendedTeamMemberMapperDecorator(ExtendedTeamMemberMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public TeamMemberResponse from(ExtendedTeamMember teamMember) {
        if (teamMember == null) return null;

        TeamMemberResponse teamMemberResponse = mapper.from(teamMember);

        // Map the citizen
        Citizen citizen = teamMember.getCitizenId();
        if (citizen != null) {
            teamMemberResponse.setIdCard(citizen.getIdCard());
            teamMemberResponse.setFirstName(citizen.getFirstName());
            teamMemberResponse.setLastName(citizen.getLastName());

            CitizenAddress address = citizen.getAddress();
            if (address != null) {
                teamMemberResponse.setAddress(AddressMapper.INSTANCE.from(address));
            }

            // Create the team summary if not exist
            if (teamMemberResponse.getTeam() == null) teamMemberResponse.setTeam(new TeamMemberSummaryResponse());

            // Fill the team
            teamMemberResponse.getTeam()
                    .withLevelCount(teamMember.getLevelCount())
                    .withMemberCount(teamMember.getMemberCount());
        }

        return teamMemberResponse;
    }
}
