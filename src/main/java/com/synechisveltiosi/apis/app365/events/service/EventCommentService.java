package com.synechisveltiosi.apis.app365.events.service;

import com.synechisveltiosi.apis.app365.events.entity.EventComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EventCommentService {

    Page<EventComment> findAll(String eventId, Pageable pageable);

    Optional<EventComment> findById(Long id);

    long countByUserId(Long userId);

    EventComment save(EventComment eventComment);
}
