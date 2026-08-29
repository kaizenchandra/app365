package com.synechisveltiosi.apis.app365.events.repository;

import com.synechisveltiosi.apis.app365.events.entity.EventLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventLikeRepository extends JpaRepository<EventLike, Long> {

    Optional<EventLike> findByUserId_IdAndEventId_Id(Long userId, Long eventId);

    long countAllByUserId_IdAndLikedIsTrue(Long userId);

    void deleteByUserId_IdAndEventId_Id(Long userId, Long eventId);
}
