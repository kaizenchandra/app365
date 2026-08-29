package com.synechisveltiosi.apis.app365.candidates.service;

import com.synechisveltiosi.apis.app365.candidates.entity.CandidateDonation;
import com.synechisveltiosi.apis.app365.candidates.repository.CandidateDonationRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateDonationServiceImpl implements CandidateDonationService {

    private final CandidateDonationRepository candidateDonationRepository;

    @Autowired
    public CandidateDonationServiceImpl(CandidateDonationRepository candidateDonationRepository) {
        this.candidateDonationRepository = candidateDonationRepository;
    }

    @Override
    public Optional<CandidateDonation> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Candidate donation id should not be null or 0");

        return candidateDonationRepository.findById(id);
    }

    @Override
    public Optional<CandidateDonation> findByUserIdAndCandidateId(Long userId, Long candidateId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (candidateId == null || candidateId == 0)
            throw new BadRequestException("Candidate id should not be null or 0");

        return candidateDonationRepository.findByUserId_IdAndCandidateId_Id(userId, candidateId);
    }

    @Override
    public List<CandidateDonation> findByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        return candidateDonationRepository.findByUserId_Id(userId);
    }

    @Transactional
    @Override
    public CandidateDonation save(CandidateDonation candidateDonation) {
        return candidateDonationRepository.save(candidateDonation);
    }

    @Transactional
    @Override
    public void deleteByUserIdAndCandidateId(Long userId, Long candidateId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (candidateId == null || candidateId == 0)
            throw new BadRequestException("Candidate id should not be null or 0");

        candidateDonationRepository.deleteByUserId_IdAndCandidateId_Id(userId, candidateId);
    }
}
