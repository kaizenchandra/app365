package com.synechisveltiosi.apis.app365.candidates.service;

import com.synechisveltiosi.apis.app365.candidates.entity.Post;
import com.synechisveltiosi.apis.app365.candidates.entity.PostComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostService {

    Page<Post> findAll(String candidateId, Pageable pageable);

    Optional<Post> findById(Long id);

    Optional<Post> findById(String id);

    Post save(Post post);

    Page<PostComment> findAllComments(String postId, Pageable pageable);

    PostComment saveComment(Long userId, String postId, PostComment comment);
}
