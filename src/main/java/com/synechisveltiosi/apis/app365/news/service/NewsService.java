package com.synechisveltiosi.apis.app365.news.service;

import com.synechisveltiosi.apis.app365.news.entity.News;
import com.synechisveltiosi.apis.app365.news.entity.NewsComment;
import cz.jirutka.rsql.parser.RSQLParserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface NewsService {

    Page<News> findAll(String query, Pageable pageable) throws RSQLParserException;

    Optional<News> findById(Long id);

    Optional<News> findById(String id);

    Optional<News> findNextFrom(Long id);

    News save(News news);

    void like(String newsId, Long userId);

    void unlike(String newsId, Long userId);

    void share(String newsId, Long userId);

    Page<NewsComment> findAllComments(String newsId, Pageable pageable);

    NewsComment saveComment(Long userId, String newsId, NewsComment comment);
}
