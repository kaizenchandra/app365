package com.synechisveltiosi.apis.app365.events.service;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.events.entity.EventLike;
import com.synechisveltiosi.apis.app365.events.repository.EventLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EventLikeServiceImpl implements EventLikeService {

    private final EventLikeRepository eventLikeRepository;

    @Autowired
    public EventLikeServiceImpl(EventLikeRepository eventLikeRepository) {
        this.eventLikeRepository = eventLikeRepository;
    }


    @Override
    public Optional<EventLike> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Event like id should not be null or 0");

        return eventLikeRepository.findById(id);
    }

    @Override
    public Optional<EventLike> findByUserIdAndEventId(Long userId, Long eventId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (eventId == null || eventId == 0) throw new BadRequestException("Event id should not be null or 0");

        return eventLikeRepository.findByUserId_IdAndEventId_Id(userId, eventId);
    }

    @Override
    public long countByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        return eventLikeRepository.countAllByUserId_IdAndLikedIsTrue(userId);
    }

    @Transactional
    @Override
    public EventLike save(EventLike eventLike) {
        return eventLikeRepository.save(eventLike);
    }

    @Transactional
    @Override
    public void deleteByUserIdAndEventId(Long userId, Long eventId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (eventId == null || eventId == 0) throw new BadRequestException("Event id should not be null or 0");

        eventLikeRepository.deleteByUserId_IdAndEventId_Id(userId, eventId);
    }
}
