package com.synechisveltiosi.apis.app365.campaign.repository;

import com.synechisveltiosi.apis.app365.campaign.entity.TeamLevel;

import java.util.List;

public interface TeamMemberBaseRepository {

    List<TeamLevel> getTeamLevelMembers(Long parentId);
}
