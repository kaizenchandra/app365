package com.synechisveltiosi.apis.app365.videos.repository;

import com.synechisveltiosi.apis.app365.videos.entity.VideoComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoCommentRepository extends JpaRepository<VideoComment, Long> {

    Page<VideoComment> findAllByVideoId_VideoId(String videoId, Pageable pageable);

    long countAllByUserId_IdAndDeletedAtIsNull(Long userId);
}
