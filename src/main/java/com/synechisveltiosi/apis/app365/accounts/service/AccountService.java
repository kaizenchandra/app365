package com.synechisveltiosi.apis.app365.accounts.service;

import com.synechisveltiosi.apis.app365.accounts.config.*;
import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.candidates.entity.Candidate;
import com.synechisveltiosi.apis.app365.common.SocialNetworkProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Page<Account> findAll(Pageable pageable);

    Optional<Account> findById(Long id);

    Optional<Account> findById(String id);

    Optional<Account> findBySubdomain(String subdomain);

    Account save(Account account, Candidate candidate);

    Account update(Account account);

    CrmConfig saveCrmConfig(String accountId, CrmConfig crmConfig);

    void deleteCrmConfig(String accountId);

    MailConfig saveMailConfig(String accountId, MailConfig mailConfig);

    void deleteMailConfig(String accountId);

    SocialTokenConfig findSocialTokenConfig(String accountId, SocialNetworkProvider provider);

    List<SocialTokenConfig> saveSocialTokenConfig(String accountId, SocialTokenConfig socialTokenConfig);

    List<SocialTokenConfig> updateSocialTokenConfig(String accountId, SocialTokenConfig socialTokenConfig);

    void deleteSocialTokenConfig(String accountId, SocialNetworkProvider provider);

    DatabaseConfig findDatabaseConfig(String accountId, String service);

    List<DatabaseConfig> saveDatabaseConfig(String accountId, DatabaseConfig databaseConfig);

    List<DatabaseConfig> updateDatabaseConfig(String accountId, DatabaseConfig databaseConfig);

    void deleteDatabaseConfig(String accountId, String service);

    ApiTokenConfig findApiTokenConfig(String accountId, String service);

    List<ApiTokenConfig> saveApiTokenConfig(String accountId, ApiTokenConfig apiTokenConfig);

    List<ApiTokenConfig> updateApiTokenConfig(String accountId, ApiTokenConfig apiTokenConfig);

    void deleteApiTokenConfig(String accountId, String service);

    void delete(String id);
}
