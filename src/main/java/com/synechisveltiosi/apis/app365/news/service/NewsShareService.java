package com.synechisveltiosi.apis.app365.news.service;

import com.synechisveltiosi.apis.app365.news.entity.NewsShare;

import java.util.Optional;

public interface NewsShareService {

    Optional<NewsShare> findById(Long id);

    long countByUserId(Long userId);

    NewsShare save(NewsShare newsShare);
}
