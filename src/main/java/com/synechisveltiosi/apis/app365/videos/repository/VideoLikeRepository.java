package com.synechisveltiosi.apis.app365.videos.repository;

import com.synechisveltiosi.apis.app365.videos.entity.VideoLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoLikeRepository extends JpaRepository<VideoLike, Long> {

    Optional<VideoLike> findByUserId_IdAndVideoId_Id(Long userId, Long videoId);

    long countAllByUserId_IdAndLikedIsTrue(Long userId);

    void deleteByUserId_IdAndVideoId_Id(Long userId, Long videoId);
}
