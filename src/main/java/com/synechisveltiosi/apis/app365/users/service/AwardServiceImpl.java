package com.synechisveltiosi.apis.app365.users.service;

import com.synechisveltiosi.apis.app365.actions.entity.ActionType;
import com.synechisveltiosi.apis.app365.actions.entity.Medal;
import com.synechisveltiosi.apis.app365.actions.entity.MedalRule;
import com.synechisveltiosi.apis.app365.actions.service.MedalRuleService;
import com.synechisveltiosi.apis.app365.actions.service.MedalService;
import com.synechisveltiosi.apis.app365.campaign.service.TeamMemberService;
import com.synechisveltiosi.apis.app365.campaign.service.VolunteerService;
import com.synechisveltiosi.apis.app365.candidates.entity.CandidateDonation;
import com.applepolitical.apis.applepolitical365.candidates.service.*;
import com.synechisveltiosi.apis.app365.candidates.service.*;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.events.service.EventCommentService;
import com.synechisveltiosi.apis.app365.events.service.EventLikeService;
import com.synechisveltiosi.apis.app365.events.service.EventShareService;
import com.synechisveltiosi.apis.app365.events.service.JoinEventService;
import com.synechisveltiosi.apis.app365.news.service.NewsCommentService;
import com.synechisveltiosi.apis.app365.news.service.NewsLikeService;
import com.synechisveltiosi.apis.app365.news.service.NewsShareService;
import com.synechisveltiosi.apis.app365.notifications.Notification;
import com.synechisveltiosi.apis.app365.users.entity.Award;
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.synechisveltiosi.apis.app365.users.entity.UserPoint;
import com.synechisveltiosi.apis.app365.users.exception.AwardNotFoundException;
import com.synechisveltiosi.apis.app365.users.exception.UserNotFoundException;
import com.synechisveltiosi.apis.app365.users.repository.AwardRepository;
import com.synechisveltiosi.apis.app365.videos.service.VideoCommentService;
import com.synechisveltiosi.apis.app365.videos.service.VideoLikeService;
import com.synechisveltiosi.apis.app365.videos.service.VideoShareService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static com.synechisveltiosi.apis.app365.actions.entity.ActionType.*;

@Service
public class AwardServiceImpl implements AwardService {

    private static final Logger logger = LoggerFactory.getLogger(AwardServiceImpl.class);

    private final AwardRepository awardRepository;
    private final MedalService medalService;
    private final MedalRuleService medalRuleService;
    private final UserService userService;
    private final TeamMemberService teamMemberService;
    private final CandidateDonationService candidateDonationService;
    private final VolunteerService volunteerService;
    private final EventShareService eventShareService;
    private final JoinEventService joinEventService;
    private final EventCommentService eventCommentService;
    private final EventLikeService eventLikeService;
    private final NewsShareService newsShareService;
    private final NewsCommentService newsCommentService;
    private final NewsLikeService newsLikeService;
    private final VideoShareService videoShareService;
    private final VideoCommentService videoCommentService;
    private final VideoLikeService videoLikeService;
    private final CandidateShareService candidateShareService;
    private final CandidateCommentService candidateCommentService;
    private final CandidateLikeService candidateLikeService;
    private final PostCommentService postCommentService;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public AwardServiceImpl(
            AwardRepository awardRepository, MedalService medalService, MedalRuleService medalRuleService,
            UserService userService, CandidateDonationService candidateDonationService,
            VolunteerService volunteerService, TeamMemberService teamMemberService,
            EventShareService eventShareService, JoinEventService joinEventService,
            EventCommentService eventCommentService, EventLikeService eventLikeService,
            NewsShareService newsShareService, NewsCommentService newsCommentService, NewsLikeService newsLikeService,
            VideoShareService videoShareService, VideoCommentService videoCommentService,
            VideoLikeService videoLikeService, CandidateShareService candidateShareService,
            CandidateCommentService candidateCommentService, CandidateLikeService candidateLikeService,
            PostCommentService postCommentService, ApplicationEventPublisher publisher) {

        this.awardRepository = awardRepository;
        this.medalService = medalService;
        this.medalRuleService = medalRuleService;
        this.userService = userService;
        this.candidateDonationService = candidateDonationService;
        this.volunteerService = volunteerService;
        this.teamMemberService = teamMemberService;
        this.eventShareService = eventShareService;
        this.joinEventService = joinEventService;
        this.eventCommentService = eventCommentService;
        this.eventLikeService = eventLikeService;
        this.newsShareService = newsShareService;
        this.newsCommentService = newsCommentService;
        this.newsLikeService = newsLikeService;
        this.videoShareService = videoShareService;
        this.videoCommentService = videoCommentService;
        this.videoLikeService = videoLikeService;
        this.candidateShareService = candidateShareService;
        this.candidateCommentService = candidateCommentService;
        this.candidateLikeService = candidateLikeService;
        this.postCommentService = postCommentService;
        this.publisher = publisher;
    }

