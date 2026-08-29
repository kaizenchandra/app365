package com.synechisveltiosi.apis.app365.accounts.mapper;

import com.synechisveltiosi.apis.app365.accounts.dto.AccountResponse;
import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountToAccountResponseMapper extends AbstractMapper<Account, AccountResponse> {

    @Override
    public AccountResponse map(Account account) {
        return AccountMapper.INSTANCE.from(account);
    }
}
