package com.synechisveltiosi.apis.app365.actions.service;

import com.synechisveltiosi.apis.app365.actions.entity.Medal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MedalService {

    Page<Medal> findAll(Pageable pageable);

    List<Medal> findAll();

    Optional<Medal> findById(String id);
}
