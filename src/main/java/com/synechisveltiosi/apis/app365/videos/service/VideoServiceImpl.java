package com.synechisveltiosi.apis.app365.videos.service;

import com.synechisveltiosi.apis.app365.actions.entity.ActionType;
import com.synechisveltiosi.apis.app365.common.repository.DefaultRsqlRepository;
import com.synechisveltiosi.apis.app365.common.repository.RsqlRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotModifiedException;
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.synechisveltiosi.apis.app365.users.event.UserActionOccurredEvent;
import com.synechisveltiosi.apis.app365.users.exception.UserNotFoundException;
import com.synechisveltiosi.apis.app365.users.service.UserService;
import com.synechisveltiosi.apis.app365.videos.VideoNotFoundException;
import com.synechisveltiosi.apis.app365.videos.entity.Video;
import com.synechisveltiosi.apis.app365.videos.entity.VideoComment;
import com.synechisveltiosi.apis.app365.videos.entity.VideoLike;
import com.synechisveltiosi.apis.app365.videos.entity.VideoShare;
import com.synechisveltiosi.apis.app365.videos.repository.VideoRepository;
import cz.jirutka.rsql.parser.RSQLParserException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final RsqlRepository<Video> rsqlRepository;
    private final UserService userService;
    private final VideoLikeService videoLikeService;
    private final VideoShareService videoShareService;
    private final VideoCommentService videoCommentService;
    private final VideoSummaryService videoSummaryService;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public VideoServiceImpl(VideoRepository videoRepository, UserService userService, VideoLikeService videoLikeService,
                            VideoShareService videoShareService, VideoCommentService videoCommentService,
                            VideoSummaryService videoSummaryService, ApplicationEventPublisher publisher,
                            EntityManager entityManager) {

        this.videoRepository = videoRepository;
        this.userService = userService;
        this.videoLikeService = videoLikeService;
        this.videoShareService = videoShareService;
        this.videoCommentService = videoCommentService;
        this.videoSummaryService = videoSummaryService;
        this.publisher = publisher;

        rsqlRepository = new DefaultRsqlRepository<>(entityManager, Video.class)
                .withAllowedFields(Video.SEARCHABLE_FIELDS);
    }

    @Override
    public Page<Video> findAll(String query, Pageable pageable) throws RSQLParserException {
        pageable = pageable == null ? Pageable.unpaged() : pageable;

        if (StringUtils.isEmpty(query))
            return videoRepository.findAll(pageable);

        return rsqlRepository.findAll(query, pageable);
    }

    @Override
    public Optional<Video> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Video id should not be null or 0");

        return videoRepository.findById(id);
    }

    @Override
    public Optional<Video> findById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("Video id should not be null or blank");

        return videoRepository.findByVideoId(id);
    }

    @Override
    public Optional<Video> findNextFrom(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Video id should not be null or 0");

        return videoRepository.findFirstByIdGreaterThanOrderByIdAsc(id);
    }

    @Transactional
    @Override
    public Video save(Video video) {
        Optional<User> user = userService.findById(video.getUserId().getId());

        if ( ! user.isPresent()) {
            throw new UserNotFoundException();
        }

        video.setUserId(user.get());

        return videoRepository.save(video);
    }

    @Transactional
    @Override
    public void like(String videoId, Long userId) {
        if (StringUtils.isBlank(videoId)) throw new BadRequestException("Video id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the video
        Optional<Video> videoOptional = findById(videoId);
        if (!videoOptional.isPresent()) throw new VideoNotFoundException();

        // Find the video like to remove
        Optional<VideoLike> videoLikeOptional = videoLikeService.findByUserIdAndVideoId(
                userOptional.get().getId(), videoOptional.get().getId());
        if (videoLikeOptional.isPresent() && (videoLikeOptional.get().isLiked() != null
                && videoLikeOptional.get().isLiked())) {
            throw new NotModifiedException("You might already liked this video.");
        }

        // Create like object
        VideoLike videoLike = new VideoLike();
        if (videoLikeOptional.isPresent()) {
            videoLike = videoLikeOptional.get();
        } else {
            videoLike.setUserId(userOptional.get());
            videoLike.setVideoId(videoOptional.get());
        }

        // Save the like
        try {
            videoLike.setLiked(Boolean.TRUE);
            videoLikeService.save(videoLike);
        } catch (DataIntegrityViolationException ex) {
            throw new NotModifiedException("You might already liked this video.");
        }

        // Increment like count for this video
        videoSummaryService.incrementLike(videoOptional.get());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.VIDEO_LIKE));
    }

    @Transactional
    @Override
    public void unlike(String videoId, Long userId) {
        if (StringUtils.isBlank(videoId)) throw new BadRequestException("Video id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the video
        Optional<Video> videoOptional = findById(videoId);
        if (!videoOptional.isPresent()) throw new VideoNotFoundException();

        // Find the video like to remove
        Optional<VideoLike> videoLikeOptional = videoLikeService.findByUserIdAndVideoId(
                userOptional.get().getId(), videoOptional.get().getId());
        if (!videoLikeOptional.isPresent() || (videoLikeOptional.get().isLiked() == null
                || !videoLikeOptional.get().isLiked())) {
            throw new NotModifiedException("You might not already liked this video.");
        }

        // Remove the like
        VideoLike videoLike = videoLikeOptional.get();
        videoLike.setLiked(Boolean.FALSE);
        videoLikeService.save(videoLike);

        // Decrement like count for this video
        videoSummaryService.decreaseLike(videoOptional.get());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.VIDEO_LIKE));
    }

    @Transactional
    @Override
    public void share(String videoId, Long userId) {
        if (StringUtils.isBlank(videoId)) throw new BadRequestException("Video id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the video
        Optional<Video> videoOptional = findById(videoId);
        if (!videoOptional.isPresent()) throw new VideoNotFoundException();

        // Create share object
        VideoShare videoShare = new VideoShare();
        videoShare.setUserId(userOptional.get());
        videoShare.setVideoId(videoOptional.get());

        // Save the share
        videoShareService.save(videoShare);

        // Increment share count for this video
        videoSummaryService.incrementShare(videoOptional.get());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.VIDEO_SHARE));
    }

    @Override
    public Page<VideoComment> findAllComments(String videoId, Pageable pageable) {
        pageable = pageable == null ? Pageable.unpaged() : pageable;

        return videoCommentService.findAll(videoId, pageable);
    }

    @Transactional
    @Override
    public VideoComment saveComment(Long userId, String videoId, VideoComment comment) {
        if (StringUtils.isBlank(videoId)) throw new BadRequestException("Video id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the video
        Optional<Video> videoOptional = findById(videoId);
        if (!videoOptional.isPresent()) throw new VideoNotFoundException();

        // Complete comment object
        comment.setVideoId(videoOptional.get());
        comment.setUserId(userOptional.get());

        VideoComment newComment = videoCommentService.save(comment);

        // Increment comment count for this video
        videoSummaryService.incrementComment(videoOptional.get());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.VIDEO_COMMENT));

        return newComment;
    }
}
