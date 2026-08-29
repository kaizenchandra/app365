package com.synechisveltiosi.apis.app365.actions.repository;

import com.synechisveltiosi.apis.app365.actions.entity.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActionTypeRepository extends JpaRepository<ActionType, String> {

    Optional<ActionType> findByName(String name);
}
