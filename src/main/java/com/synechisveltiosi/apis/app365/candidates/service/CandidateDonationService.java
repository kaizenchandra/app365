package com.synechisveltiosi.apis.app365.candidates.service;

import com.synechisveltiosi.apis.app365.candidates.entity.CandidateDonation;

import java.util.List;
import java.util.Optional;

public interface CandidateDonationService {

    Optional<CandidateDonation> findById(Long id);

    Optional<CandidateDonation> findByUserIdAndCandidateId(Long userId, Long candidateId);

    List<CandidateDonation> findByUserId(Long userId);

    CandidateDonation save(CandidateDonation candidateDonation);

    void deleteByUserIdAndCandidateId(Long userId, Long candidateId);
}
