package com.synechisveltiosi.apis.app365.accounts.mapper;

import com.synechisveltiosi.apis.app365.accounts.dto.AccountRequest;
import com.synechisveltiosi.apis.app365.accounts.dto.AccountResponse;
import com.synechisveltiosi.apis.app365.accounts.dto.PublicAccountResponse;
import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.accounts.entity.AccountCandidate;

public abstract class AccountMapperDecorator implements AccountMapper {

    private final AccountMapper mapper;

    public AccountMapperDecorator(AccountMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Account from(AccountRequest accountRequest) {
        Account account = mapper.from(accountRequest);
        account.setCandidateId(AccountMapper.INSTANCE.toAccountCandidate(accountRequest));

        return account;
    }

    @Override
    public AccountResponse from(Account account) {
        AccountResponse accountResponse = mapper.from(account);

        // Make sure candidate is not null
        AccountCandidate candidate = account.getCandidateId();
        if (candidate != null) {
            accountResponse.setCandidateId(candidate.getCandidateId());
            accountResponse.setFirstName(candidate.getFirstName());
            accountResponse.setLastName(candidate.getLastName());
            accountResponse.setEmail(candidate.getEmail());
            accountResponse.setPhoneCountryCode(candidate.getPhoneCountryCode());
            accountResponse.setPhone(candidate.getPhone());
            accountResponse.setCandidateFor(candidate.getCandidateFor());
        }

        return accountResponse;
    }

    @Override
    public PublicAccountResponse toPublicAccountResponse(Account account) {
        PublicAccountResponse accountResponse = mapper.toPublicAccountResponse(account);

        // Make sure candidate is not null
        AccountCandidate candidate = account.getCandidateId();
        if (candidate != null) {
            accountResponse.setFirstName(candidate.getFirstName());
            accountResponse.setLastName(candidate.getLastName());
            accountResponse.setCandidateFor(candidate.getCandidateFor());
        }

        return accountResponse;
    }
}
