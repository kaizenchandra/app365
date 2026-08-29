package com.synechisveltiosi.apis.app365.videos.service;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.videos.entity.VideoLike;
import com.synechisveltiosi.apis.app365.videos.repository.VideoLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VideoLikeServiceImpl implements VideoLikeService {

    private final VideoLikeRepository videoLikeRepository;

    @Autowired
    public VideoLikeServiceImpl(VideoLikeRepository videoLikeRepository) {
        this.videoLikeRepository = videoLikeRepository;
    }

    @Override
    public Optional<VideoLike> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Video like id should not be null or 0");

        return videoLikeRepository.findById(id);
    }

    @Override
    public Optional<VideoLike> findByUserIdAndVideoId(Long userId, Long videoId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (videoId == null || videoId == 0) throw new BadRequestException("Video id should not be null or 0");

        return videoLikeRepository.findByUserId_IdAndVideoId_Id(userId, videoId);
    }

    @Override
    public long countByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        return videoLikeRepository.countAllByUserId_IdAndLikedIsTrue(userId);
    }

    @Transactional
    @Override
    public VideoLike save(VideoLike videoLike) {
        return videoLikeRepository.save(videoLike);
    }

    @Transactional
    @Override
    public void deleteByUserIdAndVideoId(Long userId, Long videoId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (videoId == null || videoId == 0) throw new BadRequestException("Video id should not be null or 0");

        videoLikeRepository.deleteByUserId_IdAndVideoId_Id(userId, videoId);
    }
}
