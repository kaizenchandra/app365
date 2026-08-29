package com.synechisveltiosi.apis.app365.candidates.repository;

import com.synechisveltiosi.apis.app365.candidates.entity.CandidateOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateOfferRepository extends JpaRepository<CandidateOffer, Long> {

    Optional<CandidateOffer> findByUserId_IdAndCandidateId_Id(Long userId, Long candidateId);

    void deleteByUserId_IdAndCandidateId_Id(Long userId, Long candidateId);
}
