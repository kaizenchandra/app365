package com.synechisveltiosi.apis.app365.events.repository;

import com.synechisveltiosi.apis.app365.events.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e INNER JOIN e.joinEvents j WHERE j.userId.id = :id")
    Page<Event> findJoinEvents(@Param("id") Long id, Pageable pageable);

    Optional<Event> findByEventId(String id);

    Optional<Event> findFirstByIdGreaterThanOrderByIdAsc(Long id);
}
