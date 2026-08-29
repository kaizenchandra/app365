package com.synechisveltiosi.apis.app365.candidates.repository;

import com.synechisveltiosi.apis.app365.candidates.entity.CandidateShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateShareRepository extends JpaRepository<CandidateShare, Long> {

    long countAllByUserId_Id(Long userId);

    void deleteByCandidateId_Id(Long candidateId);
}
