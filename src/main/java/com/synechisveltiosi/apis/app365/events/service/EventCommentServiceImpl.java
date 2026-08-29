package com.synechisveltiosi.apis.app365.events.service;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.events.entity.EventComment;
import com.synechisveltiosi.apis.app365.events.repository.EventCommentRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EventCommentServiceImpl implements EventCommentService {

    private final EventCommentRepository eventCommentRepository;

    @Autowired
    public EventCommentServiceImpl(EventCommentRepository eventCommentRepository) {
        this.eventCommentRepository = eventCommentRepository;
    }

    @Override
    public Page<EventComment> findAll(String eventId, Pageable pageable) {
        if (StringUtils.isBlank(eventId)) throw new BadRequestException("Event id should not be null or blank");

        pageable = pageable == null ? Pageable.unpaged() : pageable;

        return eventCommentRepository.findAllByEventId_EventId(eventId, pageable);
    }

    @Override
    public Optional<EventComment> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Comment id should not be null or 0");

        return eventCommentRepository.findById(id);
    }

    @Override
    public long countByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        return eventCommentRepository.countAllByUserId_IdAndDeletedAtIsNull(userId);
    }

    @Transactional
    @Override
    public EventComment save(EventComment eventComment) {
        return eventCommentRepository.save(eventComment);
    }
}
