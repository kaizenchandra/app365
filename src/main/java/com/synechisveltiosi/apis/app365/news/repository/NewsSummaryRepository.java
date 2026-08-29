package com.synechisveltiosi.apis.app365.news.repository;

import com.synechisveltiosi.apis.app365.news.entity.NewsSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsSummaryRepository extends JpaRepository<NewsSummary, Long> {

    Optional<NewsSummary> findByNewsId_Id(Long id);
}
