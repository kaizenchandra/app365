package com.synechisveltiosi.apis.app365.candidates.repository;

import com.synechisveltiosi.apis.app365.candidates.entity.CandidateComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateCommentRepository extends JpaRepository<CandidateComment, Long> {

    Page<CandidateComment> findAllByCandidateId_CandidateId(String candidateId, Pageable pageable);

    long countAllByUserId_IdAndDeletedAtIsNull(Long userId);
}
