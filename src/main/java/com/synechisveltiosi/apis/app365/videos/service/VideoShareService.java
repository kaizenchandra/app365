package com.synechisveltiosi.apis.app365.videos.service;

import com.synechisveltiosi.apis.app365.videos.entity.VideoShare;

import java.util.Optional;

public interface VideoShareService {

    Optional<VideoShare> findById(Long id);

    long countByUserId(Long userId);

    VideoShare save(VideoShare videoShare);
}
