package com.synechisveltiosi.apis.app365.candidates.service;

import com.synechisveltiosi.apis.app365.candidates.entity.Candidate;
import com.synechisveltiosi.apis.app365.candidates.entity.CandidateSummary;

import java.util.Optional;

public interface CandidateSummaryService {

    Optional<CandidateSummary> findById(Long id);

    CandidateSummary save(CandidateSummary candidateSummary);

    void incrementDonation(Candidate candidate);

    void decreaseDonation(Candidate candidate);

    void incrementOffer(Candidate candidate);

    void decreaseOffer(Candidate candidate);

    void incrementLike(Candidate candidate);

    void decreaseLike(Candidate candidate);

    void incrementShare(Candidate candidate);

    void incrementComment(Candidate candidate);

    void decreaseComment(Candidate candidate);

    void incrementPost(Candidate candidate);

    void decreasePost(Candidate candidate);

    void incrementPostComment(Candidate candidate);

    void decreasePostComment(Candidate candidate);
}
