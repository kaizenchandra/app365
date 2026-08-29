package com.synechisveltiosi.apis.app365.actions.service;

import com.synechisveltiosi.apis.app365.actions.entity.PointRule;
import com.synechisveltiosi.apis.app365.actions.repository.PointRuleRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PointRuleServiceImpl implements PointRuleService {

    private final PointRuleRepository pointRuleRepository;

    @Autowired
    public PointRuleServiceImpl(PointRuleRepository pointRuleRepository) {
        this.pointRuleRepository = pointRuleRepository;
    }

    @Override
    public Optional<PointRule> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Point rule id should not be null or 0");

        return pointRuleRepository.findById(id);
    }

    @Override
    public Optional<PointRule> findById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("Point rule id should not be null or blank");

        return pointRuleRepository.findByPointRuleId(id);
    }

    @Override
    public Optional<PointRule> findByActionTypeName(String actionTypeName) {
        if (StringUtils.isBlank(actionTypeName))
            throw new BadRequestException("Action type name should not be null or blank");

        return pointRuleRepository.findByActionTypeName_Name(actionTypeName);
    }
}
