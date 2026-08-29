package com.synechisveltiosi.apis.app365.videos.service;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.videos.entity.VideoComment;
import com.synechisveltiosi.apis.app365.videos.repository.VideoCommentRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VideoCommentServiceImpl implements VideoCommentService {

    private final VideoCommentRepository videoCommentRepository;

    @Autowired
    public VideoCommentServiceImpl(VideoCommentRepository videoCommentRepository) {
        this.videoCommentRepository = videoCommentRepository;
    }

    @Override
    public Page<VideoComment> findAll(String videoId, Pageable pageable) {
        if (StringUtils.isBlank(videoId)) throw new BadRequestException("Video id should not be null or blank");

        pageable = pageable == null ? Pageable.unpaged() : pageable;

        return videoCommentRepository.findAllByVideoId_VideoId(videoId, pageable);
    }

    @Override
    public Optional<VideoComment> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Comment id should not be null or 0");

        return videoCommentRepository.findById(id);
    }

    @Override
    public long countByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        return videoCommentRepository.countAllByUserId_IdAndDeletedAtIsNull(userId);
    }

    @Transactional
    @Override
    public VideoComment save(VideoComment videoComment) {
        return videoCommentRepository.save(videoComment);
    }
}
