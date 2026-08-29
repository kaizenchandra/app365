package com.synechisveltiosi.apis.app365.common.auth.service;

import com.synechisveltiosi.apis.app365.common.auth.dto.AccessToken;
import com.synechisveltiosi.apis.app365.common.auth.dto.OAuthUserRequest;
import com.synechisveltiosi.apis.app365.common.auth.dto.OAuthUserResponse;
import com.synechisveltiosi.apis.app365.common.auth.repository.OAuth2Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OAuth2ServiceImpl implements OAuth2Service {

    private final OAuth2Repository oAuth2Repository;

    public OAuth2ServiceImpl(OAuth2Repository oAuth2Repository) {
        this.oAuth2Repository = oAuth2Repository;
    }

    @Override
    public AccessToken authenticate(String username, String password) throws IOException {
        return this.oAuth2Repository.authenticate(username, password);
    }

    @Override
    public OAuthUserResponse createUser(OAuthUserRequest user) throws IOException {
        return this.oAuth2Repository.createUser(user);
    }

    @Override
    public void activeUser(Long userId, Boolean state) throws IOException {
        this.oAuth2Repository.activeUser(userId, state);
    }
}
