package com.synechisveltiosi.apis.app365.events.repository;

import com.synechisveltiosi.apis.app365.events.entity.EventSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventSummaryRepository extends JpaRepository<EventSummary, Long> {

    Optional<EventSummary> findByEventId_Id(Long id);
}
