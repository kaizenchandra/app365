package com.synechisveltiosi.apis.app365.news.service;

import com.synechisveltiosi.apis.app365.news.entity.NewsComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface NewsCommentService {

    Page<NewsComment> findAll(String newsId, Pageable pageable);

    Optional<NewsComment> findById(Long id);

    long countByUserId(Long userId);

    NewsComment save(NewsComment newsComment);
}
