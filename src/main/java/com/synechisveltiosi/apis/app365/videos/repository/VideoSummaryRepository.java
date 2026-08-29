package com.synechisveltiosi.apis.app365.videos.repository;

import com.synechisveltiosi.apis.app365.videos.entity.VideoSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoSummaryRepository extends JpaRepository<VideoSummary, Long> {

    Optional<VideoSummary> findByVideoId_Id(Long id);
}
