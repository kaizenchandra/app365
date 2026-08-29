package com.synechisveltiosi.apis.app365.videos.service;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotModifiedException;
import com.synechisveltiosi.apis.app365.videos.entity.Video;
import com.synechisveltiosi.apis.app365.videos.entity.VideoSummary;
import com.synechisveltiosi.apis.app365.videos.repository.VideoSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class VideoSummaryServiceImpl implements VideoSummaryService {

    private final VideoSummaryRepository videoSummaryRepository;

    @Autowired
    public VideoSummaryServiceImpl(VideoSummaryRepository videoSummaryRepository) {
        this.videoSummaryRepository = videoSummaryRepository;
    }

    @Override
    public Optional<VideoSummary> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Video summary id should not be null or 0");

        return videoSummaryRepository.findById(id);
    }

    @Transactional
    @Override
    public VideoSummary save(VideoSummary videoSummary) {
        return videoSummaryRepository.save(videoSummary);
    }

    @Transactional
    @Override
    public void incrementLike(Video video) {
        // Find the summary for this video
        Optional<VideoSummary> videoSummaryOptional = getVideoSummary(video);

        // Increase the like field and persist result
        videoSummaryOptional.ifPresent(videoSummary -> {
            videoSummary.increaseLikeCount();
            videoSummary.setLastLikeAt(new Date());

            // Save the summary
            save(videoSummary);
        });
    }

    @Transactional
    @Override
    public void decreaseLike(Video video) {
        // Find the summary for this video
        Optional<VideoSummary> videoSummaryOptional = videoSummaryRepository.findByVideoId_Id(video.getId());

        // If no summary yet, nothing will change
        if (!videoSummaryOptional.isPresent()) throw new NotModifiedException();

        // Decrease the like field and persist result
        videoSummaryOptional.ifPresent(videoSummary -> {
            videoSummary.decreaseLikeCount();

            // Save the summary
            save(videoSummary);
        });
    }

    @Transactional
    @Override
    public void incrementShare(Video video) {
        // Find the summary for this video
        Optional<VideoSummary> videoSummaryOptional = getVideoSummary(video);

        // Increase the share field and persist result
        videoSummaryOptional.ifPresent(videoSummary -> {
            videoSummary.increaseShareCount();
            videoSummary.setLastShareAt(new Date());

            // Save the summary
            save(videoSummary);
        });
    }

    @Transactional
    @Override
    public void incrementComment(Video video) {
        Optional<VideoSummary> videoSummaryOptional = getVideoSummary(video);

        // Increase the comment field and persist result
        videoSummaryOptional.ifPresent(videoSummary -> {
            videoSummary.increaseCommentCount();
            videoSummary.setLastCommentAt(new Date());

            // Save the summary
            save(videoSummary);
        });
    }

    @Transactional
    @Override
    public void decreaseComment(Video video) {
        // Find the summary for this video
        Optional<VideoSummary> videoSummaryOptional = videoSummaryRepository.findByVideoId_Id(video.getId());

        // If no summary yet, nothing will change
        if (!videoSummaryOptional.isPresent()) throw new NotModifiedException();

        // Decrease the comment field and persist result
        videoSummaryOptional.ifPresent(videoSummary -> {
            videoSummary.decreaseCommentCount();

            // Save the summary
            save(videoSummary);
        });
    }

    /**
     * Get or create the first video summary
     *
     * @param video
     * @return
     */
    private Optional<VideoSummary> getVideoSummary(Video video) {
        // Find the summary for this video
        Optional<VideoSummary> videoSummaryOptional = videoSummaryRepository.findByVideoId_Id(video.getId());

        // If no summary yet, create one
        if (!videoSummaryOptional.isPresent()) {
            VideoSummary videoSummary = new VideoSummary();
            videoSummary.setVideoId(video);

            // Save this video summary
            videoSummaryOptional = Optional.of(this.save(videoSummary));
        }

        return videoSummaryOptional;
    }
}
