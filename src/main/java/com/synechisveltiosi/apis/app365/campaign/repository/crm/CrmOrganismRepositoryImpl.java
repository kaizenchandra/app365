package com.synechisveltiosi.apis.app365.campaign.repository.crm;

import com.synechisveltiosi.apis.app365.accounts.config.CrmConfig;
import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.campaign.Organism;
import com.synechisveltiosi.apis.app365.common.auth.dto.AccessToken;
import com.synechisveltiosi.apis.app365.common.auth.repository.CrmOAuth2Repository;
import com.synechisveltiosi.apis.app365.common.rest.response.crm.ErrorHelper;
import com.applepolitical.apis.applepolitical365.common.rest.response.exception.*;
import com.synechisveltiosi.apis.app365.config.AppConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class CrmOrganismRepositoryImpl implements CrmOrganismRepository {

    private static final Logger logger = LoggerFactory.getLogger(CrmOrganismRepositoryImpl.class);

    private final AppConfig appConfig;
    private final ObjectMapper objectMapper;
    private final CrmOAuth2Repository crmOAuth2Repository;
    private final OkHttpClient okHttpClient;

    public CrmOrganismRepositoryImpl(AppConfig appConfig, ObjectMapper objectMapper,
                                     CrmOAuth2Repository crmOAuth2Repository, OkHttpClient okHttpClient) {

        this.appConfig = appConfig;
        this.objectMapper = objectMapper;
        this.crmOAuth2Repository = crmOAuth2Repository;
        this.okHttpClient = okHttpClient;
    }

    @Override
    public List<Organism> findAssociatedOrganism(Account account) throws IOException {
        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        String url = appConfig.getCrm().getGetEndpoints().getAssociatedOrganisms();

        Request.Builder requestBuilder = new Request.Builder()
                .url(crmConfig.getBaseUrl() + url)
                .addHeader(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken())
                .addHeader("X-TenantID", crmConfig.getTenantId());

        Response response = okHttpClient.newCall(requestBuilder.build()).execute();

        // Retry request if the access token get expired
        if (response.code() == 401) {
            response = retryRequest(account, requestBuilder);
        }

        // Deserialize request response
        switch (response.code()) {
            case 200:
                // Process the response
                return mapOrganisms(response);

            default:
                throw ErrorHelper.handleExceptions(objectMapper, response);
        }
    }

    @Override
    public List<Organism> findSupportSource(Account account) throws IOException {

        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        String url = appConfig.getCrm().getGetEndpoints().getSupportSource();

        Request.Builder requestBuilder = new Request.Builder()
                .url(crmConfig.getBaseUrl() + url)
                .addHeader(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken())
                .addHeader("X-TenantID", crmConfig.getTenantId());

        Response response = okHttpClient.newCall(requestBuilder.build()).execute();

        // Retry request if the access token get expired
        if (response.code() == 401) {
            response = retryRequest(account, requestBuilder);
        }

        // Deserialize request response
        switch (response.code()) {
            case 200:
                // Process the response
                return mapOrganisms(response);

            default:
                throw ErrorHelper.handleExceptions(objectMapper, response);
        }


    }

    @Override
    public List<Organism> findAssociatedOrganismFromSupportSource(Account account, String id) throws IOException {
        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        String url = appConfig.getCrm().getGetEndpoints().getAssociatedOrganissmBySupportSource().replace("{id}", id);

        Request.Builder requestBuilder = new Request.Builder()
                .url(crmConfig.getBaseUrl() + url)
                .addHeader(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken())
                .addHeader("X-TenantID", crmConfig.getTenantId());

        Response response = okHttpClient.newCall(requestBuilder.build()).execute();

        // Retry request if the access token get expired
        if (response.code() == 401) {
            response = retryRequest(account, requestBuilder);
        }

        // Deserialize request response
        switch (response.code()) {
            case 200:
                // Process the response
                return mapOrganisms(response);

            default:
                throw ErrorHelper.handleExceptions(objectMapper, response);
        }
    }

    private List<Organism> mapOrganisms(Response response) throws IOException {
        ResponseBody responseBody = response.body();
        if (responseBody == null) return Collections.emptyList();

        String json = responseBody.string();
        if (StringUtils.isBlank(json)) return Collections.emptyList();

        return objectMapper.readValue(json, new TypeReference<List<Organism>>() {
        });
    }


    private Response retryRequest(Account account, Request.Builder requestBuilder)
            throws IOException {

        AccessToken accessToken = crmOAuth2Repository.refreshToken(account);

        // Modify request to add new access token
        requestBuilder.header(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken());
        return okHttpClient.newCall(requestBuilder.build()).execute();
    }
}
