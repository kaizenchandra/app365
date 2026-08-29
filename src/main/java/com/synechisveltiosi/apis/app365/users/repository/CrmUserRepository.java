package com.synechisveltiosi.apis.app365.users.repository;

import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.common.dto.places.AddressResponse;

import java.io.IOException;
import java.util.Map;

public interface CrmUserRepository {

    AddressResponse findAddress(Account account, String idCard) throws IOException;

    void addAddress(Account account, String idCard, Map<String, Object> address) throws IOException;
}
