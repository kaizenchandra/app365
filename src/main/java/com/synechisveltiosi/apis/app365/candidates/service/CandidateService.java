package com.synechisveltiosi.apis.app365.candidates.service;

import com.synechisveltiosi.apis.app365.candidates.entity.Candidate;
import com.synechisveltiosi.apis.app365.candidates.entity.CandidateComment;
import com.synechisveltiosi.apis.app365.candidates.entity.CandidateDonation;
import com.synechisveltiosi.apis.app365.candidates.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CandidateService {

    Optional<Candidate> findById(Long id);

    Optional<Candidate> findById(String id);

    Optional<Candidate> findFirstCandidate();

    Candidate save(Candidate candidate);

    void donate(String candidateId, Long userId, CandidateDonation donation);

    void removeDonation(String candidateId, Long userId);

    void offer(String candidateId, Long userId);

    void removeOffer(String candidateId, Long userId);

    void like(String candidateId, Long userId);

    void unlike(String candidateId, Long userId);

    void share(String candidateId, Long userId);

    Page<CandidateComment> findAllComments(String candidateId, Pageable pageable);

    CandidateComment saveComment(Long userId, String candidateId, CandidateComment comment);

    Page<Post> findAllPosts(String candidateId, Pageable pageable);

    Post savePost(String candidateId, Post post);
}
