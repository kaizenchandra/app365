package com.synechisveltiosi.apis.app365.news.service;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.news.entity.NewsShare;
import com.synechisveltiosi.apis.app365.news.repository.NewsShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class NewsShareServiceImpl implements NewsShareService {

    private final NewsShareRepository newsShareRepository;

    @Autowired
    public NewsShareServiceImpl(NewsShareRepository newsShareRepository) {
        this.newsShareRepository = newsShareRepository;
    }

    @Override
    public Optional<NewsShare> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("News share id should not be null or 0");

        return newsShareRepository.findById(id);
    }

    @Override
    public long countByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        return newsShareRepository.countAllByUserId_Id(userId);
    }

    @Transactional
    @Override
    public NewsShare save(NewsShare newsShare) {
        return newsShareRepository.save(newsShare);
    }
}
