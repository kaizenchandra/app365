package com.synechisveltiosi.apis.app365.accounts.mapper;

import com.synechisveltiosi.apis.app365.accounts.dto.AccountRequest;
import com.synechisveltiosi.apis.app365.accounts.dto.AccountResponse;
import com.synechisveltiosi.apis.app365.accounts.dto.PublicAccountResponse;
import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.accounts.entity.AccountCandidate;
import com.synechisveltiosi.apis.app365.candidates.entity.Candidate;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
@DecoratedWith(AccountMapperDecorator.class)
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    Account from(AccountRequest accountRequest);

    Candidate toCandidate(AccountRequest accountRequest);

    AccountCandidate toAccountCandidate(AccountRequest accountRequest);

    @Mappings({
            @Mapping(source = "accountId", target = "id"),
            @Mapping(target = "candidateId", ignore = true)
    })
    AccountResponse from(Account account);

    @Mapping(source = "accountId", target = "id")
    PublicAccountResponse toPublicAccountResponse(Account account);
}
