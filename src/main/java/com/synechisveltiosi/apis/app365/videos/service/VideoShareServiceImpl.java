package com.synechisveltiosi.apis.app365.videos.service;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.videos.entity.VideoShare;
import com.synechisveltiosi.apis.app365.videos.repository.VideoShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VideoShareServiceImpl implements VideoShareService {

    private final VideoShareRepository videoShareRepository;

    @Autowired
    public VideoShareServiceImpl(VideoShareRepository videoShareRepository) {
        this.videoShareRepository = videoShareRepository;
    }

    @Override
    public Optional<VideoShare> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Video share id should not be null or 0");

        return videoShareRepository.findById(id);
    }

    @Override
    public long countByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        return videoShareRepository.countAllByUserId_Id(userId);
    }

    @Transactional
    @Override
    public VideoShare save(VideoShare videoShare) {
        return videoShareRepository.save(videoShare);
    }
}
