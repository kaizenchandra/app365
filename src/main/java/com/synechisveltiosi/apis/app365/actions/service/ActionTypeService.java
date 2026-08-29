package com.synechisveltiosi.apis.app365.actions.service;

import com.synechisveltiosi.apis.app365.actions.entity.ActionType;

import java.util.List;
import java.util.Optional;

public interface ActionTypeService {

    List<ActionType> findAll();

    Optional<ActionType> findByName(String name);
}
