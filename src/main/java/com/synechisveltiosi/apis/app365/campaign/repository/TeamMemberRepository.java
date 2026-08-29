package com.synechisveltiosi.apis.app365.campaign.repository;

import com.synechisveltiosi.apis.app365.campaign.entity.TeamMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long>, TeamMemberBaseRepository {

    Page<TeamMember> findAllByUserId_IdAndCitizenId_IdCardNot(Long userId, String idCard, Pageable pageable);

    Optional<TeamMember> findByCitizenId_IdCard(String idCard);

    Optional<TeamMember> findByMemberId(String memberId);

    void deleteByCitizenId_IdCard(String idCard);
}
