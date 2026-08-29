package com.synechisveltiosi.apis.app365.candidates.service;

import com.synechisveltiosi.apis.app365.candidates.entity.CandidateLike;

import java.util.Optional;

public interface CandidateLikeService {

    Optional<CandidateLike> findById(Long id);

    Optional<CandidateLike> findByUserIdAndCandidateId(Long userId, Long candidateId);

    long countByUserId(Long userId);

    CandidateLike save(CandidateLike candidateLike);

    void deleteByUserIdAndCandidateId(Long userId, Long candidateId);
}
