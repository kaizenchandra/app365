package com.synechisveltiosi.apis.app365.events.service;

import com.synechisveltiosi.apis.app365.events.entity.EventLike;

import java.util.Optional;

public interface EventLikeService {

    Optional<EventLike> findById(Long id);

    Optional<EventLike> findByUserIdAndEventId(Long userId, Long eventId);

    long countByUserId(Long userId);

    EventLike save(EventLike eventLike);

    void deleteByUserIdAndEventId(Long userId, Long eventId);
}
