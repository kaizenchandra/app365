package com.synechisveltiosi.apis.app365.actions.service;

import com.synechisveltiosi.apis.app365.actions.entity.PointRule;

import java.util.Optional;

public interface PointRuleService {

    Optional<PointRule> findById(Long id);

    Optional<PointRule> findById(String id);

    Optional<PointRule> findByActionTypeName(String actionTypeName);
}
