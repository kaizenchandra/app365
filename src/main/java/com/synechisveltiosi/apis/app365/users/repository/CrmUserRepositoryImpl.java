package com.synechisveltiosi.apis.app365.users.repository;

import com.synechisveltiosi.apis.app365.accounts.config.CrmConfig;
import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.accounts.helpers.AccountHelper;
import com.synechisveltiosi.apis.app365.common.auth.dto.AccessToken;
import com.synechisveltiosi.apis.app365.common.auth.repository.CrmOAuth2Repository;
import com.synechisveltiosi.apis.app365.common.dto.places.AddressResponse;
import com.synechisveltiosi.apis.app365.common.rest.response.crm.ErrorHelper;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.config.AppConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class CrmUserRepositoryImpl implements CrmUserRepository {

    private static final Logger logger = LoggerFactory.getLogger(CrmUserRepositoryImpl.class);

    private final AppConfig appConfig;
    private final ObjectMapper objectMapper;
    private final CrmOAuth2Repository crmOAuth2Repository;
    private final OkHttpClient okHttpClient;

    @Autowired
    public CrmUserRepositoryImpl(AppConfig appConfig, ObjectMapper objectMapper,
                                 CrmOAuth2Repository crmOAuth2Repository, OkHttpClient okHttpClient) {

        this.appConfig = appConfig;
        this.objectMapper = objectMapper;
        this.crmOAuth2Repository = crmOAuth2Repository;
        this.okHttpClient = okHttpClient;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public AddressResponse findAddress(Account account, String idCard) throws IOException {
        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        String url = appConfig.getCrm().getGetEndpoints().getGetAddress();

        Request.Builder requestBuilder = new Request.Builder()
                .url(crmConfig.getBaseUrl() + url)
                .addHeader(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken())
                .addHeader("X-TenantID", crmConfig.getTenantId())
                .addHeader("X-Id-Card", AccountHelper.formatIdCard(idCard, crmConfig));

        Response response = okHttpClient.newCall(requestBuilder.build()).execute();

        // Retry request if the access token get expired
        if (response.code() == 401) {
            response = retryRequest(account, requestBuilder);
        }

        // Deserialize request response
        switch (response.code()) {
            case 200:
                // Process the response
                ResponseBody responseBody = response.body();
                if (responseBody == null) throw new BadRequestException();

                String json = responseBody.string();
                if (StringUtils.isBlank(json)) throw new BadRequestException();

                return objectMapper.readValue(json, AddressResponse.class);

            case 404:
                return null;

            default:
                throw ErrorHelper.handleExceptions(objectMapper, response);
        }
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void addAddress(Account account, String idCard, Map<String, Object> address) throws IOException {
        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        okhttp3.MediaType mediaType = okhttp3.MediaType.parse(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String url = appConfig.getCrm().getPostEndpoints().getAddAddress();

        String json = new Gson().toJson(address);
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, json);

        Request.Builder requestBuilder = new Request.Builder()
                .url(crmConfig.getBaseUrl() + url)
                .addHeader(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .addHeader("X-TenantID", crmConfig.getTenantId())
                .addHeader("X-Id-Card", AccountHelper.formatIdCard(idCard, crmConfig))
                .post(body);

        Response response = okHttpClient.newCall(requestBuilder.build()).execute();

        // Retry request if the access token get expired
        if (response.code() == 401) {
            response = retryRequest(account, requestBuilder);
        }

        // Deserialize request response
        switch (response.code()) {
            case 204:
                break;

            default:
                throw ErrorHelper.handleExceptions(objectMapper, response);
        }
    }

    private Response retryRequest(Account account, Request.Builder requestBuilder)
            throws IOException {

        AccessToken accessToken = crmOAuth2Repository.refreshToken(account);

        // Modify request to add new access token
        requestBuilder.header(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken());
        return okHttpClient.newCall(requestBuilder.build()).execute();
    }
}
