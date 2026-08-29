package com.synechisveltiosi.apis.app365.candidates.service;

import com.synechisveltiosi.apis.app365.actions.entity.ActionType;
import com.synechisveltiosi.apis.app365.candidates.CandidateNotFoundException;
import com.synechisveltiosi.apis.app365.candidates.entity.*;
import com.synechisveltiosi.apis.app365.candidates.repository.CandidateRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotModifiedException;
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.synechisveltiosi.apis.app365.users.event.UserActionOccurredEvent;
import com.synechisveltiosi.apis.app365.users.exception.UserNotFoundException;
import com.synechisveltiosi.apis.app365.users.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CandidateServiceImpl implements CandidateService {

    private static final Logger logger = LoggerFactory.getLogger(CandidateServiceImpl.class);

    private final CandidateRepository candidateRepository;
    private final UserService userService;
    private final CandidateLikeService candidateLikeService;
    private final CandidateShareService candidateShareService;
    private final CandidateCommentService candidateCommentService;
    private final CandidateSummaryService candidateSummaryService;
    private final CandidateDonationService candidateDonationService;
    private final CandidateOfferService candidateOfferService;
    private final PostService postService;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public CandidateServiceImpl(
            CandidateRepository candidateRepository, UserService userService, CandidateLikeService candidateLikeService,
            CandidateShareService candidateShareService, CandidateCommentService candidateCommentService,
            CandidateSummaryService candidateSummaryService, PostService postService,
            CandidateDonationService candidateDonationService, CandidateOfferService candidateOfferService,
            ApplicationEventPublisher publisher) {

        this.candidateRepository = candidateRepository;
        this.userService = userService;
        this.candidateLikeService = candidateLikeService;
        this.candidateShareService = candidateShareService;
        this.candidateCommentService = candidateCommentService;
        this.candidateSummaryService = candidateSummaryService;
        this.candidateDonationService = candidateDonationService;
        this.candidateOfferService = candidateOfferService;
        this.postService = postService;
        this.publisher = publisher;
    }

    @Override
    public Optional<Candidate> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Candidate id should not be null or 0");

        return candidateRepository.findById(id);
    }

    @Override
    public Optional<Candidate> findById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("Candidate id should not be null or blank");

        return candidateRepository.findByCandidateId(id);
    }

    @Override
    public Optional<Candidate> findFirstCandidate() {
        return candidateRepository.findFirstByCandidateIdIsNotNull();
    }

    @Transactional
    @Override
    public Candidate save(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Transactional
    @Override
    public void donate(String candidateId, Long userId, CandidateDonation donation) {
        if (donation == null || donation.getAmount() == null || donation.getAmount() == 0)
            throw new BadRequestException("Donation amount cannot be 0");

        if (StringUtils.isBlank(candidateId))
            throw new BadRequestException("Candidate id should not be null or blank");

        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the candidate
        Optional<Candidate> candidateOptional = findById(candidateId);
        if (!candidateOptional.isPresent()) throw new CandidateNotFoundException();

        // Create donation object
        donation.setUserId(userOptional.get());
        donation.setCandidateId(candidateOptional.get());

        // Save the donation
        candidateDonationService.save(donation);

        // Increment donation count for this candidate
        candidateSummaryService.incrementDonation(candidateOptional.get());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.DONATION));
    }

    @Transactional
    @Override
    public void removeDonation(String candidateId, Long userId) {
        if (StringUtils.isBlank(candidateId)) throw new BadRequestException("Candidate id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the candidate
        Optional<Candidate> candidateOptional = findById(candidateId);
        if (!candidateOptional.isPresent()) throw new CandidateNotFoundException();

        // Find the donation to remove
        Optional<CandidateDonation> candidateDonationOptional = candidateDonationService.findByUserIdAndCandidateId(
                userOptional.get().getId(), candidateOptional.get().getId());
        if (!candidateDonationOptional.isPresent())
            throw new NotModifiedException("You might not already make a donation to this candidate.");

        // Remove the donation
        candidateDonationService.deleteByUserIdAndCandidateId(userOptional.get().getId(), candidateOptional.get().getId());

        // Decrease donation count for this candidate
        candidateSummaryService.decreaseDonation(candidateOptional.get());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.DONATION));
    }

    @Transactional
    @Override
    public void offer(String candidateId, Long userId) {
        if (StringUtils.isBlank(candidateId))
            throw new BadRequestException("Candidate id should not be null or blank");

        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the candidate
        Optional<Candidate> candidateOptional = findById(candidateId);
        if (!candidateOptional.isPresent()) throw new CandidateNotFoundException();

        // Create offer object
        CandidateOffer offer = new CandidateOffer();
        offer.setUserId(userOptional.get());
        offer.setCandidateId(candidateOptional.get());

        // Save the offer
        candidateOfferService.save(offer);

        // Increment offer count for this candidate
        candidateSummaryService.incrementOffer(candidateOptional.get());
    }

    @Transactional
    @Override
    public void removeOffer(String candidateId, Long userId) {
        if (StringUtils.isBlank(candidateId)) throw new BadRequestException("Candidate id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the candidate
        Optional<Candidate> candidateOptional = findById(candidateId);
        if (!candidateOptional.isPresent()) throw new CandidateNotFoundException();

        // Find the offer to remove
        Optional<CandidateOffer> candidateOfferOptional = candidateOfferService.findByUserIdAndCandidateId(
                userOptional.get().getId(), candidateOptional.get().getId());
        if (!candidateOfferOptional.isPresent())
            throw new NotModifiedException("You might not already make an offer to this candidate.");

        // Remove the offer
        candidateOfferService.deleteByUserIdAndCandidateId(userOptional.get().getId(), candidateOptional.get().getId());

        // Decrease offer count for this candidate
        candidateSummaryService.decreaseOffer(candidateOptional.get());
    }

    @Transactional
    @Override
    public void like(String candidateId, Long userId) {
        if (StringUtils.isBlank(candidateId)) throw new BadRequestException("Candidate id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the candidate
        Optional<Candidate> candidateOptional = findById(candidateId);
        if (!candidateOptional.isPresent()) throw new CandidateNotFoundException();

        // Find the candidate like to remove
        Optional<CandidateLike> candidateLikeOptional = candidateLikeService.findByUserIdAndCandidateId(
                userOptional.get().getId(), candidateOptional.get().getId());
        if (candidateLikeOptional.isPresent() && (candidateLikeOptional.get().isLiked() != null
                && candidateLikeOptional.get().isLiked())) {
            throw new NotModifiedException("You might already liked this candidate.");
        }

        // Create like object
        CandidateLike candidateLike = new CandidateLike();
        if (candidateLikeOptional.isPresent()) {
            candidateLike = candidateLikeOptional.get();
        } else {
            candidateLike.setUserId(userOptional.get());
            candidateLike.setCandidateId(candidateOptional.get());
        }

        // Save the like
        try {
            candidateLike.setLiked(Boolean.TRUE);
            candidateLikeService.save(candidateLike);
        } catch (DataIntegrityViolationException ex) {
            throw new NotModifiedException("You might already liked this candidate.");
        }

        // Increment like count for this candidate
        candidateSummaryService.incrementLike(candidateOptional.get());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.CANDIDATE_LIKE));
    }

    @Transactional
    @Override
    public void unlike(String candidateId, Long userId) {
        if (StringUtils.isBlank(candidateId)) throw new BadRequestException("Candidate id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the candidate
        Optional<Candidate> candidateOptional = findById(candidateId);
        if (!candidateOptional.isPresent()) throw new CandidateNotFoundException();

        // Find the candidate like to remove
        Optional<CandidateLike> candidateLikeOptional = candidateLikeService.findByUserIdAndCandidateId(
                userOptional.get().getId(), candidateOptional.get().getId());
        if (!candidateLikeOptional.isPresent() || (candidateLikeOptional.get().isLiked() == null
                || !candidateLikeOptional.get().isLiked())) {
            throw new NotModifiedException("You might not already liked this candidate.");
        }

        // Remove the like
        CandidateLike candidateLike = candidateLikeOptional.get();
        candidateLike.setLiked(Boolean.FALSE);
        candidateLikeService.save(candidateLike);

        // Decrement like count for this candidate
        candidateSummaryService.decreaseLike(candidateOptional.get());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.CANDIDATE_LIKE));
    }

    @Transactional
    @Override
    public void share(String candidateId, Long userId) {
        if (StringUtils.isBlank(candidateId)) throw new BadRequestException("Candidate id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the candidate
        Optional<Candidate> candidateOptional = findById(candidateId);
        if (!candidateOptional.isPresent()) throw new CandidateNotFoundException();

        // Create share object
        CandidateShare candidateShare = new CandidateShare();
        candidateShare.setUserId(userOptional.get());
        candidateShare.setCandidateId(candidateOptional.get());

        // Save the share
        candidateShareService.save(candidateShare);

        // Increment share count for this candidate
        candidateSummaryService.incrementShare(candidateOptional.get());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.CANDIDATE_SHARE));
    }

    @Override
    public Page<CandidateComment> findAllComments(String candidateId, Pageable pageable) {
        pageable = pageable == null ? Pageable.unpaged() : pageable;

        return candidateCommentService.findAll(candidateId, pageable);
    }

    @Transactional
    @Override
    public CandidateComment saveComment(Long userId, String candidateId, CandidateComment comment) {
        if (StringUtils.isBlank(candidateId)) throw new BadRequestException("Candidate id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the candidate
        Optional<Candidate> candidateOptional = findById(candidateId);
        if (!candidateOptional.isPresent()) throw new CandidateNotFoundException();

        // Complete comment object
        comment.setCandidateId(candidateOptional.get());
        comment.setUserId(userOptional.get());

        CandidateComment newComment = candidateCommentService.save(comment);

        // Increment comment count for this candidate
        candidateSummaryService.incrementComment(candidateOptional.get());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.CANDIDATE_COMMENT));

        return newComment;
    }

    @Override
    public Page<Post> findAllPosts(String candidateId, Pageable pageable) {
        pageable = pageable == null ? Pageable.unpaged() : pageable;

        return postService.findAll(candidateId, pageable);
    }

    @Transactional
    @Override
    public Post savePost(String candidateId, Post post) {
        if (StringUtils.isBlank(candidateId)) throw new BadRequestException("Candidate id should not be null or blank");

        // Find the candidate
        Optional<Candidate> candidateOptional = findById(candidateId);
        if (!candidateOptional.isPresent()) throw new CandidateNotFoundException();

        // Complete post object
        post.setCandidateId(candidateOptional.get());

        Post newPost = postService.save(post);

        // Increment comment count for this candidate
        candidateSummaryService.incrementPost(candidateOptional.get());

        return newPost;
    }
}
