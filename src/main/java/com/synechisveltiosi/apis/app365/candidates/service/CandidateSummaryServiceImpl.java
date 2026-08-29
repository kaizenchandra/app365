package com.synechisveltiosi.apis.app365.candidates.service;

import com.synechisveltiosi.apis.app365.candidates.entity.Candidate;
import com.synechisveltiosi.apis.app365.candidates.entity.CandidateSummary;
import com.synechisveltiosi.apis.app365.candidates.repository.CandidateSummaryRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotModifiedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CandidateSummaryServiceImpl implements CandidateSummaryService {

    private final CandidateSummaryRepository candidateSummaryRepository;

    @Autowired
    public CandidateSummaryServiceImpl(CandidateSummaryRepository candidateSummaryRepository) {
        this.candidateSummaryRepository = candidateSummaryRepository;
    }

    @Override
    public Optional<CandidateSummary> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Candidate summary id should not be null or 0");

        return candidateSummaryRepository.findById(id);
    }

    @Transactional
    @Override
    public CandidateSummary save(CandidateSummary candidateSummary) {
        return candidateSummaryRepository.save(candidateSummary);
    }

    @Transactional
    @Override
    public void incrementDonation(Candidate candidate) {
        // Find the summary for this candidate
        Optional<CandidateSummary> candidateSummaryOptional = getCandidateSummary(candidate);

        // Increase the donation field and persist result
        candidateSummaryOptional.ifPresent(candidateSummary -> {
            candidateSummary.increaseDonationCount();
            candidateSummary.setLastDonationAt(LocalDateTime.now());

            // Save the summary
            save(candidateSummary);
        });
    }

    @Transactional
    @Override
    public void decreaseDonation(Candidate candidate) {
        // Find the summary for this candidate
        Optional<CandidateSummary> candidateSummaryOptional =
                candidateSummaryRepository.findByCandidateId_Id(candidate.getId());

        // If no summary yet, nothing will change
        if (!candidateSummaryOptional.isPresent()) throw new NotModifiedException();

        // Decrease the donation field and persist result
        candidateSummaryOptional.ifPresent(candidateSummary -> {
            candidateSummary.decreaseDonationCount();

            // Save the summary
            save(candidateSummary);
        });
    }

    @Transactional
    @Override
    public void incrementOffer(Candidate candidate) {
        // Find the summary for this candidate
        Optional<CandidateSummary> candidateSummaryOptional = getCandidateSummary(candidate);

        // Increase the offer field and persist result
        candidateSummaryOptional.ifPresent(candidateSummary -> {
            candidateSummary.increaseOfferCount();
            candidateSummary.setLastOfferAt(LocalDateTime.now());

            // Save the summary
            save(candidateSummary);
        });
    }

    @Transactional
    @Override
    public void decreaseOffer(Candidate candidate) {
        // Find the summary for this candidate
        Optional<CandidateSummary> candidateSummaryOptional =
                candidateSummaryRepository.findByCandidateId_Id(candidate.getId());

        // If no summary yet, nothing will change
        if (!candidateSummaryOptional.isPresent()) throw new NotModifiedException();

        // Decrease the offer field and persist result
        candidateSummaryOptional.ifPresent(candidateSummary -> {
            candidateSummary.decreaseOfferCount();

            // Save the summary
            save(candidateSummary);
        });
    }

    @Transactional
    @Override
    public void incrementLike(Candidate candidate) {
        // Find the summary for this candidate
        Optional<CandidateSummary> candidateSummaryOptional = getCandidateSummary(candidate);

        // Increase the like field and persist result
        candidateSummaryOptional.ifPresent(candidateSummary -> {
            candidateSummary.increaseLikeCount();
            candidateSummary.setLastLikeAt(LocalDateTime.now());

            // Save the summary
            save(candidateSummary);
        });
    }

    @Transactional
    @Override
    public void decreaseLike(Candidate candidate) {
        // Find the summary for this candidate
        Optional<CandidateSummary> candidateSummaryOptional =
                candidateSummaryRepository.findByCandidateId_Id(candidate.getId());

        // If no summary yet, nothing will change
        if (!candidateSummaryOptional.isPresent()) throw new NotModifiedException();

        // Decrease the like field and persist result
        candidateSummaryOptional.ifPresent(candidateSummary -> {
            candidateSummary.decreaseLikeCount();

            // Save the summary
            save(candidateSummary);
        });
    }

    @Transactional
    @Override
    public void incrementShare(Candidate candidate) {
        // Find the summary for this candidate
        Optional<CandidateSummary> candidateSummaryOptional = getCandidateSummary(candidate);

        // Increase the share field and persist result
        candidateSummaryOptional.ifPresent(candidateSummary -> {
            candidateSummary.increaseShareCount();
            candidateSummary.setLastShareAt(LocalDateTime.now());

            // Save the summary
            save(candidateSummary);
        });
    }

    @Transactional
    @Override
    public void incrementComment(Candidate candidate) {
        Optional<CandidateSummary> candidateSummaryOptional = getCandidateSummary(candidate);

        // Increase the comment field and persist result
        candidateSummaryOptional.ifPresent(candidateSummary -> {
            candidateSummary.increaseCommentCount();
            candidateSummary.setLastCommentAt(LocalDateTime.now());

            // Save the summary
            save(candidateSummary);
        });
    }

    @Transactional
    @Override
    public void decreaseComment(Candidate candidate) {
        // Find the summary for this candidate
        Optional<CandidateSummary> candidateSummaryOptional =
                candidateSummaryRepository.findByCandidateId_Id(candidate.getId());

        // If no summary yet, nothing will change
        if (!candidateSummaryOptional.isPresent()) throw new NotModifiedException();

        // Decrease the comment field and persist result
        candidateSummaryOptional.ifPresent(candidateSummary -> {
            candidateSummary.decreaseCommentCount();

            // Save the summary
            save(candidateSummary);
        });
    }

    @Transactional
    @Override
    public void incrementPost(Candidate candidate) {
        Optional<CandidateSummary> candidateSummaryOptional = getCandidateSummary(candidate);

        // Increase the post field and persist result
        candidateSummaryOptional.ifPresent(candidateSummary -> {
            candidateSummary.increasePostCount();
            candidateSummary.setLastPostAt(LocalDateTime.now());

            // Save the summary
            save(candidateSummary);
        });
    }

    @Transactional
    @Override
    public void decreasePost(Candidate candidate) {
        // Find the summary for this candidate
        Optional<CandidateSummary> candidateSummaryOptional =
                candidateSummaryRepository.findByCandidateId_Id(candidate.getId());

        // If no summary yet, nothing will change
        if (!candidateSummaryOptional.isPresent()) throw new NotModifiedException();

        // Decrease the post field and persist result
        candidateSummaryOptional.ifPresent(candidateSummary -> {
            candidateSummary.decreasePostCount();

            // Save the summary
            save(candidateSummary);
        });
    }

    @Transactional
    @Override
    public void incrementPostComment(Candidate candidate) {
        Optional<CandidateSummary> candidateSummaryOptional = getCandidateSummary(candidate);

        // Increase the post comment field and persist result
        candidateSummaryOptional.ifPresent(candidateSummary -> {
            candidateSummary.increasePostCommentCount();
            candidateSummary.setLastPostCommentAt(LocalDateTime.now());

            // Save the summary
            save(candidateSummary);
        });
    }

    @Transactional
    @Override
    public void decreasePostComment(Candidate candidate) {
        // Find the summary for this candidate
        Optional<CandidateSummary> candidateSummaryOptional =
                candidateSummaryRepository.findByCandidateId_Id(candidate.getId());

        // If no summary yet, nothing will change
        if (!candidateSummaryOptional.isPresent()) throw new NotModifiedException();

        // Decrease the post comment field and persist result
        candidateSummaryOptional.ifPresent(candidateSummary -> {
            candidateSummary.decreasePostCommentCount();

            // Save the summary
            save(candidateSummary);
        });
    }

    /**
     * Get or create the first candidate summary
     *
     * @param candidate
     * @return
     */
    private Optional<CandidateSummary> getCandidateSummary(Candidate candidate) {
        // Find the summary for this candidate
        Optional<CandidateSummary> candidateSummaryOptional =
                candidateSummaryRepository.findByCandidateId_Id(candidate.getId());

        // If no summary yet, create one
        if (!candidateSummaryOptional.isPresent()) {
            CandidateSummary candidateSummary = new CandidateSummary();
            candidateSummary.setCandidateId(candidate);

            // Save this candidate summary
            candidateSummaryOptional = Optional.of(this.save(candidateSummary));
        }

        return candidateSummaryOptional;
    }
}
