package com.synechisveltiosi.apis.app365.actions.service;

import com.synechisveltiosi.apis.app365.actions.entity.Medal;
import com.synechisveltiosi.apis.app365.actions.repository.MedalRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedalServiceImpl implements MedalService {

    private final MedalRepository medalRepository;

    @Autowired
    public MedalServiceImpl(MedalRepository medalRepository) {
        this.medalRepository = medalRepository;
    }

    @Override
    public Page<Medal> findAll(Pageable pageable) {
        pageable = pageable == null ? Pageable.unpaged() : pageable;

        return medalRepository.findAll(pageable);
    }

    @Override
    public List<Medal> findAll() {
        return medalRepository.findAll();
    }

    @Override
    public Optional<Medal> findById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("Medal id should not be null or blank");

        return medalRepository.findByMedalId(id);
    }
}
