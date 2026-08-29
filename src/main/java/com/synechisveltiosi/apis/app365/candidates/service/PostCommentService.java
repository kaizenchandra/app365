package com.synechisveltiosi.apis.app365.candidates.service;

import com.synechisveltiosi.apis.app365.candidates.entity.PostComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostCommentService {

    Page<PostComment> findAll(String postId, Pageable pageable);

    Optional<PostComment> findById(Long id);

    long countByUserId(Long userId);

    PostComment save(PostComment postComment);
}
