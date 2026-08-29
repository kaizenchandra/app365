package com.synechisveltiosi.apis.app365.common.repository;

import com.synechisveltiosi.apis.app365.common.util.domain.RsqlQueryUtils;
import com.synechisveltiosi.apis.app365.common.util.domain.sort.DefaultSort;
import com.github.tennaito.rsql.jpa.JpaCriteriaQueryVisitor;
import com.github.tennaito.rsql.jpa.JpaPredicateVisitor;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.RSQLParserException;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultRsqlRepository<T> implements RsqlRepository<T> {

    private final EntityManager entityManager;
    private final Class<T> domainClass;
    private RSQLVisitor<CriteriaQuery<T>, EntityManager> visitor;
    private List<String> allowedFields = new ArrayList<>();

    public DefaultRsqlRepository(EntityManager entityManager, Class<T> domainClass) {
        Assert.notNull(domainClass, "Domain class cannot be null.");
        Assert.notNull(entityManager, "Entity entityManager cannot be null.");

        this.entityManager = entityManager;
        this.domainClass = domainClass;

        try {
            //noinspection unchecked
            this.visitor = new JpaCriteriaQueryVisitor<>(domainClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public DefaultRsqlRepository(EntityManager entityManager, Class<T> domainClass, List<String> allowedFields) {
        this(entityManager, domainClass);
        this.withAllowedFields(allowedFields);
    }

    public DefaultRsqlRepository<T> withAllowedFields(List<String> allowedFields) {
        Assert.notNull(allowedFields, "Allowed fields cannot be null.");

        this.allowedFields = allowedFields;
        return this;
    }

    @Override
    public List<T> findAll(String query, Predicate predicate, Sort sort) throws RSQLParserException {
        Assert.notNull(query, "Query cannot be null.");
        Assert.notNull(sort, "Sort cannot be null.");

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        Node rootNode = new RSQLParser().parse(query);

        // Validate that all query fields are available
        RsqlQueryUtils.assertAllowedFields(rootNode, allowedFields);

        CriteriaQuery<T> criteriaQuery = rootNode.accept(visitor, entityManager);

        // Create the query
        TypedQuery<T> newQuery = entityManager.createQuery(criteriaQuery);

        // Add where constraint predicate
        if (predicate != null) criteriaQuery.where(predicate);

        // Build orders from the pageable sort
        CriteriaQuery<Long> newCriteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> root = newCriteriaQuery.from(domainClass);
        List<Order> orders = DefaultSort.from(sort, criteriaBuilder, root);
        if (orders.size() > 0) {
            criteriaQuery.orderBy(orders);
        }

        // Execute the query
        return newQuery.getResultList();
    }

    @Override
    public List<T> findAll(String query, Sort sort) throws RSQLParserException {
        return this.findAll(query, null, sort);
    }

    @Override
    public Page<T> findAll(String query, Predicate predicate, Pageable pageable) throws RSQLParserException {
        Assert.notNull(query, "Query cannot be null.");
        Assert.notNull(pageable, "Pageable cannot be null.");

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        Node rootNode = new RSQLParser().parse(query);

        // Validate that all query fields are available
        RsqlQueryUtils.assertAllowedFields(rootNode, allowedFields);

        CriteriaQuery<T> criteriaQuery = rootNode.accept(visitor, entityManager);

        // Add where constraint predicate
        if (predicate != null) criteriaQuery.where(predicate);

        // Create the query
        TypedQuery<T> newQuery = entityManager.createQuery(criteriaQuery);

        // Build orders from the pageable sort
        CriteriaQuery<Long> newCriteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> root = newCriteriaQuery.from(domainClass);
        List<Order> orders = DefaultSort.from(pageable.getSort(), criteriaBuilder, root);
        if (orders.size() > 0) {
            criteriaQuery.orderBy(orders);
        }

        // Set pagination info
        newQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        newQuery.setMaxResults(pageable.getPageSize());

        // Execute the query
        List<T> result = newQuery.getResultList();

        // Build the page
        return new PageImpl<>(result, pageable, count(query, predicate));
    }

    @Override
    public Page<T> findAll(String query, Pageable pageable) throws RSQLParserException {
        return this.findAll(query, null, pageable);
    }

    @Override
    public long count(String query, Predicate predicate) {
        Assert.notNull(query, "Query cannot be null.");

        // Parse a RSQL into a Node
        Node rootNode = new RSQLParser().parse(query);

        // Validate that all query fields are available
        RsqlQueryUtils.assertAllowedFields(rootNode, allowedFields);

        // Create criteria and from
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        From root = criteriaQuery.from(domainClass);

        // Create the JPA Predicate Visitor
        RSQLVisitor<Predicate, EntityManager> visitor = new JpaPredicateVisitor<>().defineRoot(root);

        // Visit the node to retrieve CriteriaQuery
        Predicate notePredicate = rootNode.accept(visitor, entityManager);

        // Add where constraint predicate
        if (predicate != null) notePredicate.getExpressions().addAll(predicate.getExpressions());

        // Build the query
        criteriaQuery.select(criteriaBuilder.count(root));
        criteriaQuery.where(notePredicate);

        // Execute the query
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public long count(String query) {
        return this.count(query, null);
    }
}
