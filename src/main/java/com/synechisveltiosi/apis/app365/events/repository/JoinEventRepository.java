package com.synechisveltiosi.apis.app365.events.repository;

import com.synechisveltiosi.apis.app365.events.entity.JoinEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JoinEventRepository extends JpaRepository<JoinEvent, Long> {

    Optional<JoinEvent> findByUserId_IdAndEventId_Id(Long userId, Long eventId);

    long countAllByUserId_IdAndJoinedIsTrue(Long userId);

    void deleteByUserId_IdAndEventId_Id(Long userId, Long eventId);
}
