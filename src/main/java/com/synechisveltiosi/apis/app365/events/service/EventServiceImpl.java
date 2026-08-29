package com.synechisveltiosi.apis.app365.events.service;

import com.synechisveltiosi.apis.app365.actions.entity.ActionType;
import com.synechisveltiosi.apis.app365.calendar.Calendar;
import com.synechisveltiosi.apis.app365.calendar.CalendarService;
import com.synechisveltiosi.apis.app365.common.repository.DefaultRsqlRepository;
import com.synechisveltiosi.apis.app365.common.repository.RsqlRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotModifiedException;
import com.synechisveltiosi.apis.app365.events.EventNotFoundException;
import com.synechisveltiosi.apis.app365.events.EventPredicates;
import com.applepolitical.apis.applepolitical365.events.entity.*;
import com.synechisveltiosi.apis.app365.events.entity.*;
import com.synechisveltiosi.apis.app365.events.repository.EventRepository;
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.synechisveltiosi.apis.app365.users.event.UserActionOccurredEvent;
import com.synechisveltiosi.apis.app365.users.exception.UserNotFoundException;
import com.synechisveltiosi.apis.app365.users.service.UserService;
import cz.jirutka.rsql.parser.RSQLParserException;
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

import javax.persistence.EntityManager;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    private final EventRepository eventRepository;
    private final RsqlRepository<Event> rsqlRepository;
    private final UserService userService;
    private final JoinEventService joinEventService;
    private final EventLikeService eventLikeService;
    private final EventShareService eventShareService;
    private final CalendarService calendarService;
    private final EventCommentService eventCommentService;
    private final EventSummaryService eventSummaryService;
    private final ApplicationEventPublisher publisher;
    private final EntityManager entityManager;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, UserService userService, JoinEventService joinEventService,
                            EventLikeService eventLikeService, EventShareService eventShareService,
                            CalendarService calendarService, EventCommentService eventCommentService,
                            EventSummaryService eventSummaryService, ApplicationEventPublisher publisher,
                            EntityManager entityManager) {

        this.eventRepository = eventRepository;
        this.userService = userService;
        this.joinEventService = joinEventService;
        this.eventLikeService = eventLikeService;
        this.eventShareService = eventShareService;
        this.calendarService = calendarService;
        this.eventCommentService = eventCommentService;
        this.eventSummaryService = eventSummaryService;
        this.publisher = publisher;
        this.entityManager = entityManager;

        rsqlRepository = new DefaultRsqlRepository<>(entityManager, Event.class)
                .withAllowedFields(Event.SEARCHABLE_FIELDS);
    }

    @Override
    public Page<Event> findAll(String query, Pageable pageable) throws RSQLParserException {
        pageable = pageable == null ? Pageable.unpaged() : pageable;

        if (StringUtils.isEmpty(query))
            return eventRepository.findAll(pageable);

        return rsqlRepository.findAll(query, pageable);
    }

    @Override
    public Page<Event> findAllJoined(Long userId, String query, Pageable pageable) throws RSQLParserException {
        pageable = pageable == null ? Pageable.unpaged() : pageable;

        if (StringUtils.isEmpty(query))
            return eventRepository.findJoinEvents(userId, pageable);

        return rsqlRepository.findAll(query, EventPredicates.joinEventPredicate(userId, entityManager), pageable);
    }

    @Override
    public Optional<Event> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Event id should not be null or 0");

        return eventRepository.findById(id);
    }

    @Override
    public Optional<Event> findById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("Event id should not be null or blank");

        return eventRepository.findByEventId(id);
    }

    @Override
    public Optional<Event> findNextFrom(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Event id should not be null or 0");

        return eventRepository.findFirstByIdGreaterThanOrderByIdAsc(id);
    }

    @Transactional
    @Override
    public Event save(Event event) {

        Optional<User> user = userService.findById(event.getUserId().getId());

        if ( ! user.isPresent()) {
            throw new UserNotFoundException();
        }

        event.setUserId(user.get());

        return eventRepository.save(event);
    }

    @Transactional
    @Override
    public void join(String eventId, Long userId) {
        if (StringUtils.isBlank(eventId)) throw new BadRequestException("Event id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the event
        Optional<Event> eventOptional = findById(eventId);
        if (!eventOptional.isPresent()) throw new EventNotFoundException();

        // Create joinEvent object
        JoinEvent joinEvent = new JoinEvent();
        joinEvent.setJoined(Boolean.TRUE);
        joinEvent.setUserId(userOptional.get());
        joinEvent.setEventId(eventOptional.get());

        // Save the joinEvent
        try {
            joinEventService.save(joinEvent);
        } catch (DataIntegrityViolationException ex) {
            throw new NotModifiedException("You might already joined this event.");
        }

        // Increment join count for this event
        eventSummaryService.incrementJoin(eventOptional.get());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.EVENT_JOIN));
    }

    @Transactional
    @Override
    public void unjoin(String eventId, Long userId) {
        if (StringUtils.isBlank(eventId)) throw new BadRequestException("Event id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the event
        Optional<Event> eventOptional = findById(eventId);
        if (!eventOptional.isPresent()) throw new EventNotFoundException();

        // Find the event like to remove
        Optional<JoinEvent> joinEventOptional = joinEventService.findByUserIdAndEventId(
                userOptional.get().getId(), eventOptional.get().getId());
        if (!joinEventOptional.isPresent()) throw new NotModifiedException("You might not already joined this event.");

        // Remove the join
        joinEventService.deleteByUserIdAndEventId(userOptional.get().getId(), eventOptional.get().getId());

        // Decrement join count for this event
        eventSummaryService.decreaseJoin(eventOptional.get());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.EVENT_JOIN));
    }

    @Transactional
    @Override
    public void like(String eventId, Long userId) {
        if (StringUtils.isBlank(eventId)) throw new BadRequestException("Event id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the event
        Optional<Event> eventOptional = findById(eventId);
        if (!eventOptional.isPresent()) throw new EventNotFoundException();

        // Find the event like to remove
        Optional<EventLike> eventLikeOptional = eventLikeService.findByUserIdAndEventId(
                userOptional.get().getId(), eventOptional.get().getId());
        if (eventLikeOptional.isPresent() && (eventLikeOptional.get().getLiked() != null
                && eventLikeOptional.get().getLiked())) {
            throw new NotModifiedException("You might already liked this event.");
        }

        // Create like object
        EventLike eventLike = new EventLike();
        if (eventLikeOptional.isPresent()) {
            eventLike = eventLikeOptional.get();
        } else {
            eventLike.setUserId(userOptional.get());
            eventLike.setEventId(eventOptional.get());
        }

        // Save the like
        try {
            eventLike.setLiked(Boolean.TRUE);
            eventLikeService.save(eventLike);
        } catch (DataIntegrityViolationException ex) {
            throw new NotModifiedException("You might already liked this event.");
        }

        // Increment like count for this event
        eventSummaryService.incrementLike(eventOptional.get());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.EVENT_LIKE));
    }

    @Transactional
    @Override
    public void unlike(String eventId, Long userId) {
        if (StringUtils.isBlank(eventId)) throw new BadRequestException("Event id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the event
        Optional<Event> eventOptional = findById(eventId);
        if (!eventOptional.isPresent()) throw new EventNotFoundException();

        // Find the event like to remove
        Optional<EventLike> eventLikeOptional = eventLikeService.findByUserIdAndEventId(
                userOptional.get().getId(), eventOptional.get().getId());
        if (!eventLikeOptional.isPresent() || (eventLikeOptional.get().getLiked() == null
                || !eventLikeOptional.get().getLiked())) {
            throw new NotModifiedException("You might not already liked this event.");
        }

        // Remove the like
        EventLike eventLike = eventLikeOptional.get();
        eventLike.setLiked(Boolean.FALSE);
        eventLikeService.save(eventLike);

        // Decrement like count for this event
        eventSummaryService.decreaseLike(eventOptional.get());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.EVENT_LIKE));
    }

    @Transactional
    @Override
    public void share(String eventId, Long userId) {
        if (StringUtils.isBlank(eventId)) throw new BadRequestException("Event id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the event
        Optional<Event> eventOptional = findById(eventId);
        if (!eventOptional.isPresent()) throw new EventNotFoundException();

        // Create share object
        EventShare eventShare = new EventShare();
        eventShare.setUserId(userOptional.get());
        eventShare.setEventId(eventOptional.get());

        // Save the share
        eventShareService.save(eventShare);

        // Increment share count for this event
        eventSummaryService.incrementShare(eventOptional.get());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.EVENT_SHARE));
    }

    @Override
    public Page<EventComment> findAllComments(String eventId, Pageable pageable) {
        pageable = pageable == null ? Pageable.unpaged() : pageable;

        return eventCommentService.findAll(eventId, pageable);
    }

    @Transactional
    @Override
    public EventComment saveComment(Long userId, String eventId, EventComment comment) {
        if (StringUtils.isBlank(eventId)) throw new BadRequestException("Event id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the event
        Optional<Event> eventOptional = findById(eventId);
        if (!eventOptional.isPresent()) throw new EventNotFoundException();

        // Complete comment object
        comment.setEventId(eventOptional.get());
        comment.setUserId(userOptional.get());

        EventComment newComment = eventCommentService.save(comment);

        // Increment comment count for this event
        eventSummaryService.incrementComment(eventOptional.get());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.EVENT_COMMENT));

        return newComment;
    }

    @Transactional
    @Override
    public Calendar addToCalendar(Long userId, String eventId, Calendar calendar) {
        if (StringUtils.isBlank(eventId)) throw new BadRequestException("Event id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the event
        Optional<Event> eventOptional = findById(eventId);
        if (!eventOptional.isPresent()) throw new EventNotFoundException();

        // Complete calendar object
        calendar.setEventId(eventOptional.get());
        calendar.setUserId(userOptional.get());

        // Save the calendar entry
        try {
            return calendarService.save(calendar);
        } catch (DataIntegrityViolationException ex) {
            throw new NotModifiedException("You might already add this event to your calendar.");
        }
    }

    @Transactional
    @Override
    public void removeFromCalendar(Long userId, String eventId) {
        calendarService.deleteByUserIdAndEventId(userId, eventId);
    }
}
