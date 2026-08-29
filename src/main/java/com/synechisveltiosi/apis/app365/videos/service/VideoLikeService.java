package com.synechisveltiosi.apis.app365.videos.service;

import com.synechisveltiosi.apis.app365.videos.entity.VideoLike;

import java.util.Optional;

public interface VideoLikeService {

    Optional<VideoLike> findById(Long id);

    Optional<VideoLike> findByUserIdAndVideoId(Long userId, Long videoId);

    long countByUserId(Long userId);

    VideoLike save(VideoLike videoLike);

    void deleteByUserIdAndVideoId(Long userId, Long videoId);
}
