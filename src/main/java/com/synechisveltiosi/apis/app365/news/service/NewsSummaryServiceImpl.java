package com.synechisveltiosi.apis.app365.news.service;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotModifiedException;
import com.synechisveltiosi.apis.app365.news.entity.News;
import com.synechisveltiosi.apis.app365.news.entity.NewsSummary;
import com.synechisveltiosi.apis.app365.news.repository.NewsSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class NewsSummaryServiceImpl implements NewsSummaryService {

    private final NewsSummaryRepository newsSummaryRepository;

    @Autowired
    public NewsSummaryServiceImpl(NewsSummaryRepository newsSummaryRepository) {
        this.newsSummaryRepository = newsSummaryRepository;
    }

    @Override
    public Optional<NewsSummary> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("News summary id should not be null or 0");

        return newsSummaryRepository.findById(id);
    }

    @Transactional
    @Override
    public NewsSummary save(NewsSummary newsSummary) {
        return newsSummaryRepository.save(newsSummary);
    }

    @Transactional
    @Override
    public void incrementLike(News news) {
        // Find the summary for this news
        Optional<NewsSummary> newsSummaryOptional = getNewsSummary(news);

        // Increase the like field and persist result
        newsSummaryOptional.ifPresent(newsSummary -> {
            newsSummary.increaseLikeCount();
            newsSummary.setLastLikeAt(new Date());

            // Save the summary
            save(newsSummary);
        });
    }

    @Transactional
    @Override
    public void decreaseLike(News news) {
        // Find the summary for this news
        Optional<NewsSummary> newsSummaryOptional = newsSummaryRepository.findByNewsId_Id(news.getId());

        // If no summary yet, nothing will change
        if (!newsSummaryOptional.isPresent()) throw new NotModifiedException();

        // Decrease the like field and persist result
        newsSummaryOptional.ifPresent(newsSummary -> {
            newsSummary.decreaseLikeCount();

            // Save the summary
            save(newsSummary);
        });
    }

    @Transactional
    @Override
    public void incrementShare(News news) {
        // Find the summary for this news
        Optional<NewsSummary> newsSummaryOptional = getNewsSummary(news);

        // Increase the share field and persist result
        newsSummaryOptional.ifPresent(newsSummary -> {
            newsSummary.increaseShareCount();
            newsSummary.setLastShareAt(new Date());

            // Save the summary
            save(newsSummary);
        });
    }

    @Transactional
    @Override
    public void incrementComment(News news) {
        Optional<NewsSummary> newsSummaryOptional = getNewsSummary(news);

        // Increase the comment field and persist result
        newsSummaryOptional.ifPresent(newsSummary -> {
            newsSummary.increaseCommentCount();
            newsSummary.setLastCommentAt(new Date());

            // Save the summary
            save(newsSummary);
        });
    }

    @Transactional
    @Override
    public void decreaseComment(News news) {
        // Find the summary for this news
        Optional<NewsSummary> newsSummaryOptional = newsSummaryRepository.findByNewsId_Id(news.getId());

        // If no summary yet, nothing will change
        if (!newsSummaryOptional.isPresent()) throw new NotModifiedException();

        // Decrease the comment field and persist result
        newsSummaryOptional.ifPresent(newsSummary -> {
            newsSummary.decreaseCommentCount();

            // Save the summary
            save(newsSummary);
        });
    }

    /**
     * Get or create the first news summary
     *
     * @param news
     * @return
     */
    private Optional<NewsSummary> getNewsSummary(News news) {
        // Find the summary for this news
        Optional<NewsSummary> newsSummaryOptional = newsSummaryRepository.findByNewsId_Id(news.getId());

        // If no summary yet, create one
        if (!newsSummaryOptional.isPresent()) {
            NewsSummary newsSummary = new NewsSummary();
            newsSummary.setNewsId(news);

            // Save this news summary
            newsSummaryOptional = Optional.of(this.save(newsSummary));
        }

        return newsSummaryOptional;
    }
}
