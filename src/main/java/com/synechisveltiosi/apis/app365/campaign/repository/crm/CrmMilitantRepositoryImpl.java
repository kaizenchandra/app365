package com.synechisveltiosi.apis.app365.campaign.repository.crm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.synechisveltiosi.apis.app365.accounts.config.CrmConfig;
import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.accounts.helpers.AccountHelper;
import com.synechisveltiosi.apis.app365.campaign.MilitantNotFoundException;
import com.synechisveltiosi.apis.app365.campaign.dto.Militant;
import com.synechisveltiosi.apis.app365.campaign.dto.MilitantRequest;
import com.synechisveltiosi.apis.app365.common.auth.SessionUtils;
import com.synechisveltiosi.apis.app365.common.auth.dto.AccessToken;
import com.synechisveltiosi.apis.app365.common.auth.repository.CrmOAuth2Repository;
import com.synechisveltiosi.apis.app365.common.dto.id.IdCardRequest;
import com.synechisveltiosi.apis.app365.common.rest.response.crm.ErrorHelper;
import com.synechisveltiosi.apis.app365.config.AppConfig;
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

/**
 * @author Clivens Petit <clivens.petit@centallylabs.com>
 */
@Service
public class CrmMilitantRepositoryImpl implements CrmMilitantRepository {

    private static final Logger logger = LoggerFactory.getLogger(CrmMilitantRepositoryImpl.class);

    private final AppConfig appConfig;
    private final ObjectMapper objectMapper;
    private final CrmOAuth2Repository crmOAuth2Repository;
    private final OkHttpClient okHttpClient;

    @Autowired
    public CrmMilitantRepositoryImpl(AppConfig appConfig, ObjectMapper objectMapper,
                                     CrmOAuth2Repository crmOAuth2Repository, OkHttpClient okHttpClient) {

        this.appConfig = appConfig;
        this.objectMapper = objectMapper;
        this.crmOAuth2Repository = crmOAuth2Repository;
        this.okHttpClient = okHttpClient;
    }

