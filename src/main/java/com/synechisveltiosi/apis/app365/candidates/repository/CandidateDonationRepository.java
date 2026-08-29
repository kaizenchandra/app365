package com.synechisveltiosi.apis.app365.candidates.repository;

import com.synechisveltiosi.apis.app365.candidates.entity.CandidateDonation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateDonationRepository extends JpaRepository<CandidateDonation, Long> {

    Optional<CandidateDonation> findByUserId_IdAndCandidateId_Id(Long userId, Long candidateId);

    List<CandidateDonation> findByUserId_Id(Long userId);

    void deleteByUserId_IdAndCandidateId_Id(Long userId, Long candidateId);
}
