package com.synechisveltiosi.apis.app365.common.auth.service;

import com.synechisveltiosi.apis.app365.common.auth.dto.AccessToken;
import com.synechisveltiosi.apis.app365.common.auth.dto.OAuthUserRequest;
import com.synechisveltiosi.apis.app365.common.auth.dto.OAuthUserResponse;

import java.io.IOException;

public interface OAuth2Service {

    String GRANT_TYPE_PASSWORD = "password";
    String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";
    String TOKEN_ENDPOINT = "/api/oauth/token";
    String CLIENT_TOKEN_ENDPOINT = "/oauth/token";
    String CHECK_TOKEN_ENDPOINT = "/api/user";
    String CREATE_USER_ENDPOINT = "/client/user";
    String CHANGE_USER_PASSWORD_ENDPOINT = "/client/user/password";
    String RESET_USER_PASSWORD_ENDPOINT = "/api/user/password";
    String USER_LOGOUT_ENDPOINT = "/api/user/logout";

    AccessToken authenticate(String username, String password) throws IOException;

    OAuthUserResponse createUser(OAuthUserRequest user) throws IOException;

    void activeUser(Long userId, Boolean state) throws IOException;
}