    @Override
    public Militant findMilitant(Account account, String idCard, Militant.SearchType searchType) throws IOException {

        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        String url = appConfig.getCrm().getGetEndpoints().getMilitant();

        Request.Builder requestBuilder = new Request.Builder()
                .url(crmConfig.getBaseUrl() + url)
                .addHeader(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken())
                .addHeader("X-TenantID", crmConfig.getTenantId())
                .addHeader("X-Id-Card", AccountHelper.formatIdCard(idCard, crmConfig))
                .addHeader("X-Search-Type", String.valueOf(searchType.ordinal()));

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
                if (responseBody == null) throw new MilitantNotFoundException();

                String json = responseBody.string();
                if (StringUtils.isBlank(json)) throw new MilitantNotFoundException();

                return objectMapper.readValue(json, Militant.class);

            default:
                throw ErrorHelper.handleExceptions(objectMapper, response);
        }
    }

    @Override
    public void addUserAsMilitant(Account account, IdCardRequest idCardRequest) throws IOException {
        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        idCardRequest.setId(AccountHelper.formatIdCard(idCardRequest.getId(), crmConfig));

        okhttp3.MediaType mediaType = okhttp3.MediaType.parse(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String url = appConfig.getCrm().getPostEndpoints().getUserAsMembers();

        String json = new Gson().toJson(idCardRequest);
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, json);

        Request.Builder requestBuilder = new Request.Builder()
                .url(crmConfig.getBaseUrl() + url)
                .addHeader(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .addHeader("X-TenantID", crmConfig.getTenantId())
                .post(body);

        Response response = okHttpClient.newCall(requestBuilder.build()).execute();

        // Retry request if the access token get expired
        if (response.code() == 401) {
            response = retryRequest(account, requestBuilder);
        }

        // Deserialize request response
        switch (response.code()) {
            case 204:
                return;

            default:
                throw ErrorHelper.handleExceptions(objectMapper, response);
        }
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void addMilitant(Account account, MilitantRequest militantRequest) throws IOException {
        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        if (StringUtils.isNotBlank(militantRequest.getIdCard())) {
            militantRequest.setIdCard(AccountHelper.formatIdCard(militantRequest.getIdCard(), crmConfig));
        }

        okhttp3.MediaType mediaType = okhttp3.MediaType.parse(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String url = appConfig.getCrm().getPostEndpoints().getMilitant();

        String json = new Gson().toJson(militantRequest);
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, json);

        Request.Builder requestBuilder = new Request.Builder()
                .url(crmConfig.getBaseUrl() + url)
                .addHeader(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .addHeader("X-TenantID", crmConfig.getTenantId())
                .addHeader("X-Id-Card", AccountHelper.formatIdCard(SessionUtils.getLoggedUser().getIdCard(), crmConfig))
                .post(body);

        Response response = okHttpClient.newCall(requestBuilder.build()).execute();

        // Retry request if the access token get expired
        if (response.code() == 401) {
            response = retryRequest(account, requestBuilder);
        }

        // Deserialize request response
        switch (response.code()) {
            case 204:
                return;

            default:
                throw ErrorHelper.handleExceptions(objectMapper, response);
        }
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void updateMilitant(Account account, MilitantRequest militantRequest) throws IOException {
        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        if (StringUtils.isNotBlank(militantRequest.getIdCard())) {
            militantRequest.setIdCard(AccountHelper.formatIdCard(militantRequest.getIdCard(), crmConfig));
        }

        okhttp3.MediaType mediaType = okhttp3.MediaType.parse(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String url = appConfig.getCrm().getPutEndpoints().getMilitant();

        String json = new Gson().toJson(militantRequest);
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, json);

        Request.Builder requestBuilder = new Request.Builder()
                .url(crmConfig.getBaseUrl() + url)
                .addHeader(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .addHeader("X-TenantID", crmConfig.getTenantId())
                .put(body);

        Response response = okHttpClient.newCall(requestBuilder.build()).execute();

        // Retry request if the access token get expired
        if (response.code() == 401) {
            response = retryRequest(account, requestBuilder);
        }

        // Deserialize request response
        switch (response.code()) {
            case 204:
                return;

            default:
                throw ErrorHelper.handleExceptions(objectMapper, response);
        }
    }

    @Override
    public void verifyMilitantsEmailAddress(Account account, String email, String idCard) throws IOException {
        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        String url = appConfig.getCrm().getGetEndpoints().getMilitantsEmailAddress();

        Request.Builder requestBuilder = new Request.Builder()
                .url(crmConfig.getBaseUrl() + url)
                .addHeader(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .addHeader("X-TenantID", crmConfig.getTenantId())
                .addHeader("X-Id-Card", AccountHelper.formatIdCard(idCard, crmConfig))
                .addHeader("X-Email", email);

        Response response = okHttpClient.newCall(requestBuilder.build()).execute();

        // Retry request if the access token get expired
        if (response.code() == 401) {
            response = retryRequest(account, requestBuilder);
        }

        // Deserialize request response
        switch (response.code()) {
            case 204:
                return;

            default:
                throw ErrorHelper.handleExceptions(objectMapper, response);
        }
    }

    @Override
    public void verifyMilitantsPhoneNumbers(Account account, String countryID, String type, String phone, String idCard) throws IOException {
        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        String url = appConfig.getCrm().getGetEndpoints().getMilitantsPhoneNumber().replace("{type}", type);

        Request.Builder requestBuilder = new Request.Builder()
                .url(crmConfig.getBaseUrl() + url)
                .addHeader(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .addHeader("X-TenantID", crmConfig.getTenantId())
                .addHeader("X-Id-Card", AccountHelper.formatIdCard(idCard, crmConfig))
                .addHeader("X-CountryID", countryID)
                .addHeader("X-Phone", phone);

        Response response = okHttpClient.newCall(requestBuilder.build()).execute();

        // Retry request if the access token get expired
        if (response.code() == 401) {
            response = retryRequest(account, requestBuilder);
        }

        // Deserialize request response
        switch (response.code()) {
            case 204:
                return;

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
