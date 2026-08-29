package com.synechisveltiosi.apis.app365.candidates.service;

import com.synechisveltiosi.apis.app365.candidates.entity.CandidateLike;
import com.synechisveltiosi.apis.app365.candidates.repository.CandidateLikeRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CandidateLikeServiceImpl implements CandidateLikeService {

    private final CandidateLikeRepository candidateLikeRepository;

    @Autowired
    public CandidateLikeServiceImpl(CandidateLikeRepository candidateLikeRepository) {
        this.candidateLikeRepository = candidateLikeRepository;
    }


    @Override
    public Optional<CandidateLike> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Candidate like id should not be null or 0");

        return candidateLikeRepository.findById(id);
    }

    @Override
    public Optional<CandidateLike> findByUserIdAndCandidateId(Long userId, Long candidateId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (candidateId == null || candidateId == 0)
            throw new BadRequestException("Candidate id should not be null or 0");

        return candidateLikeRepository.findByUserId_IdAndCandidateId_Id(userId, candidateId);
    }

    @Override
    public long countByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        return candidateLikeRepository.countAllByUserId_IdAndLikedIsTrue(userId);
    }

    @Transactional
    @Override
    public CandidateLike save(CandidateLike candidateLike) {
        return candidateLikeRepository.save(candidateLike);
    }

    @Transactional
    @Override
    public void deleteByUserIdAndCandidateId(Long userId, Long candidateId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (candidateId == null || candidateId == 0)
            throw new BadRequestException("Candidate id should not be null or 0");

        candidateLikeRepository.deleteByUserId_IdAndCandidateId_Id(userId, candidateId);
    }
}
