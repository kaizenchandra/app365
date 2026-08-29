package com.synechisveltiosi.apis.app365.news.service;

import com.synechisveltiosi.apis.app365.actions.entity.ActionType;
import com.synechisveltiosi.apis.app365.common.repository.DefaultRsqlRepository;
import com.synechisveltiosi.apis.app365.common.repository.RsqlRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotModifiedException;
import com.synechisveltiosi.apis.app365.news.NewsNotFoundException;
import com.synechisveltiosi.apis.app365.news.entity.News;
import com.synechisveltiosi.apis.app365.news.entity.NewsComment;
import com.synechisveltiosi.apis.app365.news.entity.NewsLike;
import com.synechisveltiosi.apis.app365.news.entity.NewsShare;
import com.synechisveltiosi.apis.app365.news.repository.NewsRepository;
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.synechisveltiosi.apis.app365.users.event.UserActionOccurredEvent;
import com.synechisveltiosi.apis.app365.users.exception.UserNotFoundException;
import com.synechisveltiosi.apis.app365.users.service.UserService;
import cz.jirutka.rsql.parser.RSQLParserException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

    private final NewsRepository newsRepository;
    private final RsqlRepository<News> rsqlRepository;
    private final UserService userService;
    private final NewsLikeService newsLikeService;
    private final NewsShareService newsShareService;
    private final NewsCommentService newsCommentService;
    private final NewsSummaryService newsSummaryService;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, UserService userService, NewsLikeService newsLikeService,
                           NewsShareService newsShareService, NewsCommentService newsCommentService,
                           NewsSummaryService newsSummaryService, ApplicationEventPublisher publisher,
                           EntityManager entityManager) {

        this.newsRepository = newsRepository;
        this.userService = userService;
        this.newsLikeService = newsLikeService;
        this.newsShareService = newsShareService;
        this.newsCommentService = newsCommentService;
        this.newsSummaryService = newsSummaryService;
        this.publisher = publisher;

        rsqlRepository = new DefaultRsqlRepository<>(entityManager, News.class)
                .withAllowedFields(News.SEARCHABLE_FIELDS);
    }

    @Override
    public Page<News> findAll(String query, Pageable pageable) throws RSQLParserException {
        pageable = pageable == null ? Pageable.unpaged() : pageable;

        if (StringUtils.isEmpty(query))
            return newsRepository.findAll(pageable);

        return rsqlRepository.findAll(query, pageable);
    }

    @Override
    public Optional<News> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("News id should not be null or 0");

        return newsRepository.findById(id);
    }

    @Override
    public Optional<News> findById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("News id should not be null or blank");

        return newsRepository.findByNewsId(id);
    }

    @Override
    public Optional<News> findNextFrom(Long id) {
        if (id == null || id == 0) throw new BadRequestException("News id should not be null or 0");

        return newsRepository.findFirstByIdGreaterThanOrderByIdAsc(id);
    }

    @Transactional
    @Override
    public News save(News news) {

        Optional<User> user = userService.findById(news.getUserId().getId());

        if ( ! user.isPresent()) {
            throw new UserNotFoundException();
        }

        news.setUserId(user.get());
        return newsRepository.save(news);
    }

    @Transactional
    @Override
    public void like(String newsId, Long userId) {
        if (StringUtils.isBlank(newsId)) throw new BadRequestException("News id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the news
        Optional<News> newsOptional = findById(newsId);
        if (!newsOptional.isPresent()) throw new NewsNotFoundException();

        // Find the news like to remove
        Optional<NewsLike> newsLikeOptional = newsLikeService.findByUserIdAndNewsId(
                userOptional.get().getId(), newsOptional.get().getId());
        if (newsLikeOptional.isPresent() && (newsLikeOptional.get().getLiked() != null
                && newsLikeOptional.get().getLiked())) {
            throw new NotModifiedException("You might already liked this news.");
        }

        // Create like object
        NewsLike newsLike = new NewsLike();
        if (newsLikeOptional.isPresent()) {
            newsLike = newsLikeOptional.get();
        } else {
            newsLike.setUserId(userOptional.get());
            newsLike.setNewsId(newsOptional.get());
        }

        // Save the like
        try {
            newsLike.setLiked(Boolean.TRUE);
            newsLikeService.save(newsLike);
        } catch (DataIntegrityViolationException ex) {
            throw new NotModifiedException("You might already liked this news.");
        }

        // Increment like count for this news
        newsSummaryService.incrementLike(newsOptional.get());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.NEWS_LIKE));
    }

    @Transactional
    @Override
    public void unlike(String newsId, Long userId) {
        if (StringUtils.isBlank(newsId)) throw new BadRequestException("News id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the news
        Optional<News> newsOptional = findById(newsId);
        if (!newsOptional.isPresent()) throw new NewsNotFoundException();

        // Find the news like to remove
        Optional<NewsLike> newsLikeOptional = newsLikeService.findByUserIdAndNewsId(
                userOptional.get().getId(), newsOptional.get().getId());
        if (!newsLikeOptional.isPresent() || (newsLikeOptional.get().getLiked() == null
                || !newsLikeOptional.get().getLiked())) {
            throw new NotModifiedException("You might not already liked this news.");
        }

        // Remove the like
        NewsLike newsLike = newsLikeOptional.get();
        newsLike.setLiked(Boolean.FALSE);
        newsLikeService.save(newsLike);

        // Decrement like count for this news
        newsSummaryService.decreaseLike(newsOptional.get());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.NEWS_LIKE));
    }

    @Transactional
    @Override
    public void share(String newsId, Long userId) {
        if (StringUtils.isBlank(newsId)) throw new BadRequestException("News id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the news
        Optional<News> newsOptional = findById(newsId);
        if (!newsOptional.isPresent()) throw new NewsNotFoundException();

        // Create share object
        NewsShare newsShare = new NewsShare();
        newsShare.setUserId(userOptional.get());
        newsShare.setNewsId(newsOptional.get());

        // Save the share
        newsShareService.save(newsShare);

        // Increment share count for this news
        newsSummaryService.incrementShare(newsOptional.get());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.NEWS_SHARE));
    }

    @Override
    public Page<NewsComment> findAllComments(String newsId, Pageable pageable) {
        pageable = pageable == null ? Pageable.unpaged() : pageable;

        return newsCommentService.findAll(newsId, pageable);
    }

    @Transactional
    @Override
    public NewsComment saveComment(Long userId, String newsId, NewsComment comment) {
        if (StringUtils.isBlank(newsId)) throw new BadRequestException("News id should not be null or blank");
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find the news
        Optional<News> newsOptional = findById(newsId);
        if (!newsOptional.isPresent()) throw new NewsNotFoundException();

        // Complete comment object
        comment.setNewsId(newsOptional.get());
        comment.setUserId(userOptional.get());

        NewsComment newComment = newsCommentService.save(comment);

        // Increment comment count for this news
        newsSummaryService.incrementComment(newsOptional.get());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.NEWS_COMMENT));

        return newComment;
    }
}
