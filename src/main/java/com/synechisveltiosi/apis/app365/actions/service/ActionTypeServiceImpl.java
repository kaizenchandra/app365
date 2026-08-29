package com.synechisveltiosi.apis.app365.actions.service;

import com.synechisveltiosi.apis.app365.actions.entity.ActionType;
import com.synechisveltiosi.apis.app365.actions.repository.ActionTypeRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActionTypeServiceImpl implements ActionTypeService {

    private final ActionTypeRepository actionTypeRepository;

    @Autowired
    public ActionTypeServiceImpl(ActionTypeRepository actionTypeRepository) {
        this.actionTypeRepository = actionTypeRepository;
    }

    @Override
    public List<ActionType> findAll() {
        return actionTypeRepository.findAll();
    }

    @Override
    public Optional<ActionType> findByName(String name) {
        if (StringUtils.isBlank(name)) throw new BadRequestException("Action type name should not be null or blank");

        return actionTypeRepository.findByName(name);
    }
}
