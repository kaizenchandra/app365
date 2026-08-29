package com.synechisveltiosi.apis.app365.videos;

import com.synechisveltiosi.apis.app365.common.auth.SessionUtils;
import com.synechisveltiosi.apis.app365.common.dto.comments.CommentRequest;
import com.synechisveltiosi.apis.app365.common.dto.comments.CommentResponse;
import com.synechisveltiosi.apis.app365.common.dto.id.IdResponse;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.CursorResponse;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PageResponse;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PageResponseBuilder;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PaginationResponse;
import com.synechisveltiosi.apis.app365.common.util.domain.DefaultPageable;
import com.synechisveltiosi.apis.app365.common.util.mapper.Mapper;
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.synechisveltiosi.apis.app365.videos.dto.VideoResponse;
import com.synechisveltiosi.apis.app365.videos.entity.Video;
import com.synechisveltiosi.apis.app365.videos.entity.VideoComment;
import com.synechisveltiosi.apis.app365.videos.helper.VideoHelper;
import com.synechisveltiosi.apis.app365.videos.mapper.VideoCommentMapper;
import com.synechisveltiosi.apis.app365.videos.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/videos",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class VideoController {

    private final VideoService videoService;
    private final Mapper<VideoComment, CommentResponse> commentResponseMapper;

    @Autowired
    public VideoController(VideoService videoService, Mapper<VideoComment, CommentResponse> commentResponseMapper) {
        this.videoService = videoService;
        this.commentResponseMapper = commentResponseMapper;
    }

    @GetMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<PageResponse<VideoResponse>> getAll(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "summary", required = false) boolean summary
    ) {

        User loggedUser = SessionUtils.getLoggedUser();

        // Construct the pageable
        Pageable pageable = DefaultPageable.builder()
                .with(page, size, sortBy, Video.SORTABLE_FIELDS)
                .withDefaults(Video.DEFAULT_PAGE, Video.MAX_PAGE_SIZE, Video.Sortable.DEFAULT_SORT)
                .withMaxSize(Video.MAX_PAGE_SIZE)
                .build();

        // Execute the search
        Page<Video> videoPage = videoService.findAll(query, pageable);

        List<VideoResponse> responseData = VideoHelper.processVideoMetadata(loggedUser.getId(), videoPage.getContent());

        if (!summary) {
            responseData = VideoHelper.nullifyListOnlyFields(responseData);
        }

        // Prepare the response
        PageResponse<VideoResponse> pageResponse = PageResponseBuilder.<VideoResponse>builder()
                .withData(responseData)
                .withPagination(PaginationResponse.from(videoPage))
                .build();

        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<VideoResponse> getOne(@PathVariable @NotBlank String id) {
        Optional<Video> videoOptional = videoService.findById(id);

        // Get the video
        VideoResponse videoResponse = VideoHelper.processVideoMetada(SessionUtils.getLoggedUser().getId(),
                videoOptional.orElseThrow(VideoNotFoundException::new));

        // Find next video if any and add next cursor
        Optional<Video> nextVideoOptional = videoService.findNextFrom(videoOptional.get().getId());
        nextVideoOptional.ifPresent(video -> videoResponse.setCursor(new CursorResponse(video.getVideoId())));

        return ResponseEntity.ok(videoResponse);
    }

    @PostMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody VideoRequest request) {

        Video toVideo = request.mapToVideo();

        toVideo.setUserId(SessionUtils.getLoggedUser());
        Video video = videoService.save(toVideo);

        return ResponseEntity.status(HttpStatus.CREATED).body(new IdResponse(video.getVideoId()));
    }


    @PostMapping(value = "/{id}/likes")
    public ResponseEntity<Video> doLike(@PathVariable @NotBlank String id) {
        User loggedUser = SessionUtils.getLoggedUser();

        // Like the video
        videoService.like(id, loggedUser.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{id}/likes", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> doUnlike(@PathVariable @NotBlank String id) {
        User loggedUser = SessionUtils.getLoggedUser();

        // Unlike the video
        videoService.unlike(id, loggedUser.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/{id}/share")
    public ResponseEntity<Void> doShare(@PathVariable @NotBlank String id) {
        User loggedUser = SessionUtils.getLoggedUser();

        // Share the video
        videoService.share(id, loggedUser.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{id}/comments", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<PageResponse<CommentResponse>> getComments(
            @PathVariable @NotBlank String id,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sortBy", required = false) String sortBy) {

        // Construct the pageable
        Pageable pageable = DefaultPageable.builder()
                .with(page, size, sortBy, VideoComment.SORTABLE_FIELDS)
                .withDefaults(VideoComment.DEFAULT_PAGE, VideoComment.MAX_PAGE_SIZE, VideoComment.Sortable.DEFAULT_SORT)
                .withMaxSize(VideoComment.MAX_PAGE_SIZE)
                .build();

        // Execute the search
        Page<VideoComment> commentPage = videoService.findAllComments(id, pageable);

        // Prepare the response
        PageResponse<CommentResponse> pageResponse = PageResponseBuilder.<CommentResponse>builder()
                .withData(commentResponseMapper.map(commentPage.getContent()))
                .withPagination(PaginationResponse.from(commentPage))
                .build();

        return ResponseEntity.ok(pageResponse);
    }

    @PostMapping(value = "/{id}/comments")
    public ResponseEntity<Void> saveComments(
            @PathVariable @NotBlank String id, @Valid @RequestBody CommentRequest commentRequest) {

        User loggedUser = SessionUtils.getLoggedUser();

        videoService.saveComment(loggedUser.getId(), id, VideoCommentMapper.INSTANCE.from(commentRequest));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
