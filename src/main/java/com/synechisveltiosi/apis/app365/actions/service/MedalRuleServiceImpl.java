package com.synechisveltiosi.apis.app365.actions.service;

import com.synechisveltiosi.apis.app365.actions.entity.MedalRule;
import com.synechisveltiosi.apis.app365.actions.repository.MedalRuleRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedalRuleServiceImpl implements MedalRuleService {

    private final MedalRuleRepository medalRuleRepository;

    @Autowired
    public MedalRuleServiceImpl(MedalRuleRepository medalRuleRepository) {
        this.medalRuleRepository = medalRuleRepository;
    }

    @Override
    public Optional<MedalRule> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Medal rule id should not be null or 0");

        return medalRuleRepository.findById(id);
    }

    @Override
    public Optional<MedalRule> findById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("Medal rule id should not be null or blank");

        return medalRuleRepository.findByMedalRuleId(id);
    }

    @Override
    public Optional<MedalRule> findByMedalIdAndActionTypeName(Long medalId, String actionTypeName) {
        if (medalId == null || medalId == 0) throw new BadRequestException("Medal id should not be null or 0");
        if (StringUtils.isBlank(actionTypeName))
            throw new BadRequestException("Action type name should not be null or blank");

        return medalRuleRepository.findByMedalId_IdAndActionTypeName_Name(medalId, actionTypeName);
    }

    @Override
    public List<MedalRule> findAllByMedalId(Long medalId) {
        if (medalId == null || medalId == 0) throw new BadRequestException("Medal id should not be null or 0");

        return medalRuleRepository.findAllByMedalId_Id(medalId);
    }
}
