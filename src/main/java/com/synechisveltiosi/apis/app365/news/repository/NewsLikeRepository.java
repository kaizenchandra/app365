package com.synechisveltiosi.apis.app365.news.repository;

import com.synechisveltiosi.apis.app365.news.entity.NewsLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsLikeRepository extends JpaRepository<NewsLike, Long> {

    Optional<NewsLike> findByUserId_IdAndNewsId_Id(Long userId, Long newsId);

    long countAllByUserId_IdAndLikedIsTrue(Long userId);

    void deleteByUserId_IdAndNewsId_Id(Long userId, Long newsId);
}
