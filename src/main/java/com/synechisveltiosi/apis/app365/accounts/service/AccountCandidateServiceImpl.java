package com.synechisveltiosi.apis.app365.accounts.service;

import com.synechisveltiosi.apis.app365.accounts.entity.AccountCandidate;
import com.synechisveltiosi.apis.app365.accounts.repository.AccountCandidateRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountCandidateServiceImpl implements AccountCandidateService {

    private final AccountCandidateRepository accountCandidateRepository;

    @Autowired
    public AccountCandidateServiceImpl(AccountCandidateRepository accountCandidateRepository) {
        this.accountCandidateRepository = accountCandidateRepository;
    }

    @Override
    public Optional<AccountCandidate> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Account candidate id should not be null or 0");

        return accountCandidateRepository.findById(id);
    }

    @Override
    public Optional<AccountCandidate> findById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("Account candidate id should not be null or blank");

        return accountCandidateRepository.findByCandidateId(id);
    }

    @Override
    public Optional<AccountCandidate> findByEmail(String email) {
        if (StringUtils.isBlank(email)) throw new BadRequestException("Email should not be null or blank");

        return accountCandidateRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public AccountCandidate save(AccountCandidate candidate) {
        return accountCandidateRepository.save(candidate);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        // Find candidate by id and delete the record
        findById(id).ifPresent(accountCandidate -> accountCandidateRepository.deleteByCandidateId(id));
    }
}
