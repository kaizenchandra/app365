package com.synechisveltiosi.apis.app365.events.service;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.events.entity.JoinEvent;
import com.synechisveltiosi.apis.app365.events.repository.JoinEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class JoinEventServiceImpl implements JoinEventService {

    private final JoinEventRepository joinEventRepository;

    @Autowired
    public JoinEventServiceImpl(JoinEventRepository joinEventRepository) {
        this.joinEventRepository = joinEventRepository;
    }

    @Override
    public Optional<JoinEvent> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Event id should not be null or 0");

        return joinEventRepository.findById(id);
    }

    @Override
    public Optional<JoinEvent> findByUserIdAndEventId(Long userId, Long eventId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (eventId == null || eventId == 0) throw new BadRequestException("Event id should not be null or 0");

        return joinEventRepository.findByUserId_IdAndEventId_Id(userId, eventId);
    }

    @Override
    public long countByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        return joinEventRepository.countAllByUserId_IdAndJoinedIsTrue(userId);
    }

    @Transactional
    @Override
    public JoinEvent save(JoinEvent joinEvent) {
        return joinEventRepository.save(joinEvent);
    }

    @Transactional
    @Override
    public void deleteByUserIdAndEventId(Long userId, Long eventId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (eventId == null || eventId == 0) throw new BadRequestException("Event id should not be null or 0");

        joinEventRepository.deleteByUserId_IdAndEventId_Id(userId, eventId);
    }
}
