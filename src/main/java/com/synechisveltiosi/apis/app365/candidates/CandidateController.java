package com.synechisveltiosi.apis.app365.candidates;

import com.synechisveltiosi.apis.app365.candidates.dto.CandidateResponse;
import com.synechisveltiosi.apis.app365.candidates.dto.PostResponse;
import com.synechisveltiosi.apis.app365.candidates.entity.Candidate;
import com.synechisveltiosi.apis.app365.candidates.entity.CandidateComment;
import com.synechisveltiosi.apis.app365.candidates.entity.Post;
import com.synechisveltiosi.apis.app365.candidates.entity.PostComment;
import com.synechisveltiosi.apis.app365.candidates.mapper.CandidateCommentMapper;
import com.synechisveltiosi.apis.app365.candidates.mapper.CandidateMapper;
import com.synechisveltiosi.apis.app365.candidates.mapper.PostCommentMapper;
import com.synechisveltiosi.apis.app365.candidates.service.CandidateService;
import com.synechisveltiosi.apis.app365.candidates.service.PostService;
import com.synechisveltiosi.apis.app365.common.auth.SessionUtils;
import com.synechisveltiosi.apis.app365.common.dto.comments.CommentRequest;
import com.synechisveltiosi.apis.app365.common.dto.comments.CommentResponse;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PageResponse;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PageResponseBuilder;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PaginationResponse;
import com.synechisveltiosi.apis.app365.common.util.domain.DefaultPageable;
import com.synechisveltiosi.apis.app365.common.util.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@RestController
@RequestMapping(value = "/candidate",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class CandidateController {

    private final CandidateService candidateService;
    private final PostService postService;
    private final Mapper<CandidateComment, CommentResponse> candidateCommentResponseMapper;
    private final Mapper<Post, PostResponse> postResponseMapper;
    private final Mapper<PostComment, CommentResponse> postCommentCommentResponseMapper;

    @Autowired
    public CandidateController(
            CandidateService candidateService, PostService postService,
            Mapper<CandidateComment, CommentResponse> candidateCommentResponseMapper,
            Mapper<Post, PostResponse> postResponseMapper,
            Mapper<PostComment, CommentResponse> postCommentCommentResponseMapper) {

        this.candidateService = candidateService;
        this.postService = postService;
        this.candidateCommentResponseMapper = candidateCommentResponseMapper;
        this.postResponseMapper = postResponseMapper;
        this.postCommentCommentResponseMapper = postCommentCommentResponseMapper;
    }

    @GetMapping(value = "/info", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<CandidateResponse> getInfo() {
        // Find the candidate for this user
        Optional<Candidate> candidateOptional = candidateService.findFirstCandidate();
        if (!candidateOptional.isPresent()) throw new CandidateNotFoundException();

        return ResponseEntity.ok(CandidateMapper.INSTANCE.from(SessionUtils.getLoggedUser().getId(),
                candidateOptional.get()));
    }

    @PostMapping(value = "/likes")
    public ResponseEntity<Void> doLike() {
        // Find the candidate for this user
        Optional<Candidate> candidateOptional = candidateService.findFirstCandidate();
        if (!candidateOptional.isPresent()) throw new CandidateNotFoundException();

        // Like the candidate profile
        candidateService.like(candidateOptional.get().getCandidateId(), SessionUtils.getLoggedUser().getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/likes", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> doUnlike() {
        // Find the candidate for this user
        Optional<Candidate> candidateOptional = candidateService.findFirstCandidate();
        if (!candidateOptional.isPresent()) throw new CandidateNotFoundException();

        // Unlike the candidate profile
        candidateService.unlike(candidateOptional.get().getCandidateId(), SessionUtils.getLoggedUser().getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/share")
    public ResponseEntity<Void> doShare() {
        // Find the candidate for this user
        Optional<Candidate> candidateOptional = candidateService.findFirstCandidate();
        if (!candidateOptional.isPresent()) throw new CandidateNotFoundException();

        // Share the candidate profile
        candidateService.share(candidateOptional.get().getCandidateId(), SessionUtils.getLoggedUser().getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/comments", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<PageResponse<CommentResponse>> getComments(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sortBy", required = false) String sortBy) {

        // Find the candidate for this user
        Optional<Candidate> candidateOptional = candidateService.findFirstCandidate();
        if (!candidateOptional.isPresent()) throw new CandidateNotFoundException();

        // Construct the pageable
        Pageable pageable = DefaultPageable.builder()
                .with(page, size, sortBy, CandidateComment.SORTABLE_FIELDS)
                .withDefaults(CandidateComment.DEFAULT_PAGE, CandidateComment.MAX_PAGE_SIZE,
                        CandidateComment.Sortable.DEFAULT_SORT)
                .withMaxSize(CandidateComment.MAX_PAGE_SIZE)
                .build();

        // Execute the search
        Page<CandidateComment> commentPage = candidateService.findAllComments(
                candidateOptional.get().getCandidateId(), pageable);

        // Prepare the response
        PageResponse<CommentResponse> pageResponse = PageResponseBuilder.<CommentResponse>builder()
                .withData(candidateCommentResponseMapper.map(commentPage.getContent()))
                .withPagination(PaginationResponse.from(commentPage))
                .build();

        return ResponseEntity.ok(pageResponse);
    }

    @PostMapping(value = "/comments")
    public ResponseEntity<Void> saveComment(@Valid @RequestBody CommentRequest commentRequest) {
        // Find the candidate for this user
        Optional<Candidate> candidateOptional = candidateService.findFirstCandidate();
        if (!candidateOptional.isPresent()) throw new CandidateNotFoundException();

        candidateService.saveComment(SessionUtils.getLoggedUser().getId(), candidateOptional.get().getCandidateId(),
                CandidateCommentMapper.INSTANCE.from(commentRequest));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/social/posts", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<PageResponse<PostResponse>> getSocialPosts(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sortBy", required = false) String sortBy) {

        // Find the candidate for this user
        Optional<Candidate> candidateOptional = candidateService.findFirstCandidate();
        if (!candidateOptional.isPresent()) throw new CandidateNotFoundException();

        // Construct the pageable
        Pageable pageable = DefaultPageable.builder()
                .with(page, size, sortBy, Post.SORTABLE_FIELDS)
                .withDefaults(Post.DEFAULT_PAGE, Post.MAX_PAGE_SIZE, Post.Sortable.DEFAULT_SORT)
                .withMaxSize(Post.MAX_PAGE_SIZE)
                .build();

        // Execute the search
        Page<Post> postPage = candidateService.findAllPosts(candidateOptional.get().getCandidateId(), pageable);

        // Prepare the response
        PageResponse<PostResponse> pageResponse = PageResponseBuilder.<PostResponse>builder()
                .withData(postResponseMapper.map(postPage.getContent()))
                .withPagination(PaginationResponse.from(postPage))
                .build();

        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping(value = "/social/posts/{id}/comments", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<PageResponse<CommentResponse>> getPostComments(
            @PathVariable @NotBlank String id,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sortBy", required = false) String sortBy) {

        // Construct the pageable
        Pageable pageable = DefaultPageable.builder()
                .with(page, size, sortBy, PostComment.SORTABLE_FIELDS)
                .withDefaults(PostComment.DEFAULT_PAGE, PostComment.MAX_PAGE_SIZE, PostComment.Sortable.DEFAULT_SORT)
                .withMaxSize(PostComment.MAX_PAGE_SIZE)
                .build();

        // Execute the search
        Page<PostComment> commentPage = postService.findAllComments(id, pageable);

        // Prepare the response
        PageResponse<CommentResponse> pageResponse = PageResponseBuilder.<CommentResponse>builder()
                .withData(postCommentCommentResponseMapper.map(commentPage.getContent()))
                .withPagination(PaginationResponse.from(commentPage))
                .build();

        return ResponseEntity.ok(pageResponse);
    }

    @PostMapping(value = "/social/posts/{id}/comments")
    public ResponseEntity<Void> savePostComment(
            @PathVariable @NotBlank String id, @Valid @RequestBody CommentRequest commentRequest) {

        postService.saveComment(SessionUtils.getLoggedUser().getId(), id,
                PostCommentMapper.INSTANCE.from(commentRequest));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
