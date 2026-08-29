package com.synechisveltiosi.apis.app365.accounts.service;

import com.applepolitical.apis.applepolitical365.accounts.config.*;
import com.synechisveltiosi.apis.app365.accounts.config.*;
import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.accounts.entity.AccountCandidate;
import com.synechisveltiosi.apis.app365.accounts.exception.AccountNotFoundException;
import com.synechisveltiosi.apis.app365.accounts.exception.DuplicateAccountException;
import com.synechisveltiosi.apis.app365.accounts.exception.DuplicateTenantException;
import com.synechisveltiosi.apis.app365.accounts.repository.AccountRepository;
import com.synechisveltiosi.apis.app365.candidates.entity.Candidate;
import com.synechisveltiosi.apis.app365.candidates.service.CandidateService;
import com.synechisveltiosi.apis.app365.common.SocialNetworkProvider;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CandidateService candidateService;
    private final AccountCandidateService accountCandidateService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, CandidateService candidateService,
                              AccountCandidateService accountCandidateService) {

        this.accountRepository = accountRepository;
        this.candidateService = candidateService;
        this.accountCandidateService = accountCandidateService;
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        pageable = pageable == null ? Pageable.unpaged() : pageable;

        return accountRepository.findAll(pageable);
    }

    @Override
    public Optional<Account> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Account id should not be null or 0");

        return accountRepository.findById(id);
    }

    @Override
    public Optional<Account> findById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("Account id should not be null or blank");

        return accountRepository.findByAccountId(id);
    }

    @Override
    public Optional<Account> findBySubdomain(String subdomain) {
        if (StringUtils.isBlank(subdomain)) throw new BadRequestException("Subdomain should not be null or blank");

        return accountRepository.findBySubdomain(subdomain);
    }

    @Transactional
    @Override
    public Account save(Account account, Candidate candidate) {
        // Validate the subdomain
        Optional<Account> accountOptional = findBySubdomain(account.getSubdomain());
        if (accountOptional.isPresent()) throw new DuplicateTenantException();

        // Validate the user candidate email address
        Optional<AccountCandidate> candidateOptional = accountCandidateService.findByEmail(candidate.getEmail());
        if (candidateOptional.isPresent()) throw new DuplicateAccountException();

        Account newAccount = accountRepository.save(account);

        // Save the candidate
        candidate.setCandidateId(newAccount.getCandidateId().getCandidateId());
        candidateService.save(candidate);

        return newAccount;
    }

    @Transactional
    @Override
    public Account update(Account account) {
        return accountRepository.save(account);
    }

    @Transactional
    @Override
    public CrmConfig saveCrmConfig(String accountId, CrmConfig crmConfig) {
        // Validate CRM configuration
        if (crmConfig == null) throw new BadRequestException("CRM configuration cannot be null.");

        // Find the account
        Account account = findById(accountId).orElseThrow(AccountNotFoundException::new);

        // Set config
        account.getConfiguration().setCrmConfig(crmConfig);

        // Save and return the updates
        return this.update(account).getConfiguration().getCrmConfig();
    }

    @Transactional
    @Override
    public void deleteCrmConfig(String accountId) {
        // Find the account
        Account account = findById(accountId).orElseThrow(AccountNotFoundException::new);

        // Set config
        account.getConfiguration().setCrmConfig(null);

        // Save changes
        this.update(account);
    }

    @Transactional
    @Override
    public MailConfig saveMailConfig(String accountId, MailConfig mailConfig) {
        // Validate mail configuration
        if (mailConfig == null) throw new BadRequestException("Mail configuration cannot be null.");

        // Find the account
        Account account = findById(accountId).orElseThrow(AccountNotFoundException::new);

        // Set config
        account.getConfiguration().setMailConfig(mailConfig);

        // Save and return the updates
        return this.update(account).getConfiguration().getMailConfig();
    }

    @Transactional
    @Override
    public void deleteMailConfig(String accountId) {
        // Find the account
        Account account = findById(accountId).orElseThrow(AccountNotFoundException::new);

        // Set config
        account.getConfiguration().setMailConfig(null);

        // Save changes
        this.update(account);
    }

    @Override
    public SocialTokenConfig findSocialTokenConfig(String accountId, SocialNetworkProvider provider) {
        // Validate social token provider
        if (provider == null) throw new BadRequestException("Social network provider cannot be null.");

        // Find the account
        Account account = findById(accountId).orElseThrow(AccountNotFoundException::new);

        return account.getConfiguration().getSocialTokenConfigs().stream()
                .filter(socialTokenConfig -> socialTokenConfig.getProvider() == provider)
                .findFirst()
                .orElse(null);
    }

    @Transactional
    @Override
    public List<SocialTokenConfig> saveSocialTokenConfig(String accountId, SocialTokenConfig socialTokenConfig) {
        // Validate social token configuration
        if (socialTokenConfig == null) throw new BadRequestException("Social token configuration cannot be null.");

        // Find the account
        Account account = findById(accountId).orElseThrow(AccountNotFoundException::new);

        // Add the social token configuration
        account.getConfiguration().getSocialTokenConfigs().add(socialTokenConfig);

        // Save and return the updates
        return this.update(account).getConfiguration().getSocialTokenConfigs();
    }

    @Transactional
    @Override
    public List<SocialTokenConfig> updateSocialTokenConfig(String accountId, SocialTokenConfig socialTokenConfig) {
        // Validate social token configuration
        if (socialTokenConfig == null) throw new BadRequestException("Social token configuration cannot be null.");

        // Find the account
        Account account = findById(accountId).orElseThrow(AccountNotFoundException::new);

        // Remove existing token for this provider
        account.getConfiguration().removeSocialTokenConfig(socialTokenConfig.getProvider());

        // Add the social token configuration
        account.getConfiguration().getSocialTokenConfigs().add(socialTokenConfig);

        // Save and return the updates
        return this.update(account).getConfiguration().getSocialTokenConfigs();
    }

    @Transactional
    @Override
    public void deleteSocialTokenConfig(String accountId, SocialNetworkProvider provider) {
        // Validate social token provider
        if (provider == null) throw new BadRequestException("Social network provider cannot be null.");

        // Find the account
        Account account = findById(accountId).orElseThrow(AccountNotFoundException::new);

        // Remove existing token for this provider
        account.getConfiguration().removeSocialTokenConfig(provider);

        // Save changes
        this.update(account);
    }

    @Override
    public DatabaseConfig findDatabaseConfig(String accountId, String service) {
        // Validate database configuration
        if (StringUtils.isBlank(service)) throw new BadRequestException("Service cannot be null.");

        // Find the account
        Account account = findById(accountId).orElseThrow(AccountNotFoundException::new);

        return account.getConfiguration().getDatabaseConfigs().stream()
                .filter(databaseConfig -> Objects.equals(databaseConfig.getService(), service))
                .findFirst()
                .orElse(null);
    }

    @Transactional
    @Override
    public List<DatabaseConfig> saveDatabaseConfig(String accountId, DatabaseConfig databaseConfig) {
        // Validate database configuration
        if (databaseConfig == null) throw new BadRequestException("Database configuration cannot be null.");

        // Find the account
        Account account = findById(accountId).orElseThrow(AccountNotFoundException::new);

        // Add the database configuration
        account.getConfiguration().getDatabaseConfigs().add(databaseConfig);

        // Save and return the updates
        return this.update(account).getConfiguration().getDatabaseConfigs();
    }

    @Transactional
    @Override
    public List<DatabaseConfig> updateDatabaseConfig(String accountId, DatabaseConfig databaseConfig) {
        // Validate database configuration
        if (databaseConfig == null) throw new BadRequestException("Social token configuration cannot be null.");

        // Find the account
        Account account = findById(accountId).orElseThrow(AccountNotFoundException::new);

        // Remove existing configuration for this service
        account.getConfiguration().removeDatabaseConfig(databaseConfig.getService());

        // Add the database configuration
        account.getConfiguration().getDatabaseConfigs().add(databaseConfig);

        // Save and return the updates
        return this.update(account).getConfiguration().getDatabaseConfigs();
    }

    @Transactional
    @Override
    public void deleteDatabaseConfig(String accountId, String service) {
        // Validate database service
        if (StringUtils.isBlank(service)) throw new BadRequestException("Service cannot be null.");

        // Find the account
        Account account = findById(accountId).orElseThrow(AccountNotFoundException::new);

        // Remove existing configuration for this service
        account.getConfiguration().removeDatabaseConfig(service);

        // Save changes
        this.update(account);
    }

    @Override
    public ApiTokenConfig findApiTokenConfig(String accountId, String service) {
        // Validate api token service
        if (StringUtils.isBlank(service)) throw new BadRequestException("Service cannot be null.");

        // Find the account
        Account account = findById(accountId).orElseThrow(AccountNotFoundException::new);

        return account.getConfiguration().getApiTokenConfigs().stream()
                .filter(apiTokenConfig -> Objects.equals(apiTokenConfig.getService(), service))
                .findFirst()
                .orElse(null);
    }

    @Transactional
    @Override
    public List<ApiTokenConfig> saveApiTokenConfig(String accountId, ApiTokenConfig apiTokenConfig) {
        // Validate api token configuration
        if (apiTokenConfig == null) throw new BadRequestException("Api token configuration cannot be null.");

        // Find the account
        Account account = findById(accountId).orElseThrow(AccountNotFoundException::new);

        // Add the api token configuration
        account.getConfiguration().getApiTokenConfigs().add(apiTokenConfig);

        // Save and return the updates
        return this.update(account).getConfiguration().getApiTokenConfigs();
    }

    @Transactional
    @Override
    public List<ApiTokenConfig> updateApiTokenConfig(String accountId, ApiTokenConfig apiTokenConfig) {
        // Validate api token configuration
        if (apiTokenConfig == null) throw new BadRequestException("Api token configuration cannot be null.");

        // Find the account
        Account account = findById(accountId).orElseThrow(AccountNotFoundException::new);

        // Remove existing configuration for this service
        account.getConfiguration().removeApiTokenConfig(apiTokenConfig.getService());

        // Add the api token configuration
        account.getConfiguration().getApiTokenConfigs().add(apiTokenConfig);

        // Save and return the updates
        return this.update(account).getConfiguration().getApiTokenConfigs();
    }

    @Transactional
    @Override
    public void deleteApiTokenConfig(String accountId, String service) {
        // Validate api token service
        if (StringUtils.isBlank(service)) throw new BadRequestException("Service cannot be null.");

        // Find the account
        Account account = findById(accountId).orElseThrow(AccountNotFoundException::new);

        // Remove existing configuration for this service
        account.getConfiguration().removeApiTokenConfig(service);

        // Save changes
        this.update(account);
    }

    @Transactional
    @Override
    public void delete(String id) {
        // Find the account
        Account account = findById(id).orElseThrow(AccountNotFoundException::new);

        // Delete the account
        accountRepository.deleteById(account.getId());
    }
}
