package com.synechisveltiosi.apis.app365.events.service;

import com.synechisveltiosi.apis.app365.events.entity.JoinEvent;

import java.util.Optional;

public interface JoinEventService {

    Optional<JoinEvent> findById(Long id);

    Optional<JoinEvent> findByUserIdAndEventId(Long userId, Long eventId);

    long countByUserId(Long userId);

    JoinEvent save(JoinEvent joinEvent);

    void deleteByUserIdAndEventId(Long userId, Long eventId);
}
