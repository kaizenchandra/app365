package com.synechisveltiosi.apis.app365.actions.repository;

import com.synechisveltiosi.apis.app365.actions.entity.MedalRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedalRuleRepository extends JpaRepository<MedalRule, Long> {

    Optional<MedalRule> findByMedalRuleId(String id);

    Optional<MedalRule> findByMedalId_IdAndActionTypeName_Name(Long medalId, String actionTypeName);

    List<MedalRule> findAllByMedalId_Id(Long medalId);
}
