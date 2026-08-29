package com.synechisveltiosi.apis.app365.candidates.service;

import com.synechisveltiosi.apis.app365.candidates.entity.PostComment;
import com.synechisveltiosi.apis.app365.candidates.repository.PostCommentRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PostCommentServiceImpl implements PostCommentService {

    private final PostCommentRepository postCommentRepository;

    @Autowired
    public PostCommentServiceImpl(PostCommentRepository postCommentRepository) {
        this.postCommentRepository = postCommentRepository;
    }

    @Override
    public Page<PostComment> findAll(String postId, Pageable pageable) {
        if (StringUtils.isBlank(postId)) throw new BadRequestException("Post id should not be null or blank");

        pageable = pageable == null ? Pageable.unpaged() : pageable;

        return postCommentRepository.findAllByPostId_PostId(postId, pageable);
    }

    @Override
    public Optional<PostComment> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Comment id should not be null or 0");

        return postCommentRepository.findById(id);
    }

    @Override
    public long countByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        return postCommentRepository.countAllByUserId_IdAndDeletedAtIsNull(userId);
    }

    @Transactional
    @Override
    public PostComment save(PostComment postComment) {
        return postCommentRepository.save(postComment);
    }
}
