package com.synechisveltiosi.apis.app365.common.auth.repository;

import com.synechisveltiosi.apis.app365.accounts.config.CrmConfig;
import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.common.auth.dto.AccessToken;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotAcceptableException;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.ServiceUnavailableException;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.UnauthorizedException;
import com.synechisveltiosi.apis.app365.config.AppConfig;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Clivens Petit <clivens.petit@centallylabs.com>
 */
@Service
public class CrmOAuth2RepositoryImpl implements CrmOAuth2Repository {

    private static final Logger logger = LoggerFactory.getLogger(CrmOAuth2RepositoryImpl.class);
    private static final Map<String, AccessToken> tokenMap = new ConcurrentHashMap<>();

    private final OkHttpClient okHttpClient;
    private final AppConfig appConfig;

    @Autowired
    public CrmOAuth2RepositoryImpl(OkHttpClient okHttpClient, AppConfig appConfig) {
        this.okHttpClient = okHttpClient;
        this.appConfig = appConfig;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public AccessToken authenticate(Account account) throws IOException {
        // Return cached access token
        AccessToken accessToken = tokenMap.get(account.getAccountId());
        if (accessToken != null) return accessToken;

        // Try to authenticate the user and cached access token
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        String credential = Credentials.basic(crmConfig.getAppId(), crmConfig.getAppSecret());
        FormBody formBody = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .build();

        Request request = new Request.Builder()
                .url(crmConfig.getBaseUrl() + appConfig.getCrm().getPostEndpoints().getToken())
                .post(formBody)
                .addHeader(HttpHeaders.AUTHORIZATION, credential)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        switch (response.code()) {
            case 200:
                // Process the response
                ResponseBody responseBody = response.body();
                if (responseBody == null) throw new BadRequestException("No result was returned for this user.");

                Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
                accessToken = gson.fromJson(responseBody.string(), AccessToken.class);

                // Cache access token
                tokenMap.put(account.getAccountId(), accessToken);

                return accessToken;

            case 401:
                throw new UnauthorizedException();

            case 503:
                throw new ServiceUnavailableException();

            default:
                String message = "Unhandled http code";
                logger.error(message + ". OAuth response: " + response.toString());
                throw new NotAcceptableException(message);
        }
    }

    @Override
    public AccessToken refreshToken(Account account) throws IOException {
        // Remove token from cache
        tokenMap.remove(account.getAccountId());

        return this.authenticate(account);
    }
}
