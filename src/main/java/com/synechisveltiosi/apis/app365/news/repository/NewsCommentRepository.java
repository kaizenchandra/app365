package com.synechisveltiosi.apis.app365.news.repository;

import com.synechisveltiosi.apis.app365.news.entity.NewsComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsCommentRepository extends JpaRepository<NewsComment, Long> {

    Page<NewsComment> findAllByNewsId_NewsId(String newsId, Pageable pageable);

    long countAllByUserId_IdAndDeletedAtIsNull(Long userId);
}
