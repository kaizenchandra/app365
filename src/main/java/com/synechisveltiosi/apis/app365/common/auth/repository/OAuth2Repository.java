package com.synechisveltiosi.apis.app365.common.auth.repository;

import com.synechisveltiosi.apis.app365.common.auth.dto.AccessToken;
import com.synechisveltiosi.apis.app365.common.auth.dto.OAuthUserRequest;
import com.synechisveltiosi.apis.app365.common.auth.dto.OAuthUserResponse;

import javax.validation.constraints.NotNull;
import java.io.IOException;

public interface OAuth2Repository {

    /**
     * Authenticate using client_credentials grant type
     *
     * @return
     * @throws IOException
     */
    AccessToken authenticate() throws IOException;

    /**
     * Authenticate using password grant type
     *
     * @param username
     * @param password
     * @return
     * @throws IOException
     */
    AccessToken authenticate(String username, String password) throws IOException;

    /**
     * Create a new user in the OAuth service
     *
     * @param user
     * @return
     * @throws IOException
     */
    OAuthUserResponse createUser(OAuthUserRequest user) throws IOException;

    /**
     * Activate a user in the OAuth service
     *
     * @return
     * @throws IOException
     */
    void activeUser(Long userId, Boolean state) throws IOException;

    /**
     * Change the user password in the OAuth service
     *
     * @return
     * @throws IOException
     */
    void changeUserPassword(Long userId, String oldPassword, String newPassword) throws IOException;

    /**
     * Reset the user password in the OAuth service
     *
     * @return
     * @throws IOException
     */
    void resetUserPassword(Long userId, String password) throws IOException;

    /**
     * User logout
     *
     * @param accessToken
     * @return
     * @throws IOException
     */
    void logout(@NotNull AccessToken accessToken) throws IOException;
}
