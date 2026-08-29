package com.synechisveltiosi.apis.app365.events.service;

import com.synechisveltiosi.apis.app365.events.entity.Event;
import com.synechisveltiosi.apis.app365.events.entity.EventSummary;

import java.util.Optional;

public interface EventSummaryService {

    Optional<EventSummary> findById(Long id);

    EventSummary save(EventSummary eventSummary);

    void incrementJoin(Event event);

    void decreaseJoin(Event event);

    void incrementLike(Event event);

    void decreaseLike(Event event);

    void incrementShare(Event event);

    void incrementComment(Event event);

    void decreaseComment(Event event);
}
