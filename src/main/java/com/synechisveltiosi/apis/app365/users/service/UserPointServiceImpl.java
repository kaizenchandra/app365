package com.synechisveltiosi.apis.app365.users.service;

import com.synechisveltiosi.apis.app365.actions.entity.ActionType;
import com.synechisveltiosi.apis.app365.actions.entity.PointRule;
import com.synechisveltiosi.apis.app365.actions.exception.ActionTypeNameNotFoundException;
import com.synechisveltiosi.apis.app365.actions.exception.PointRuleNotFoundException;
import com.synechisveltiosi.apis.app365.actions.service.ActionTypeService;
import com.synechisveltiosi.apis.app365.actions.service.PointRuleService;
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
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.synechisveltiosi.apis.app365.users.entity.UserPoint;
import com.synechisveltiosi.apis.app365.users.exception.UserNotFoundException;
import com.synechisveltiosi.apis.app365.users.repository.UserPointRepository;
import com.synechisveltiosi.apis.app365.videos.service.VideoCommentService;
import com.synechisveltiosi.apis.app365.videos.service.VideoLikeService;
import com.synechisveltiosi.apis.app365.videos.service.VideoShareService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.synechisveltiosi.apis.app365.actions.entity.ActionType.*;

@Service
public class UserPointServiceImpl implements UserPointService {

    private final UserPointRepository userPointRepository;
    private final UserService userService;
    private final ActionTypeService actionTypeService;
    private final PointRuleService pointRuleService;
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

    @Autowired
    public UserPointServiceImpl(
            UserPointRepository userPointRepository, UserService userService, ActionTypeService actionTypeService,
            PointRuleService pointRuleService, TeamMemberService teamMemberService,
            CandidateDonationService candidateDonationService, VolunteerService volunteerService,
            EventShareService eventShareService, JoinEventService joinEventService,
            EventCommentService eventCommentService, EventLikeService eventLikeService,
            NewsShareService newsShareService, NewsCommentService newsCommentService, NewsLikeService newsLikeService,
            VideoShareService videoShareService, VideoCommentService videoCommentService,
            VideoLikeService videoLikeService, CandidateShareService candidateShareService,
            CandidateCommentService candidateCommentService, CandidateLikeService candidateLikeService,
            PostCommentService postCommentService) {

        this.userPointRepository = userPointRepository;
        this.userService = userService;
        this.actionTypeService = actionTypeService;
        this.pointRuleService = pointRuleService;
        this.teamMemberService = teamMemberService;
        this.candidateDonationService = candidateDonationService;
        this.volunteerService = volunteerService;
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
    }

