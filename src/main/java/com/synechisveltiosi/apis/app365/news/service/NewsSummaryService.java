package com.synechisveltiosi.apis.app365.news.service;

import com.synechisveltiosi.apis.app365.news.entity.News;
import com.synechisveltiosi.apis.app365.news.entity.NewsSummary;

import java.util.Optional;

public interface NewsSummaryService {

    Optional<NewsSummary> findById(Long id);

    NewsSummary save(NewsSummary newsSummary);

    void incrementLike(News news);

    void decreaseLike(News news);

    void incrementShare(News news);

    void incrementComment(News news);

    void decreaseComment(News news);
}
