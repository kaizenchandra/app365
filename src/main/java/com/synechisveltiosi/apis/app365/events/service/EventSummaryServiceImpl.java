package com.synechisveltiosi.apis.app365.events.service;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotModifiedException;
import com.synechisveltiosi.apis.app365.events.entity.Event;
import com.synechisveltiosi.apis.app365.events.entity.EventSummary;
import com.synechisveltiosi.apis.app365.events.repository.EventSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class EventSummaryServiceImpl implements EventSummaryService {

    private final EventSummaryRepository eventSummaryRepository;

    @Autowired
    public EventSummaryServiceImpl(EventSummaryRepository eventSummaryRepository) {
        this.eventSummaryRepository = eventSummaryRepository;
    }

    @Override
    public Optional<EventSummary> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Event summary id should not be null or 0");

        return eventSummaryRepository.findById(id);
    }

    @Transactional
    @Override
    public EventSummary save(EventSummary eventSummary) {
        return eventSummaryRepository.save(eventSummary);
    }

    @Transactional
    @Override
    public void incrementJoin(Event event) {
        // Find the summary for this event
        Optional<EventSummary> eventSummaryOptional = getEventSummary(event);

        // Increase the join field and persist result
        eventSummaryOptional.ifPresent(eventSummary -> {
            eventSummary.increaseJoinCount();
            eventSummary.setLastJoinedAt(new Date());

            // Save the summary
            save(eventSummary);
        });
    }

    @Transactional
    @Override
    public void decreaseJoin(Event event) {
        // Find the summary for this event
        Optional<EventSummary> eventSummaryOptional = eventSummaryRepository.findByEventId_Id(event.getId());

        // If no summary yet, nothing will change
        if (!eventSummaryOptional.isPresent()) throw new NotModifiedException();

        // Decrease the join field and persist result
        eventSummaryOptional.ifPresent(eventSummary -> {
            eventSummary.decreaseJoinCount();

            // Save the summary
            save(eventSummary);
        });
    }

    @Transactional
    @Override
    public void incrementLike(Event event) {
        // Find the summary for this event
        Optional<EventSummary> eventSummaryOptional = getEventSummary(event);

        // Increase the like field and persist result
        eventSummaryOptional.ifPresent(eventSummary -> {
            eventSummary.increaseLikeCount();
            eventSummary.setLastLikeAt(new Date());

            // Save the summary
            save(eventSummary);
        });
    }

    @Transactional
    @Override
    public void decreaseLike(Event event) {
        // Find the summary for this event
        Optional<EventSummary> eventSummaryOptional = eventSummaryRepository.findByEventId_Id(event.getId());

        // If no summary yet, nothing will change
        if (!eventSummaryOptional.isPresent()) throw new NotModifiedException();

        // Decrease the like field and persist result
        eventSummaryOptional.ifPresent(eventSummary -> {
            eventSummary.decreaseLikeCount();

            // Save the summary
            save(eventSummary);
        });
    }

    @Transactional
    @Override
    public void incrementShare(Event event) {
        // Find the summary for this event
        Optional<EventSummary> eventSummaryOptional = getEventSummary(event);

        // Increase the share field and persist result
        eventSummaryOptional.ifPresent(eventSummary -> {
            eventSummary.increaseShareCount();
            eventSummary.setLastShareAt(new Date());

            // Save the summary
            save(eventSummary);
        });
    }

    @Transactional
    @Override
    public void incrementComment(Event event) {
        Optional<EventSummary> eventSummaryOptional = getEventSummary(event);

        // Increase the comment field and persist result
        eventSummaryOptional.ifPresent(eventSummary -> {
            eventSummary.increaseCommentCount();
            eventSummary.setLastCommentAt(new Date());

            // Save the summary
            save(eventSummary);
        });
    }

    @Transactional
    @Override
    public void decreaseComment(Event event) {
        // Find the summary for this event
        Optional<EventSummary> eventSummaryOptional = eventSummaryRepository.findByEventId_Id(event.getId());

        // If no summary yet, nothing will change
        if (!eventSummaryOptional.isPresent()) throw new NotModifiedException();

        // Decrease the comment field and persist result
        eventSummaryOptional.ifPresent(eventSummary -> {
            eventSummary.decreaseCommentCount();

            // Save the summary
            save(eventSummary);
        });
    }

    /**
     * Get or create the first event summary
     *
     * @param event
     * @return
     */
    private Optional<EventSummary> getEventSummary(Event event) {
        // Find the summary for this event
        Optional<EventSummary> eventSummaryOptional = eventSummaryRepository.findByEventId_Id(event.getId());

        // If no summary yet, create one
        if (!eventSummaryOptional.isPresent()) {
            EventSummary eventSummary = new EventSummary();
            eventSummary.setEventId(event);

            // Save this event summary
            eventSummaryOptional = Optional.of(this.save(eventSummary));
        }

        return eventSummaryOptional;
    }
}
