package com.synechisveltiosi.apis.app365.accounts.service;

import com.synechisveltiosi.apis.app365.accounts.entity.AccountCandidate;

import java.util.Optional;

public interface AccountCandidateService {

    Optional<AccountCandidate> findById(Long id);

    Optional<AccountCandidate> findById(String id);

    Optional<AccountCandidate> findByEmail(String email);

    AccountCandidate save(AccountCandidate candidate);

    void deleteById(String id);
}
