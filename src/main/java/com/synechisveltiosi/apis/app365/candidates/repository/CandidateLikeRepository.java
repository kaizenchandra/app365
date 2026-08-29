package com.synechisveltiosi.apis.app365.candidates.repository;

import com.synechisveltiosi.apis.app365.candidates.entity.CandidateLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateLikeRepository extends JpaRepository<CandidateLike, Long> {

    Optional<CandidateLike> findByUserId_IdAndCandidateId_Id(Long userId, Long candidateId);

    long countAllByUserId_IdAndLikedIsTrue(Long userId);

    void deleteByUserId_IdAndCandidateId_Id(Long userId, Long candidateId);
}
