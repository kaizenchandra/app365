package com.synechisveltiosi.apis.app365.candidates.service;

import com.synechisveltiosi.apis.app365.candidates.entity.CandidateShare;

import java.util.Optional;

public interface CandidateShareService {

    Optional<CandidateShare> findById(Long id);

    long countByUserId(Long userId);

    CandidateShare save(CandidateShare candidateShare);
}
