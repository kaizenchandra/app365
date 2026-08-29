package com.synechisveltiosi.apis.app365.events.repository;

import com.synechisveltiosi.apis.app365.events.entity.EventShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventShareRepository extends JpaRepository<EventShare, Long> {

    long countAllByUserId_Id(Long userId);

    void deleteByEventId_Id(Long eventId);
}
