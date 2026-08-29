package com.synechisveltiosi.apis.app365.events.service;

import com.synechisveltiosi.apis.app365.calendar.Calendar;
import com.synechisveltiosi.apis.app365.events.entity.Event;
import com.synechisveltiosi.apis.app365.events.entity.EventComment;
import cz.jirutka.rsql.parser.RSQLParserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EventService {

    Page<Event> findAll(String query, Pageable pageable) throws RSQLParserException;

    Page<Event> findAllJoined(Long userId, String query, Pageable pageable) throws RSQLParserException;

    Optional<Event> findById(Long id);

    Optional<Event> findById(String id);

    Optional<Event> findNextFrom(Long id);

    Event save(Event event);

    void join(String eventId, Long userId);

    void unjoin(String eventId, Long userId);

    void like(String eventId, Long userId);

    void unlike(String eventId, Long userId);

    void share(String eventId, Long userId);

    Page<EventComment> findAllComments(String eventId, Pageable pageable);

    EventComment saveComment(Long userId, String eventId, EventComment comment);

    Calendar addToCalendar(Long userId, String eventId, Calendar calendar);

    void removeFromCalendar(Long userId, String eventId);
}
