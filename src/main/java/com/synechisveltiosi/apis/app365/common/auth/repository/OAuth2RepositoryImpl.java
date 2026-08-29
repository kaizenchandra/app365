package com.synechisveltiosi.apis.app365.common.auth.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synechisveltiosi.apis.app365.common.auth.dto.AccessToken;
import com.synechisveltiosi.apis.app365.common.auth.dto.OAuthUserRequest;
import com.synechisveltiosi.apis.app365.common.auth.dto.OAuthUserResponse;
import com.synechisveltiosi.apis.app365.common.auth.service.OAuth2Service;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.*;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class OAuth2RepositoryImpl implements OAuth2Repository {

    private static final Logger logger = LoggerFactory.getLogger(OAuth2RepositoryImpl.class);

    private final String baseUrl;
    private final String clientId;
    private final String secret;
    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    public OAuth2RepositoryImpl(@Value("${app365.oauth.base-url}") String baseUrl,
                                @Value("${app365.oauth.client-id}") String clientId,
                                @Value("${app365.oauth.client-secret}") String secret,
                                OkHttpClient okHttpClient, ObjectMapper objectMapper) {
        this.baseUrl = baseUrl;
        this.clientId = clientId;
        this.secret = secret;
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public AccessToken authenticate() throws IOException {
        // Build request body
        FormBody body = new FormBody.Builder()
                .add("grant_type", OAuth2Service.GRANT_TYPE_CLIENT_CREDENTIALS)
                .add("client_id", clientId)
                .add("client_secret", secret)
                .add("scope", "")
                .build();

        // Build HTTP request
        Request request = new Request.Builder()
                .url(baseUrl + OAuth2Service.CLIENT_TOKEN_ENDPOINT)
                .addHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .addHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .post(body)
                .build();

        // Execute the request
        Response response = okHttpClient.newCall(request).execute();
        return processOAuth2Response(response);
    }

    @Override
    public AccessToken authenticate(String username, String password) throws IOException {
        // Build request body
        FormBody body = new FormBody.Builder()
                .add("grant_type", OAuth2Service.GRANT_TYPE_PASSWORD)
                .add("client_id", clientId)
                .add("client_secret", secret)
                .add("username", username)
                .add("password", password)
                .add("scope", "")
                .build();

        // Build HTTP request
        Request request = new Request.Builder()
                .url(baseUrl + OAuth2Service.TOKEN_ENDPOINT)
                .addHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .addHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .post(body)
                .build();

        // Execute the request
        Response response = okHttpClient.newCall(request).execute();
        return processOAuth2Response(response);
    }

    @Override
    public OAuthUserResponse createUser(OAuthUserRequest user) throws IOException {

        // Get a new access token for this request
        AccessToken token = authenticate();

        FormBody.Builder builder = new FormBody.Builder()
                .add("email", user.getEmail())
                .add("password", user.getPassword())
                .add("password_confirmation", user.getPassword())
                .add("active", (user.isActive() != null && user.isActive()) ? "1" : "0");

        if (!StringUtils.isEmpty(StringUtils.trim(user.getName())))
            builder.add("name", user.getName());
        else
            builder.add("name", user.getEmail());

        RequestBody formBody = builder.build();

        Request request = new Request.Builder()
                .url(baseUrl + OAuth2Service.CREATE_USER_ENDPOINT)
                .addHeader("Authorization", token.prepareAccessToken())
                .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .addHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .post(formBody)
                .build();

        // Execute the request
        Response response = okHttpClient.newCall(request).execute();
        switch (response.code()) {
            case 201:
                // Process the response
                ResponseBody responseBody = response.body();
                if (responseBody == null) throw new BadRequestException("No result was returned for this user.");

                return objectMapper.readValue(responseBody.string(), OAuthUserResponse.class);

            case 422:
                throw new ConflictException("Duplicated user exception.");

            default:
                String message = "Unable to create user";
                logger.error(message + ". OAuth response: " + response);
                throw new BadRequestException(message);
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void activeUser(Long userId, Boolean state) throws IOException {
        // Get a new access token for this request
        AccessToken token = authenticate();

        Map<String, Object> data = new HashMap<>();
        data.put("active", (state != null && state) ? 1 : 0);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(MediaType.APPLICATION_JSON_VALUE),
                objectMapper.writeValueAsString(data));

        Request request = new Request.Builder()
                .url(baseUrl + OAuth2Service.CREATE_USER_ENDPOINT + "/" + userId)
                .addHeader("Authorization", token.prepareAccessToken())
                .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .addHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .put(body)
                .build();

        // Execute the request
        Response response = okHttpClient.newCall(request).execute();
        switch (response.code()) {
            case 204:
                return;

            default:
                String message = "Unable to active the user.";
                logger.error(message + ". OAuth response: " + response);
                throw new RuntimeException(message);
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void changeUserPassword(Long userId, String oldPassword, String newPassword) throws IOException {
        // Get a new access token for this request
        AccessToken token = authenticate();

        Map<String, Object> data = new HashMap<>();
        data.put("old_password", oldPassword);
        data.put("new_password", newPassword);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(MediaType.APPLICATION_JSON_VALUE),
                objectMapper.writeValueAsString(data));

        Request request = new Request.Builder()
                .url(baseUrl + OAuth2Service.CHANGE_USER_PASSWORD_ENDPOINT + "/" + userId)
                .addHeader("Authorization", token.prepareAccessToken())
                .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .addHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .put(body)
                .build();

        // Execute the request
        Response response = okHttpClient.newCall(request).execute();
        switch (response.code()) {
            case 204:
                return;

            case 422:
                throw new UnauthorizedException();

            default:
                String message = "Unable to change the user password.";
                logger.error(message + ". OAuth response: " + response);
                throw new RuntimeException(message);
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void resetUserPassword(Long userId, String password) throws IOException {
        // Get a new access token for this request
        AccessToken token = authenticate();

        Map<String, Object> data = new HashMap<>();
        data.put("password", password);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(MediaType.APPLICATION_JSON_VALUE),
                objectMapper.writeValueAsString(data));

        Request request = new Request.Builder()
                .url(baseUrl + OAuth2Service.RESET_USER_PASSWORD_ENDPOINT + "/" + userId)
                .addHeader("Authorization", token.prepareAccessToken())
                .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .addHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .post(body)
                .build();

        // Execute the request
        Response response = okHttpClient.newCall(request).execute();
        switch (response.code()) {
            case 204:
                return;

            default:
                String message = "Unable to reset the user password.";
                logger.error(message + ". OAuth response: " + response);
                throw new RuntimeException(message);
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void logout(@NotNull AccessToken accessToken) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + OAuth2Service.USER_LOGOUT_ENDPOINT)
                .addHeader("Authorization", accessToken.prepareAccessToken())
                .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .addHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .get()
                .build();

        // Execute the request
        Response response = okHttpClient.newCall(request).execute();
        switch (response.code()) {
            case 200:
                return;

            case 401:
                throw new UnauthorizedException();

            default:
                String message = "Unable to logout the user.";
                logger.error(message + ". OAuth response: " + response);
                throw new RuntimeException(message);
        }
    }

    @SuppressWarnings("Duplicates")
    private AccessToken processOAuth2Response(Response response) throws IOException {
        switch (response.code()) {
            case 200:
                // Process the response
                ResponseBody responseBody = response.body();
                if (responseBody == null) throw new BadRequestException("No result was returned for this user.");

                return objectMapper.readValue(responseBody.string(), AccessToken.class);

            case 401:
                throw new UnauthorizedException();

            case 503:
                throw new ServiceUnavailableException();

            default:
                String message = "Unhandled http code";
                logger.error(message + ". OAuth response: " + response);
                throw new NotAcceptableException(message);
        }
    }
}
