package com.synechisveltiosi.apis.app365.news.repository;

import com.synechisveltiosi.apis.app365.news.entity.NewsShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsShareRepository extends JpaRepository<NewsShare, Long> {

    long countAllByUserId_Id(Long userId);

    void deleteByNewsId_Id(Long newsId);
}
