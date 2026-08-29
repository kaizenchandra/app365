package com.synechisveltiosi.apis.app365.videos.service;

import com.synechisveltiosi.apis.app365.videos.entity.Video;
import com.synechisveltiosi.apis.app365.videos.entity.VideoComment;
import cz.jirutka.rsql.parser.RSQLParserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VideoService {

    Page<Video> findAll(String query, Pageable pageable) throws RSQLParserException;

    Optional<Video> findById(Long id);

    Optional<Video> findById(String id);

    Optional<Video> findNextFrom(Long id);

    Video save(Video video);

    void like(String videoId, Long userId);

    void unlike(String videoId, Long userId);

    void share(String videoId, Long userId);

    Page<VideoComment> findAllComments(String videoId, Pageable pageable);

    VideoComment saveComment(Long userId, String videoId, VideoComment comment);
}
