package com.synechisveltiosi.apis.app365.events.service;

import com.synechisveltiosi.apis.app365.events.entity.EventShare;

import java.util.Optional;

public interface EventShareService {

    Optional<EventShare> findById(Long id);

    long countByUserId(Long userId);

    EventShare save(EventShare eventShare);
}
