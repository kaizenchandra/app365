package com.synechisveltiosi.apis.app365.candidates.service;

import com.synechisveltiosi.apis.app365.candidates.entity.CandidateComment;
import com.synechisveltiosi.apis.app365.candidates.repository.CandidateCommentRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CandidateCommentServiceImpl implements CandidateCommentService {

    private final CandidateCommentRepository candidateCommentRepository;

    @Autowired
    public CandidateCommentServiceImpl(CandidateCommentRepository candidateCommentRepository) {
        this.candidateCommentRepository = candidateCommentRepository;
    }

    @Override
    public Page<CandidateComment> findAll(String candidateId, Pageable pageable) {
        if (StringUtils.isBlank(candidateId)) throw new BadRequestException("Candidate id should not be null or blank");

        pageable = pageable == null ? Pageable.unpaged() : pageable;

        return candidateCommentRepository.findAllByCandidateId_CandidateId(candidateId, pageable);
    }

    @Override
    public Optional<CandidateComment> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Comment id should not be null or 0");

        return candidateCommentRepository.findById(id);
    }

    @Override
    public long countByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        return candidateCommentRepository.countAllByUserId_IdAndDeletedAtIsNull(userId);
    }

    @Transactional
    @Override
    public CandidateComment save(CandidateComment candidateComment) {
        return candidateCommentRepository.save(candidateComment);
    }
}
