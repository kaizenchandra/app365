package com.synechisveltiosi.apis.app365.candidates.service;

import com.synechisveltiosi.apis.app365.actions.entity.ActionType;
import com.synechisveltiosi.apis.app365.candidates.PostNotFoundException;
import com.synechisveltiosi.apis.app365.candidates.entity.Post;
import com.synechisveltiosi.apis.app365.candidates.entity.PostComment;
import com.synechisveltiosi.apis.app365.candidates.repository.PostRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.synechisveltiosi.apis.app365.users.event.UserActionOccurredEvent;
import com.synechisveltiosi.apis.app365.users.exception.UserNotFoundException;
import com.synechisveltiosi.apis.app365.users.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostCommentService postCommentService;
    private final UserService userService;
    private final CandidateSummaryService candidateSummaryService;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, PostCommentService postCommentService,
                           UserService userService, CandidateSummaryService candidateSummaryService,
                           ApplicationEventPublisher publisher) {

        this.postRepository = postRepository;
        this.postCommentService = postCommentService;
        this.userService = userService;
        this.candidateSummaryService = candidateSummaryService;
        this.publisher = publisher;
    }

    @Override
    public Page<Post> findAll(String candidateId, Pageable pageable) {
        if (StringUtils.isBlank(candidateId)) throw new BadRequestException("Candidate id should not be null or blank");

        pageable = pageable == null ? Pageable.unpaged() : pageable;

        return postRepository.findAllByCandidateId_CandidateId(candidateId, pageable);
    }

    @Override
    public Optional<Post> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Post id should not be null or 0");

        return postRepository.findById(id);
    }

    @Override
    public Optional<Post> findById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("Post id should not be null or blank");

        return postRepository.findByPostId(id);
    }

    @Transactional
    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Page<PostComment> findAllComments(String postId, Pageable pageable) {
        if (StringUtils.isBlank(postId)) throw new BadRequestException("Post id should not be null or blank");

        pageable = pageable == null ? Pageable.unpaged() : pageable;

        return postCommentService.findAll(postId, pageable);
    }

    @Transactional
    @Override
    public PostComment saveComment(Long userId, String postId, PostComment comment) {
        if (StringUtils.isBlank(postId)) throw new BadRequestException("Post id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the candidate
        Optional<Post> postOptional = findById(postId);
        if (!postOptional.isPresent()) throw new PostNotFoundException();

        // Complete post object
        comment.setUserId(userOptional.get());
        comment.setPostId(postOptional.get());

        PostComment newComment = postCommentService.save(comment);

        // Increment post comment count for this candidate
        candidateSummaryService.incrementPostComment(postOptional.get().getCandidateId());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.CANDIDATE_POST_COMMENT));

        return newComment;
    }
}
