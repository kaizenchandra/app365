package com.synechisveltiosi.apis.app365.events;

import com.synechisveltiosi.apis.app365.events.entity.Event;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EventPredicates {

    public static Predicate joinEventPredicate(Long userId, final EntityManager manager) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);

        return builder.equal(root.join("joinEvents").join("userId").get("id"), userId);
    }
}
