package com.synechisveltiosi.apis.app365.accounts;

import com.applepolitical.apis.applepolitical365.accounts.config.*;
import com.synechisveltiosi.apis.app365.accounts.config.*;
import com.synechisveltiosi.apis.app365.accounts.dto.AccountRequest;
import com.synechisveltiosi.apis.app365.accounts.dto.AccountResponse;
import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.accounts.exception.AccountNotFoundException;
import com.synechisveltiosi.apis.app365.accounts.mapper.AccountMapper;
import com.synechisveltiosi.apis.app365.accounts.service.AccountService;
import com.synechisveltiosi.apis.app365.candidates.entity.Candidate;
import com.synechisveltiosi.apis.app365.common.SocialNetworkProvider;
import com.synechisveltiosi.apis.app365.common.VisibilityType;
import com.synechisveltiosi.apis.app365.common.dto.id.IdResponse;
import com.synechisveltiosi.apis.app365.common.http.request.HttpHeader;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.UnauthorizedException;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PageResponse;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PageResponseBuilder;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PaginationResponse;
import com.synechisveltiosi.apis.app365.common.util.domain.DefaultPageable;
import com.synechisveltiosi.apis.app365.common.util.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/accounts",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    private final Mapper<Account, AccountResponse> accountResponseMapper;
    private final AccountService accountService;
    private final HttpHeader httpHeader;

    @Autowired
    public AccountController(Mapper<Account, AccountResponse> accountResponseMapper, AccountService accountService,
                             HttpHeader httpHeader) {

        this.accountResponseMapper = accountResponseMapper;
        this.accountService = accountService;
        this.httpHeader = httpHeader;
    }

    @GetMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<PageResponse<AccountResponse>> getAccounts(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {

        // Construct the pageable
        Pageable pageable = DefaultPageable.builder()
                .withPage(page, Account.DEFAULT_PAGE)
                .withSize(size, Account.MAX_PAGE_SIZE)
                .withMaxSize(Account.MAX_PAGE_SIZE)
                .build();

        // Execute the search
        Page<Account> accountPage = accountService.findAll(pageable);

        // Prepare the response
        PageResponse<AccountResponse> pageResponse = PageResponseBuilder.<AccountResponse>builder()
                .withData(accountResponseMapper.map(accountPage.getContent()))
                .withPagination(PaginationResponse.from(accountPage))
                .build();

        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping(value = "/me", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getOne(Authentication authentication) {
        VisibilityType visibilityType = httpHeader.getVisibility();
        if (visibilityType == VisibilityType.PUBLIC) { // Public version me endpoint
            return ResponseEntity.ok(AccountMapper.INSTANCE.toPublicAccountResponse(
                    accountService.findBySubdomain(httpHeader.getTenantId())
                            .orElseThrow(AccountNotFoundException::new)));
        } else if (authentication != null && authentication.isAuthenticated()) { // Secured version of me endpoint
            return ResponseEntity.ok(AccountMapper.INSTANCE.from(
                    accountService.findBySubdomain(httpHeader.getTenantId())
                            .orElseThrow(AccountNotFoundException::new)));
        }

        throw new UnauthorizedException();
    }

    @PostMapping
    public ResponseEntity<IdResponse> saveAccount(@Valid @RequestBody AccountRequest accountRequest) {
        // Map account request object
        Account account = AccountMapper.INSTANCE.from(accountRequest);
        account.setStatus(AccountStatus.ACTIVE);
        account.setVerified(Boolean.TRUE);

        // Map the candidate object
        Candidate candidate = AccountMapper.INSTANCE.toCandidate(accountRequest);

        // Persist the new account
        Account newAccount = accountService.save(account, candidate);

        return ResponseEntity.status(HttpStatus.CREATED).body(new IdResponse(newAccount.getAccountId()));
    }

    @PostMapping(value = "/{id}/configurations/crm")
    public ResponseEntity<Void> saveCrmConfig(
            @NotBlank @PathVariable String id, @Valid @RequestBody CrmConfig crmConfig) {

        accountService.saveCrmConfig(id, crmConfig);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/configurations/crm")
    public ResponseEntity<Void> editCrmConfig(
            @NotBlank @PathVariable String id, @Valid @RequestBody CrmConfig crmConfig) {

        accountService.saveCrmConfig(id, crmConfig);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}/configurations/crm", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> deleteCrmConfig(@NotBlank @PathVariable String id) {

        accountService.deleteCrmConfig(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/configurations/mail")
    public ResponseEntity<Void> saveMailConfig(
            @NotBlank @PathVariable String id, @Valid @RequestBody MailConfig mailConfig) {

        accountService.saveMailConfig(id, mailConfig);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/configurations/mail")
    public ResponseEntity<Void> editMailConfig(
            @NotBlank @PathVariable String id, @Valid @RequestBody MailConfig mailConfig) {

        accountService.saveMailConfig(id, mailConfig);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}/configurations/mail", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> deleteMailConfig(@NotBlank @PathVariable String id) {

        accountService.deleteMailConfig(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/configurations/social/{channel}")
    public ResponseEntity<Void> saveSocialConfig(
            @NotBlank @PathVariable String id,
            @NotNull @PathVariable("channel") SocialNetworkProvider provider,
            @Valid @RequestBody SocialTokenConfig token) {

        // Set provider
        token.setProvider(provider);

        accountService.saveSocialTokenConfig(id, token);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/configurations/social/{channel}")
    public ResponseEntity<Void> editSocialConfig(
            @NotBlank @PathVariable String id,
            @NotNull @PathVariable("channel") SocialNetworkProvider provider,
            @Valid @RequestBody SocialTokenConfig token) {

        // Set provider
        token.setProvider(provider);

        accountService.updateSocialTokenConfig(id, token);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}/configurations/social/{channel}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> deleteSocialConfig(
            @NotBlank @PathVariable String id, @NotNull @PathVariable("channel") SocialNetworkProvider provider) {

        accountService.deleteSocialTokenConfig(id, provider);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/configurations/database/{service}")
    public ResponseEntity<Void> saveDatabaseConfig(
            @NotBlank @PathVariable String id,
            @NotNull @PathVariable String service,
            @Valid @RequestBody DatabaseConfig databaseConfig) {

        // Set service
        databaseConfig.setService(service);

        accountService.saveDatabaseConfig(id, databaseConfig);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/configurations/database/{service}")
    public ResponseEntity<Void> editDatabaseConfig(
            @NotBlank @PathVariable String id,
            @NotNull @PathVariable String service,
            @Valid @RequestBody DatabaseConfig databaseConfig) {

        // Set service
        databaseConfig.setService(service);

        accountService.updateDatabaseConfig(id, databaseConfig);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}/configurations/database/{service}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> deleteDatabaseConfig(
            @NotBlank @PathVariable String id, @NotNull @PathVariable String service) {

        accountService.deleteDatabaseConfig(id, service);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/configurations/apiToken/{service}")
    public ResponseEntity<Void> saveApiTokenConfig(
            @NotBlank @PathVariable String id,
            @NotNull @PathVariable String service,
            @Valid @RequestBody ApiTokenConfig apiTokenConfig) {

        // Set service
        apiTokenConfig.setService(service);

        accountService.saveApiTokenConfig(id, apiTokenConfig);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/configurations/apiToken/{service}")
    public ResponseEntity<Void> editApiTokenConfig(
            @NotBlank @PathVariable String id,
            @NotNull @PathVariable String service,
            @Valid @RequestBody ApiTokenConfig apiTokenConfig) {

        // Set service
        apiTokenConfig.setService(service);

        accountService.updateApiTokenConfig(id, apiTokenConfig);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}/configurations/apiToken/{service}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> deleteApiTokenConfig(
            @NotBlank @PathVariable String id, @NotNull @PathVariable String service) {

        accountService.deleteApiTokenConfig(id, service);

        return ResponseEntity.noContent().build();
    }
}
