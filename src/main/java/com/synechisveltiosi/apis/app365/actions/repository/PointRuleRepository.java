package com.synechisveltiosi.apis.app365.actions.repository;

import com.synechisveltiosi.apis.app365.actions.entity.PointRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointRuleRepository extends JpaRepository<PointRule, Long> {

    Optional<PointRule> findByPointRuleId(String id);

    Optional<PointRule> findByActionTypeName_Name(String actionTypeName);
}
