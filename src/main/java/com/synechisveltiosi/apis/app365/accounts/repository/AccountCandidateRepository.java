package com.synechisveltiosi.apis.app365.accounts.repository;

import com.synechisveltiosi.apis.app365.accounts.entity.AccountCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountCandidateRepository extends JpaRepository<AccountCandidate, Long> {

    Optional<AccountCandidate> findByCandidateId(String id);

    Optional<AccountCandidate> findByEmail(String email);

    void deleteByCandidateId(String id);
}
