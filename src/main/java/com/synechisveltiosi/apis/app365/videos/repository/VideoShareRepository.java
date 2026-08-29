package com.synechisveltiosi.apis.app365.videos.repository;

import com.synechisveltiosi.apis.app365.videos.entity.VideoShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoShareRepository extends JpaRepository<VideoShare, Long> {

    long countAllByUserId_Id(Long userId);

    void deleteByVideoId_Id(Long videoId);
}
