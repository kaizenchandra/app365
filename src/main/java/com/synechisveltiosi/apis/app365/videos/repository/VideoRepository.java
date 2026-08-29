package com.synechisveltiosi.apis.app365.videos.repository;

import com.synechisveltiosi.apis.app365.videos.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    Optional<Video> findByVideoId(String id);

    Optional<Video> findFirstByIdGreaterThanOrderByIdAsc(Long id);
}
