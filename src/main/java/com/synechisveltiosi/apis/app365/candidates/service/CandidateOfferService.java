package com.synechisveltiosi.apis.app365.candidates.service;

import com.synechisveltiosi.apis.app365.candidates.entity.CandidateOffer;

import java.util.Optional;

public interface CandidateOfferService {

    Optional<CandidateOffer> findById(Long id);

    Optional<CandidateOffer> findByUserIdAndCandidateId(Long userId, Long candidateId);

    CandidateOffer save(CandidateOffer candidateOffer);

    void deleteByUserIdAndCandidateId(Long userId, Long candidateId);
}
