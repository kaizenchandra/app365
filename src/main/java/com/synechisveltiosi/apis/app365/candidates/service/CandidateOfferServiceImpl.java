package com.synechisveltiosi.apis.app365.candidates.service;

import com.synechisveltiosi.apis.app365.candidates.entity.CandidateOffer;
import com.synechisveltiosi.apis.app365.candidates.repository.CandidateOfferRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CandidateOfferServiceImpl implements CandidateOfferService {

    private final CandidateOfferRepository candidateOfferRepository;

    @Autowired
    public CandidateOfferServiceImpl(CandidateOfferRepository candidateOfferRepository) {
        this.candidateOfferRepository = candidateOfferRepository;
    }

    @Override
    public Optional<CandidateOffer> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Candidate offer id should not be null or 0");

        return candidateOfferRepository.findById(id);
    }

    @Override
    public Optional<CandidateOffer> findByUserIdAndCandidateId(Long userId, Long candidateId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (candidateId == null || candidateId == 0)
            throw new BadRequestException("Candidate id should not be null or 0");

        return candidateOfferRepository.findByUserId_IdAndCandidateId_Id(userId, candidateId);
    }

    @Transactional
    @Override
    public CandidateOffer save(CandidateOffer candidateOffer) {
        return candidateOfferRepository.save(candidateOffer);
    }

    @Transactional
    @Override
    public void deleteByUserIdAndCandidateId(Long userId, Long candidateId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (candidateId == null || candidateId == 0)
            throw new BadRequestException("Candidate id should not be null or 0");

        candidateOfferRepository.deleteByUserId_IdAndCandidateId_Id(userId, candidateId);
    }
}
