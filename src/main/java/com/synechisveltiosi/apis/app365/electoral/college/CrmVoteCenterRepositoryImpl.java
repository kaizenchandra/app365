package com.synechisveltiosi.apis.app365.electoral.college;

import com.synechisveltiosi.apis.app365.accounts.config.CrmConfig;
import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.accounts.helpers.AccountHelper;
import com.synechisveltiosi.apis.app365.common.auth.dto.AccessToken;
import com.synechisveltiosi.apis.app365.common.auth.repository.CrmOAuth2Repository;
import com.applepolitical.apis.applepolitical365.common.rest.response.exception.*;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.*;
import com.synechisveltiosi.apis.app365.config.AppConfig;
import com.synechisveltiosi.apis.app365.electoral.college.dto.VoteCenterResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CrmVoteCenterRepositoryImpl implements CrmVoteCenterRepository {

    private static final Logger logger = LoggerFactory.getLogger(CrmVoteCenterRepositoryImpl.class);

    private final AppConfig appConfig;
    private final ObjectMapper objectMapper;
    private final CrmOAuth2Repository crmOAuth2Repository;
    private final OkHttpClient okHttpClient;

    @Autowired
    public CrmVoteCenterRepositoryImpl(AppConfig appConfig, ObjectMapper objectMapper,
                                       CrmOAuth2Repository crmOAuth2Repository, OkHttpClient okHttpClient) {

        this.appConfig = appConfig;
        this.objectMapper = objectMapper;
        this.crmOAuth2Repository = crmOAuth2Repository;
        this.okHttpClient = okHttpClient;
    }

    @Override
    public VoteCenterResponse findFirstCollege(Account account, String idCard) throws IOException {
        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        String url = appConfig.getCrm().getGetEndpoints().getElectoralCollege();

        Request.Builder requestBuilder = new Request.Builder()
                .url(crmConfig.getBaseUrl() + url)
                .addHeader(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken())
                .addHeader("X-TenantID", crmConfig.getTenantId())
                .addHeader("X-Id-Card", AccountHelper.formatIdCard(idCard, crmConfig));

        Response response = okHttpClient.newCall(requestBuilder.build()).execute();

        // Retry request if the access token get expired
        if (response.code() == 401) {
            response = retryRequest(account, accessToken, requestBuilder);
        }

        // Deserialize request response
        switch (response.code()) {
            case 200:
                // Process the response
                ResponseBody responseBody = response.body();
                if (responseBody == null) throw new VoteCenterNotFoundException();

                String json = responseBody.string();
                if (StringUtils.isBlank(json)) throw new VoteCenterNotFoundException();

                return objectMapper.readValue(json, VoteCenterResponse.class);

            default:
                throw handleExceptions(response);
        }
    }

    private Response retryRequest(Account account, AccessToken accessToken, Request.Builder requestBuilder)
            throws IOException {

        accessToken = crmOAuth2Repository.refreshToken(account);

        // Modify request to add new access token
        requestBuilder.header(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken());
        return okHttpClient.newCall(requestBuilder.build()).execute();
    }

    @SuppressWarnings("Duplicates")
    private RestResponseException handleExceptions(Response response) throws RuntimeException {

        RestResponseException exception = new NotAcceptableException();

        switch (response.code()) {
            case 401:

                exception = new UnauthorizedException(response.message());
                break;

            case 404:
                exception = new NotFoundException(response.message());
                break;

            case 409:
                exception = new ConflictException(response.message());
                break;

            case 503:
                exception = new ServiceUnavailableException(response.message());
                break;
            default:
                String message = "Unhandled http code";
                logger.error(message + ". OAuth response: " + response.toString());
        }

        return exception;
    }
}
