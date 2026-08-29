package com.synechisveltiosi.apis.app365.videos.service;

import com.synechisveltiosi.apis.app365.videos.entity.VideoComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VideoCommentService {

    Page<VideoComment> findAll(String videoId, Pageable pageable);

    Optional<VideoComment> findById(Long id);

    long countByUserId(Long userId);

    VideoComment save(VideoComment videoComment);
}
