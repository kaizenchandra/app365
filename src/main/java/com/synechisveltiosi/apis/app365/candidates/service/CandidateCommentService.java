package com.synechisveltiosi.apis.app365.candidates.service;

import com.synechisveltiosi.apis.app365.candidates.entity.CandidateComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CandidateCommentService {

    Page<CandidateComment> findAll(String candidateId, Pageable pageable);

    Optional<CandidateComment> findById(Long id);

    long countByUserId(Long userId);

    CandidateComment save(CandidateComment candidateComment);
}