    @Override
    public List<UserPoint> findByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        return userPointRepository.findById_UserId_Id(userId);
    }

    @Override
    public Optional<UserPoint> findByUserIdAndActionTypeName(Long userId, String actionTypeName) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (StringUtils.isBlank(actionTypeName))
            throw new BadRequestException("Action type name should not be null or blank");

        return userPointRepository.findById_UserId_IdAndId_ActionTypeName_Name(userId, actionTypeName);
    }

    @Transactional
    @Override
    public UserPoint save(UserPoint userPoint) {
        return userPointRepository.save(userPoint);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void calculatePoints(Long userId, String actionTypeName) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (StringUtils.isBlank(actionTypeName))
            throw new BadRequestException("Action type name should not be null or blank");

        // Find the user
        User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);

        // Find the action type name
        ActionType actionType = actionTypeService.findByName(actionTypeName)
                .orElseThrow(ActionTypeNameNotFoundException::new);

        // Find the point rules for this action
        PointRule pointRule = pointRuleService.findByActionTypeName(actionTypeName)
                .orElseThrow(PointRuleNotFoundException::new);

        // Find the user point for this action type
        UserPoint point = findByUserIdAndActionTypeName(userId, actionTypeName).orElse(null);

        // No point has been monitored yet for this action type, initialize it
        if (point == null) {
            point = new UserPoint(user, actionType);
        }

        // Route the action type
        switch (actionTypeName) {
            case ID_CARD:
                setUserPointForIdCard(user, point, pointRule);
                break;

            case ADDRESS:
                setUserPointForAddress(user, point, pointRule);
                break;

            case PHONE:
                setUserPointForPhone(user, point, pointRule);
                break;

            case MEMBER:
                setUserPointForMembers(user, point, pointRule);
                break;

            case LEVEL:
                setUserPointForLevels(user, point, pointRule);
                break;

            case DONATION:
                setUserPointForDonation(user, point, pointRule);
                break;

            case VOLUNTEER_SCHEDULE:
                setUserPointForVolunteerSchedule(user, point, pointRule);
                break;

            case CANDIDATE_SHARE:
                setUserPointForCandidateShare(user, point, pointRule);
                break;

            case CANDIDATE_LIKE:
                setUserPointForCandidateLike(user, point, pointRule);
                break;

            case CANDIDATE_COMMENT:
                setUserPointForCandidateComment(user, point, pointRule);
                break;

            case CANDIDATE_POST_COMMENT:
                setUserPointForCandidatePostComment(user, point, pointRule);
                break;

            case EVENT_JOIN:
                setUserPointForEventJoint(user, point, pointRule);
                break;

            case EVENT_SHARE:
                setUserPointForEventShare(user, point, pointRule);
                break;

            case EVENT_LIKE:
                setUserPointForEventLike(user, point, pointRule);
                break;

            case EVENT_COMMENT:
                setUserPointForEventComment(user, point, pointRule);
                break;

            case NEWS_SHARE:
                setUserPointForNewsShare(user, point, pointRule);
                break;

            case NEWS_LIKE:
                setUserPointForNewsLike(user, point, pointRule);
                break;

            case NEWS_COMMENT:
                setUserPointForNewsComment(user, point, pointRule);
                break;

            case VIDEO_SHARE:
                setUserPointForVideoShare(user, point, pointRule);
                break;

            case VIDEO_LIKE:
                setUserPointForVideoLike(user, point, pointRule);
                break;

            case VIDEO_COMMENT:
                setUserPointForVideoComment(user, point, pointRule);
                break;

            default:
                throw new IllegalArgumentException("Invalid action type.");
        }

        // Save the new points
        this.save(point);
    }

    private void setUserPointForIdCard(User user, UserPoint point, PointRule pointRule) {
        if (StringUtils.isBlank(user.getIdCard())) return;

        // Set the points
        point.setPoints(pointRule.getPoints());
    }

    private void setUserPointForAddress(User user, UserPoint point, PointRule pointRule) {
        if (user.getAddress() == null) return;

        // Set the points
        point.setPoints(pointRule.getPoints());
    }

    private void setUserPointForPhone(User user, UserPoint point, PointRule pointRule) {
        // Validate the phone number
        if (user.getPhone() != null && Pattern.matches("^[0-9]{4,15}$", user.getPhone())
                && user.isPhoneVerified() != null && user.isPhoneVerified()) {

            // Set the points
            point.setPoints(pointRule.getPoints());
        }
    }

    private void setUserPointForMembers(User user, UserPoint point, PointRule pointRule) {
        // Find the team member and calculate the member points
        teamMemberService.findTeamMemberByUserIdAndIdCard(user.getId(), user.getIdCard())
                .ifPresent(extendedTeamMember -> point.setPoints(
                        (extendedTeamMember.getMemberCount() / pointRule.getFrequency().longValue())
                                * pointRule.getPoints()));
    }

    private void setUserPointForLevels(User user, UserPoint point, PointRule pointRule) {
        // Find the team member and calculate the level points
        teamMemberService.findTeamMemberByUserIdAndIdCard(user.getId(), user.getIdCard())
                .ifPresent(extendedTeamMember -> point.setPoints(
                        (extendedTeamMember.getLevelCount() / pointRule.getFrequency().longValue())
                                * pointRule.getPoints()));
    }

    private void setUserPointForDonation(User user, UserPoint point, PointRule pointRule) {
        // Find the user donations and calculate the points
        Double total = candidateDonationService.findByUserId(user.getId()).stream()
                .mapToDouble(CandidateDonation::getAmount).sum();
        Double newPoints = (total / pointRule.getFrequency()) * pointRule.getPoints();
        if (newPoints > 0) {
            point.setPoints(newPoints.longValue());
        }
    }

    private void setUserPointForVolunteerSchedule(User user, UserPoint point, PointRule pointRule) {
        // Find the user volunteered schedules and calculate the points
        Double total = volunteerService.findAllSchedulesByUserId(user.getId()).stream()
                .mapToDouble(schedule ->
                        Duration.between(schedule.getEndTime(), schedule.getStartTime()).getSeconds() / 3600D).sum();
        if (total > 0) {
            point.setPoints(total.longValue());
        }
    }

    private void setUserPointForEventJoint(User user, UserPoint point, PointRule pointRule) {
        // Find the user join events count and calculate the points
        point.setPoints((joinEventService.countByUserId(user.getId()) / pointRule.getFrequency().longValue())
                * pointRule.getPoints());
    }

    private void setUserPointForEventShare(User user, UserPoint point, PointRule pointRule) {
        // Find the user event share count and calculate the points
        point.setPoints((eventShareService.countByUserId(user.getId()) / pointRule.getFrequency().longValue())
                * pointRule.getPoints());
    }

    private void setUserPointForEventLike(User user, UserPoint point, PointRule pointRule) {
        // Find the user event like count and calculate the points
        point.setPoints((eventLikeService.countByUserId(user.getId()) / pointRule.getFrequency().longValue())
                * pointRule.getPoints());
    }

    private void setUserPointForEventComment(User user, UserPoint point, PointRule pointRule) {
        // Find the user event comment count and calculate the points
        point.setPoints((eventCommentService.countByUserId(user.getId()) / pointRule.getFrequency().longValue())
                * pointRule.getPoints());
    }

    private void setUserPointForNewsShare(User user, UserPoint point, PointRule pointRule) {
        // Find the user news share count and calculate the points
        point.setPoints((newsShareService.countByUserId(user.getId()) / pointRule.getFrequency().longValue())
                * pointRule.getPoints());
    }

    private void setUserPointForNewsLike(User user, UserPoint point, PointRule pointRule) {
        // Find the user news like count and calculate the points
        point.setPoints((newsLikeService.countByUserId(user.getId()) / pointRule.getFrequency().longValue())
                * pointRule.getPoints());
    }

    private void setUserPointForNewsComment(User user, UserPoint point, PointRule pointRule) {
        // Find the user news comment count and calculate the points
        point.setPoints((newsCommentService.countByUserId(user.getId()) / pointRule.getFrequency().longValue())
                * pointRule.getPoints());
    }

    private void setUserPointForVideoShare(User user, UserPoint point, PointRule pointRule) {
        // Find the user video share count and calculate the points
        point.setPoints((videoShareService.countByUserId(user.getId()) / pointRule.getFrequency().longValue())
                * pointRule.getPoints());
    }

    private void setUserPointForVideoLike(User user, UserPoint point, PointRule pointRule) {
        // Find the user video like count and calculate the points
        point.setPoints((videoLikeService.countByUserId(user.getId()) / pointRule.getFrequency().longValue())
                * pointRule.getPoints());
    }

    private void setUserPointForVideoComment(User user, UserPoint point, PointRule pointRule) {
        // Find the user video comment count and calculate the points
        point.setPoints((videoCommentService.countByUserId(user.getId()) / pointRule.getFrequency().longValue())
                * pointRule.getPoints());
    }

    private void setUserPointForCandidateShare(User user, UserPoint point, PointRule pointRule) {
        // Find the user candidate share count and calculate the points
        point.setPoints((candidateShareService.countByUserId(user.getId()) / pointRule.getFrequency().longValue())
                * pointRule.getPoints());
    }

    private void setUserPointForCandidateLike(User user, UserPoint point, PointRule pointRule) {
        // Find the user candidate like count and calculate the points
        point.setPoints((candidateLikeService.countByUserId(user.getId()) / pointRule.getFrequency().longValue())
                * pointRule.getPoints());
    }

    private void setUserPointForCandidateComment(User user, UserPoint point, PointRule pointRule) {
        // Find the user candidate comment count and calculate the points
        point.setPoints((candidateCommentService.countByUserId(user.getId()) / pointRule.getFrequency().longValue())
                * pointRule.getPoints());
    }

    private void setUserPointForCandidatePostComment(User user, UserPoint point, PointRule pointRule) {
        // Find the user candidate post comment count and calculate the points
        point.setPoints((postCommentService.countByUserId(user.getId()) / pointRule.getFrequency().longValue())
                * pointRule.getPoints());
    }
}
