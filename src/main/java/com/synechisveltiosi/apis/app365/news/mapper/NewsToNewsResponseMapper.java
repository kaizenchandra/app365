package com.synechisveltiosi.apis.app365.news.mapper;

import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import com.synechisveltiosi.apis.app365.news.dto.NewsResponse;
import com.synechisveltiosi.apis.app365.news.entity.News;
import org.springframework.stereotype.Component;

@Component
public class NewsToNewsResponseMapper extends AbstractMapper<News, NewsResponse> {

    @Override
    public NewsResponse map(News news) {
        return NewsMapper.INSTANCE.from(news);
    }
}
