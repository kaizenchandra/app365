package com.synechisveltiosi.apis.app365.news.service;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.news.entity.NewsLike;
import com.synechisveltiosi.apis.app365.news.repository.NewsLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class NewsLikeServiceImpl implements NewsLikeService {

    private final NewsLikeRepository newsLikeRepository;

    @Autowired
    public NewsLikeServiceImpl(NewsLikeRepository newsLikeRepository) {
        this.newsLikeRepository = newsLikeRepository;
    }

    @Override
    public Optional<NewsLike> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("News like id should not be null or 0");

        return newsLikeRepository.findById(id);
    }

    @Override
    public Optional<NewsLike> findByUserIdAndNewsId(Long userId, Long newsId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (newsId == null || newsId == 0) throw new BadRequestException("News id should not be null or 0");

        return newsLikeRepository.findByUserId_IdAndNewsId_Id(userId, newsId);
    }

    @Override
    public long countByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        return newsLikeRepository.countAllByUserId_IdAndLikedIsTrue(userId);
    }

    @Transactional
    @Override
    public NewsLike save(NewsLike newsLike) {
        return newsLikeRepository.save(newsLike);
    }

    @Transactional
    @Override
    public void deleteByUserIdAndNewsId(Long userId, Long newsId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (newsId == null || newsId == 0) throw new BadRequestException("News id should not be null or 0");

        newsLikeRepository.deleteByUserId_IdAndNewsId_Id(userId, newsId);
    }
}
