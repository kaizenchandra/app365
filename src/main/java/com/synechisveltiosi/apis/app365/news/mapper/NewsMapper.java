package com.synechisveltiosi.apis.app365.news.mapper;

import com.synechisveltiosi.apis.app365.news.dto.NewsResponse;
import com.synechisveltiosi.apis.app365.news.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    @Mappings({
            @Mapping(source = "newsId", target = "id"),
            @Mapping(source = "newsSummary", target = "summary")
    })
    NewsResponse from(News news);
}
