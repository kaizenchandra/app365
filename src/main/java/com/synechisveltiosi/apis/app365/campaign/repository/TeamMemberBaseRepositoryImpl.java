package com.synechisveltiosi.apis.app365.campaign.repository;

import com.synechisveltiosi.apis.app365.campaign.entity.TeamLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Component
public class TeamMemberBaseRepositoryImpl implements TeamMemberBaseRepository {

    private final EntityManager entityManager;

    @Autowired
    public TeamMemberBaseRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<TeamLevel> getTeamLevelMembers(Long parentId) {
        StoredProcedureQuery namedStoredProcedureQuery =
                entityManager.createNamedStoredProcedureQuery("getTeamLevelMembers");
        namedStoredProcedureQuery.setParameter("parentId", parentId);

        //noinspection unchecked
        return namedStoredProcedureQuery.getResultList();
    }
}
