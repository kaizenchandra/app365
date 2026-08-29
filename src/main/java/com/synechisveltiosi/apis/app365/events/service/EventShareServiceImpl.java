package com.synechisveltiosi.apis.app365.events.service;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.events.entity.EventShare;
import com.synechisveltiosi.apis.app365.events.repository.EventShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EventShareServiceImpl implements EventShareService {

    private final EventShareRepository eventShareRepository;

    @Autowired
    public EventShareServiceImpl(EventShareRepository eventShareRepository) {
        this.eventShareRepository = eventShareRepository;
    }


    @Override
    public Optional<EventShare> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Event share id should not be null or 0");

        return eventShareRepository.findById(id);
    }

    @Override
    public long countByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        return eventShareRepository.countAllByUserId_Id(userId);
    }

    @Transactional
    @Override
    public EventShare save(EventShare eventShare) {
        return eventShareRepository.save(eventShare);
    }
}