    @Override
    public Optional<Award> findByUserIdAndMedalId(Long userId, Long medalId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (medalId == null || medalId == 0) throw new BadRequestException("User id should not be null or 0");

        return awardRepository.findById_UserId_IdAndId_MedalId_Id(userId, medalId);
    }

    @Override
    public List<Award> findByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        return awardRepository.findById_UserId_Id(userId);
    }

    @Override
    public List<Award> findByMedalId(Long medalId) {
        if (medalId == null || medalId == 0) throw new BadRequestException("User id should not be null or 0");

        return awardRepository.findById_MedalId_Id(medalId);
    }

    @Transactional
    @Override
    public Award save(Award award) {
        return awardRepository.save(award);
    }

    @Transactional
    @Override
    public void deleteByUserIdAndMedalId(Long userId, Long medalId) {
        // Find the award to delete
        findByUserIdAndMedalId(userId, medalId).orElseThrow(AwardNotFoundException::new);

        // Delete the award
        awardRepository.deleteById_UserId_IdAndId_MedalId_Id(userId, medalId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void determineUserMedals(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);

        // Find the user points or return if the has not been earned no point yet
        List<UserPoint> userPoints = user.getUserPoints();
        if (userPoints == null || userPoints.isEmpty()) return;

        // Find my medals
        List<Award> myAwards = this.findByUserId(userId);

        // Find the medals
        List<Medal> medals = medalService.findAll();

        // Medals to awards
        List<Medal> medalsToAward = new CopyOnWriteArrayList<>();

        // Iterate the medals and process their rules
        for (Medal medal : medals) {
            List<MedalRule> medalRules = medalRuleService.findAllByMedalId(medal.getId());
            for (MedalRule medalRule : medalRules) {
                // Route the action type
                switch (medalRule.getActionTypeName().getName()) {
                    case ID_CARD:
                    case ADDRESS:
                    case PHONE:
                        // Check if the user id card, address and phone checks for this medal, if the user has point
                        // for them, then they should be checked
                        getUserPoint(userPoints, medalRule.getActionTypeName())
                                .ifPresent(userPoint -> medalRule.setMatched(userPoint.getPoints() > 0));

                        break;

                    case MEMBER:
                        // Check if the user member checks for this medal, if the user total action is greater
                        // than equal to the minimum required, then it is checked
                        teamMemberService.findTeamMemberByUserIdAndIdCard(userId, user.getIdCard())
                                .ifPresent(teamMember -> medalRule.setMatched(
                                        teamMember.getMemberCount() >= medalRule.getTotal()));

                        break;

                    case LEVEL:
                        // Check if the user level checks for this medal, if the user total action is greater
                        // than equal to the minimum required, then it is checked
                        teamMemberService.findTeamMemberByUserIdAndIdCard(userId, user.getIdCard())
                                .ifPresent(teamMember -> medalRule.setMatched(
                                        teamMember.getLevelCount() >= medalRule.getTotal()));

                        break;

                    case CANDIDATE_SHARE:
                        // Check if the user candidate share checks for this medal, if the user total action is greater
                        // than equal to the minimum required, then it is checked
                        medalRule.setMatched(candidateShareService.countByUserId(userId) >= medalRule.getTotal());

                        break;

                    case CANDIDATE_LIKE:
                        // Check if the user candidate like checks for this medal, if the user total action is greater
                        // than equal to the minimum required, then it is checked
                        medalRule.setMatched(candidateLikeService.countByUserId(userId) >= medalRule.getTotal());

                        break;

                    case CANDIDATE_COMMENT:
                        // Check if the user candidate comment checks for this medal, if the user total action is greater
                        // than equal to the minimum required, then it is checked
                        medalRule.setMatched(candidateCommentService.countByUserId(userId) >= medalRule.getTotal());

                        break;

                    case CANDIDATE_POST_COMMENT:
                        // Check if the user post comment checks for this medal, if the user total action is greater
                        // than equal to the minimum required, then it is checked
                        medalRule.setMatched(postCommentService.countByUserId(userId) >= medalRule.getTotal());

                        break;

                    case EVENT_JOIN:
                        // Check if the user event join checks for this medal, if the user total action is greater
                        // than equal to the minimum required, then it is checked
                        medalRule.setMatched(joinEventService.countByUserId(userId) >= medalRule.getTotal());

                        break;

                    case EVENT_SHARE:
                        // Check if the user event share checks for this medal, if the user total action is greater
                        // than equal to the minimum required, then it is checked
                        medalRule.setMatched(eventShareService.countByUserId(userId) >= medalRule.getTotal());

                        break;

                    case EVENT_LIKE:
                        // Check if the user event like checks for this medal, if the user total action is greater
                        // than equal to the minimum required, then it is checked
                        medalRule.setMatched(eventLikeService.countByUserId(userId) >= medalRule.getTotal());

                        break;

                    case EVENT_COMMENT:
                        // Check if the user event comment checks for this medal, if the user total action is greater
                        // than equal to the minimum required, then it is checked
                        medalRule.setMatched(eventCommentService.countByUserId(userId) >= medalRule.getTotal());

                        break;

                    case NEWS_SHARE:
                        // Check if the user news share checks for this medal, if the user total action is greater
                        // than equal to the minimum required, then it is checked
                        medalRule.setMatched(newsShareService.countByUserId(userId) >= medalRule.getTotal());

                        break;

                    case NEWS_LIKE:
                        // Check if the user news like checks for this medal, if the user total action is greater
                        // than equal to the minimum required, then it is checked
                        medalRule.setMatched(newsLikeService.countByUserId(userId) >= medalRule.getTotal());

                        break;

                    case NEWS_COMMENT:
                        // Check if the user news comment checks for this medal, if the user total action is greater
                        // than equal to the minimum required, then it is checked
                        medalRule.setMatched(newsCommentService.countByUserId(userId) >= medalRule.getTotal());

                        break;

                    case VIDEO_SHARE:
                        // Check if the user video share checks for this medal, if the user total action is greater
                        // than equal to the minimum required, then it is checked
                        medalRule.setMatched(videoShareService.countByUserId(userId) >= medalRule.getTotal());

                        break;

                    case VIDEO_LIKE:
                        // Check if the user video like checks for this medal, if the user total action is greater
                        // than equal to the minimum required, then it is checked
                        medalRule.setMatched(videoLikeService.countByUserId(userId) >= medalRule.getTotal());

                        break;

                    case VIDEO_COMMENT:
                        // Check if the user video comment checks for this medal, if the user total action is greater
                        // than equal to the minimum required, then it is checked
                        medalRule.setMatched(videoCommentService.countByUserId(userId) >= medalRule.getTotal());

                        break;

                    case POINT:
                        // Calculate the user total point and check if it's checked for this medal
                        long points = userPoints.stream().mapToLong(UserPoint::getPoints).sum();
                        medalRule.setMatched(points >= medalRule.getTotal());

                        break;

                    case DONATION:
                        // Count the amount the has been given so far, if the amount is greater equal than the minimum
                        // required by this medal, then we have a match
                        Double amount = candidateDonationService.findByUserId(userId)
                                .stream().mapToDouble(CandidateDonation::getAmount).sum();
                        medalRule.setMatched(amount >= medalRule.getTotal());

                        break;

                    case VOLUNTEER_SCHEDULE:
                        // Calculate the total of hours of this user, if the total is greater equal than the minimum
                        // required by this medal, then we have a match
                        Double hours = volunteerService.findAllSchedulesByUserId(userId)
                                .stream().mapToDouble(schedule ->
                                        Duration.between(schedule.getEndTime(), schedule.getStartTime()).getSeconds()
                                                / 3600D).sum();
                        medalRule.setMatched(hours >= medalRule.getTotal());

                        break;

                    case SHARE:
                        // If any share is found, then we have a match
                        Long shareCount = eventShareService.countByUserId(userId) +
                                newsShareService.countByUserId(userId) + videoShareService.countByUserId(userId) +
                                candidateShareService.countByUserId(userId);

                        medalRule.setMatched(shareCount >= medalRule.getTotal());

                        break;

                    case LIKE:
                        // If any like is found, then we have a match
                        Long likeCount = eventLikeService.countByUserId(userId) +
                                newsLikeService.countByUserId(userId) + videoLikeService.countByUserId(userId) +
                                candidateLikeService.countByUserId(userId);

                        medalRule.setMatched(likeCount >= medalRule.getTotal());

                        break;

                    case COMMENT:
                        // If any comment is found, then we have a match
                        Long commentCount = eventCommentService.countByUserId(userId) +
                                newsCommentService.countByUserId(userId) + videoCommentService.countByUserId(userId) +
                                candidateCommentService.countByUserId(userId) +
                                postCommentService.countByUserId(userId);

                        medalRule.setMatched(commentCount >= medalRule.getTotal());

                        break;
                }
            }

            // If all the rules are checked, the user can have this medal
            if (medalRules.stream().allMatch(MedalRule::isMatched)) {
                medalsToAward.add(medal);
            }
        }

        // Build a list of medals the user has lost
        List<Medal> lostMedals = myAwards.stream()
                .filter(award -> medalsToAward.stream()
                        .noneMatch(medal -> medal.getMedalId().equals(award.getId().getMedalId().getMedalId())))
                .map(award -> award.getId().getMedalId())
                .collect(Collectors.toList());

        // Remove the medals the user already have
        for (Award award : myAwards) {
            for (int index = 0; index < medalsToAward.size(); index++) {
                Medal medal = medalsToAward.get(index);
                if (medal.getMedalId().equals(award.getId().getMedalId().getMedalId())) {
                    //noinspection SuspiciousListRemoveInLoop
                    medalsToAward.remove(index);
                }
            }
        }

        // Remove medals the user has lost
        List<String> lostMedalNames = new ArrayList<>();
        for (Medal medal : lostMedals) {
            logger.info(String.format("Removing medal '%s' for user '%s'.", medal.getName(),
                    user.getFirstName() + " " + user.getLastName()));

            // Track lost medal name
            lostMedalNames.add(medal.getName());

            this.deleteByUserIdAndMedalId(userId, medal.getId());
        }

        // Save medals to award
        List<String> medalsToAwardNames = new ArrayList<>();
        for (Medal medal : medalsToAward) {
            logger.info(String.format("Rewarding medal '%s' to user '%s'.", medal.getName(),
                    user.getFirstName() + " " + user.getLastName()));

            // Track medal to award name
            medalsToAwardNames.add(medal.getName());

            this.save(new Award(user, medal));
        }

        // Notify lost medals
        notifyLostMedals(user, lostMedalNames);

        // Notify medals to awards
        notifyAwardMedals(user, medalsToAwardNames);
    }

    private void notifyAwardMedals(User user, List<String> medalsToAwardNames) {
        if (medalsToAwardNames.size() > 0) {
            Notification awardMedalsNotification = new Notification();
            awardMedalsNotification.setUserId(user);
            awardMedalsNotification.setType(Notification.NOTIFICATION_USER_MEDALS_AWARDED);
            awardMedalsNotification.setTitle("Medals Awarded");
            awardMedalsNotification.setContent("One or more medals have been awarded: "
                    + StringUtils.join(medalsToAwardNames, ", "));

            publisher.publishEvent(awardMedalsNotification);
        }
    }

    private void notifyLostMedals(User user, List<String> lostMedalNames) {
        if (lostMedalNames.size() > 0) {
            Notification lostMedalsNotification = new Notification();
            lostMedalsNotification.setUserId(user);
            lostMedalsNotification.setType(Notification.NOTIFICATION_USER_MEDALS_LOST);
            lostMedalsNotification.setTitle("Medals Lost");
            lostMedalsNotification.setContent("One or more medals have been lost: "
                    + StringUtils.join(lostMedalNames, ", "));

            publisher.publishEvent(lostMedalsNotification);
        }
    }

    private Optional<UserPoint> getUserPoint(List<UserPoint> userPoints, ActionType actionType) {
        return userPoints.stream()
                .filter(userPoint -> userPoint.getId().getActionTypeName().getName().equals(actionType.getName()))
                .findFirst();
    }
}
