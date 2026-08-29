package com.synechisveltiosi.apis.app365.common.auth.repository;

import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.common.auth.dto.AccessToken;

import java.io.IOException;

public interface CrmOAuth2Repository {

    /**
     * Authenticate using client_credentials grant type
     *
     * @return
     * @throws IOException
     */
    AccessToken authenticate(Account account) throws IOException;

    /**
     * Authenticate using client_credentials grant type
     *
     * @return
     * @throws IOException
     */
    AccessToken refreshToken(Account account) throws IOException;
}
