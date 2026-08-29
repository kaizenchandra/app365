package com.synechisveltiosi.apis.app365.actions.service;

import com.synechisveltiosi.apis.app365.actions.entity.MedalRule;

import java.util.List;
import java.util.Optional;

public interface MedalRuleService {

    Optional<MedalRule> findById(Long id);

    Optional<MedalRule> findById(String id);

    Optional<MedalRule> findByMedalIdAndActionTypeName(Long medalId, String actionTypeName);

    List<MedalRule> findAllByMedalId(Long medalId);
}
