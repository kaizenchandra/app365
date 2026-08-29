package com.synechisveltiosi.apis.app365.campaign.service;

import com.synechisveltiosi.apis.app365.campaign.entity.ExtendedTeamMember;
import com.synechisveltiosi.apis.app365.campaign.entity.TeamLevel;
import com.synechisveltiosi.apis.app365.campaign.entity.TeamMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TeamMemberService {

    Optional<TeamMember> findById(Long id);

    Optional<TeamMember> findById(String id);

    Optional<TeamMember> findByIdCard(String idCard);

    Optional<ExtendedTeamMember> findTeamMemberByUserIdAndIdCard(Long userId, String idCard);

    Page<TeamMember> findAll(Long userId, String idCard, Pageable pageable);

    Page<ExtendedTeamMember> findAllTeamMembers(Long userId, String idCard, Pageable pageable);

    List<TeamLevel> findLevelMembers(String idCard);

    TeamMember save(Long userId, String idCard);

    TeamMember patchAddress(String memberId, Map<String, Object> addressPatch);

    void deleteById(Long id);

    void deleteByIdCard(String idCard);
}
