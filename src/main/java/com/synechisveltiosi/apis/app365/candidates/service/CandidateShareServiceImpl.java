package com.synechisveltiosi.apis.app365.candidates.service;

import com.synechisveltiosi.apis.app365.candidates.entity.CandidateShare;
import com.synechisveltiosi.apis.app365.candidates.repository.CandidateShareRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CandidateShareServiceImpl implements CandidateShareService {

    private final CandidateShareRepository candidateShareRepository;

    @Autowired
    public CandidateShareServiceImpl(CandidateShareRepository candidateShareRepository) {
        this.candidateShareRepository = candidateShareRepository;
    }

    @Override
    public Optional<CandidateShare> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Candidate share id should not be null or 0");

        return candidateShareRepository.findById(id);
    }

    @Override
    public long countByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        return candidateShareRepository.countAllByUserId_Id(userId);
    }

    @Transactional
    @Override
    public CandidateShare save(CandidateShare candidateShare) {
        return candidateShareRepository.save(candidateShare);
    }
}
