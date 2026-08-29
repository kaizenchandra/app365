package com.synechisveltiosi.apis.app365.news.service;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.news.entity.NewsComment;
import com.synechisveltiosi.apis.app365.news.repository.NewsCommentRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class NewsCommentServiceImpl implements NewsCommentService {

    private final NewsCommentRepository newsCommentRepository;

    @Autowired
    public NewsCommentServiceImpl(NewsCommentRepository newsCommentRepository) {
        this.newsCommentRepository = newsCommentRepository;
    }

    @Override
    public Page<NewsComment> findAll(String newsId, Pageable pageable) {
        if (StringUtils.isBlank(newsId)) throw new BadRequestException("News id should not be null or blank");

        pageable = pageable == null ? Pageable.unpaged() : pageable;

        return newsCommentRepository.findAllByNewsId_NewsId(newsId, pageable);
    }

    @Override
    public Optional<NewsComment> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Comment id should not be null or 0");

        return newsCommentRepository.findById(id);
    }

    @Override
    public long countByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        return newsCommentRepository.countAllByUserId_IdAndDeletedAtIsNull(userId);
    }

    @Transactional
    @Override
    public NewsComment save(NewsComment newsComment) {
        return newsCommentRepository.save(newsComment);
    }
}
