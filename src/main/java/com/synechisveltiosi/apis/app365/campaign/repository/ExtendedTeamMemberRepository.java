package com.synechisveltiosi.apis.app365.campaign.repository;

import com.synechisveltiosi.apis.app365.campaign.entity.ExtendedTeamMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExtendedTeamMemberRepository extends JpaRepository<ExtendedTeamMember, Long> {

    Optional<ExtendedTeamMember> findByUserId_IdAndCitizenId_IdCard(Long userId, String idCard);

    Page<ExtendedTeamMember> findAllByUserId_IdAndCitizenId_IdCardNot(Long userId, String idCard, Pageable pageable);
}
