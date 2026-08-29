package com.synechisveltiosi.apis.app365.candidates.repository;

import com.synechisveltiosi.apis.app365.candidates.entity.CandidateSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateSummaryRepository extends JpaRepository<CandidateSummary, Long> {

    Optional<CandidateSummary> findByCandidateId_Id(Long candidateId);
}
