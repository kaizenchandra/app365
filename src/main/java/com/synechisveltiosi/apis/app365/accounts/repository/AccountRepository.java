package com.synechisveltiosi.apis.app365.accounts.repository;

import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountId(String id);

    Optional<Account> findBySubdomain(String subdomain);

    void deleteByAccountId(String id);
}
