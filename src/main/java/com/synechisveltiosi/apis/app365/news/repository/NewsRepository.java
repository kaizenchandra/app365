package com.synechisveltiosi.apis.app365.news.repository;

import com.synechisveltiosi.apis.app365.news.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    Optional<News> findByNewsId(String id);

    Optional<News> findFirstByIdGreaterThanOrderByIdAsc(Long id);
}
