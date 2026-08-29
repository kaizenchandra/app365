package com.synechisveltiosi.apis.app365.news.service;

import com.synechisveltiosi.apis.app365.news.entity.NewsLike;

import java.util.Optional;

public interface NewsLikeService {

    Optional<NewsLike> findById(Long id);

    Optional<NewsLike> findByUserIdAndNewsId(Long userId, Long newsId);

    long countByUserId(Long userId);

    NewsLike save(NewsLike newsLike);

    void deleteByUserIdAndNewsId(Long userId, Long newsId);
}
