package com.synechisveltiosi.apis.app365.videos.service;

import com.synechisveltiosi.apis.app365.videos.entity.Video;
import com.synechisveltiosi.apis.app365.videos.entity.VideoSummary;

import java.util.Optional;

public interface VideoSummaryService {

    Optional<VideoSummary> findById(Long id);

    VideoSummary save(VideoSummary videoSummary);

    void incrementLike(Video video);

    void decreaseLike(Video video);

    void incrementShare(Video video);

    void incrementComment(Video video);

    void decreaseComment(Video video);
}
