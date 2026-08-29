package com.synechisveltiosi.apis.app365.common.repository;

import cz.jirutka.rsql.parser.RSQLParserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.Predicate;
import java.util.List;

public interface RsqlRepository<T> {

    List<T> findAll(String query, Sort sort) throws RSQLParserException;

    List<T> findAll(String query, Predicate predicate, Sort sort) throws RSQLParserException;

    Page<T> findAll(String query, Pageable pageable) throws RSQLParserException;

    Page<T> findAll(String query, Predicate predicate, Pageable pageable) throws RSQLParserException;

    long count(String query);

    long count(String query, Predicate predicate);
}
