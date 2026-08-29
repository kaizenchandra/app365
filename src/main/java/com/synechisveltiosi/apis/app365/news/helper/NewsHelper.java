package com.synechisveltiosi.apis.app365.news.helper;

import com.synechisveltiosi.apis.app365.common.dto.DefaultMetaResponse;
import com.synechisveltiosi.apis.app365.news.dto.NewsResponse;
import com.synechisveltiosi.apis.app365.news.entity.News;
import com.synechisveltiosi.apis.app365.news.mapper.NewsCommentMapper;
import com.synechisveltiosi.apis.app365.news.mapper.NewsMapper;
import com.synechisveltiosi.apis.app365.users.dto.UserActionMetaResponse;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class NewsHelper {

    /**
     * Inject user meta data
     *
     * @param userId
     * @param news
     * @return
     */
    public static List<NewsResponse> processNewsMetadata(Long userId, List<News> news) {
        return news.stream()
                .map(event -> processNewsMetada(userId, event))
                .collect(Collectors.toList());
    }

    public static NewsResponse processNewsMetada(Long userId, News news) {
        NewsResponse newsResponse = NewsMapper.INSTANCE.from(news);

        // Mark user likes
        markUserLikes(userId, news, newsResponse);

        // Mark user share
        markUserShare(userId, news, newsResponse);

        // Map de last comment
        mapLastComment(news, newsResponse);

        return newsResponse;
    }

    @SuppressWarnings("Duplicates")
    public static List<NewsResponse> nullifyListOnlyFields(List<NewsResponse> newses) {
        return newses.stream()
                .peek(news -> {
                    news.setSummary(null);
                    news.setCursor(null);
                    news.setMeta(null);
                })
                .collect(Collectors.toList());
    }

    @SuppressWarnings("Duplicates")
    private static void markUserShare(Long userId, News news, NewsResponse newsResponse) {
        if (!news.getShares().isEmpty()) {
            // If the user is found that means he shared this event already, add the share flag
            news.getShares().stream()
                    .filter(newsShare -> Objects.equals(newsShare.getUserId().getId(), userId))
                    .forEach(newsShare -> {
                        if (newsResponse.getMeta() == null)
                            newsResponse.setMeta(new DefaultMetaResponse().withUser(new UserActionMetaResponse()));
                        newsResponse.getMeta().getUser().withShared(Boolean.TRUE);
                    });
        }
    }

    @SuppressWarnings("Duplicates")
    private static void markUserLikes(Long userId, News news, NewsResponse newsResponse) {
        if (!news.getLikes().isEmpty()) {
            // If I liked this event already, add the like flag
            news.getLikes().stream()
                    .filter(newsLike -> Objects.equals(newsLike.getUserId().getId(), userId))
                    .forEach(newsLike -> {
                        if (newsResponse.getMeta() == null)
                            newsResponse.setMeta(new DefaultMetaResponse().withUser(new UserActionMetaResponse()));
                        newsResponse.getMeta().getUser().withLiked(newsLike.getLiked());
                    });
        }
    }

    @SuppressWarnings("Duplicates")
    private static void mapLastComment(News news, NewsResponse newsResponse) {
        if (news.getLastComment() != null) {
            if (newsResponse.getMeta() == null)
                newsResponse.setMeta(new DefaultMetaResponse().withUser(new UserActionMetaResponse()));
            newsResponse.getMeta().setLastComment(NewsCommentMapper.INSTANCE.from(news.getLastComment()));
        }
    }
}