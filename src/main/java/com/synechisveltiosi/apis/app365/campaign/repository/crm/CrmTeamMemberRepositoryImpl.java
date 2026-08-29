package com.synechisveltiosi.apis.app365.campaign.repository.crm;

import com.synechisveltiosi.apis.app365.accounts.config.CrmConfig;
import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.accounts.helpers.AccountHelper;
import com.applepolitical.apis.applepolitical365.campaign.dto.*;
import com.synechisveltiosi.apis.app365.campaign.dto.*;
import com.synechisveltiosi.apis.app365.campaign.entity.TeamLevel;
import com.synechisveltiosi.apis.app365.common.auth.SessionUtils;
import com.synechisveltiosi.apis.app365.common.auth.dto.AccessToken;
import com.synechisveltiosi.apis.app365.common.auth.repository.CrmOAuth2Repository;
import com.synechisveltiosi.apis.app365.common.rest.response.crm.ErrorHelper;
import com.synechisveltiosi.apis.app365.common.rest.response.pagination.PageResponse;
import com.synechisveltiosi.apis.app365.config.AppConfig;
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Clivens Petit <clivens.petit@centallylabs.com>
 */
@Service
public class CrmTeamMemberRepositoryImpl implements CrmTeamMemberRepository {

    private static final Logger logger = LoggerFactory.getLogger(CrmTeamMemberRepositoryImpl.class);

    private final AppConfig appConfig;
    private final ObjectMapper objectMapper;
    private final CrmOAuth2Repository crmOAuth2Repository;
    private final CrmMilitantRepository crmMilitantRepository;
    private final OkHttpClient okHttpClient;

    @Autowired
    public CrmTeamMemberRepositoryImpl(AppConfig appConfig, ObjectMapper objectMapper,
                                       CrmOAuth2Repository crmOAuth2Repository,
                                       CrmMilitantRepository crmMilitantRepository, OkHttpClient okHttpClient) {

        this.appConfig = appConfig;
        this.objectMapper = objectMapper;
        this.crmOAuth2Repository = crmOAuth2Repository;
        this.crmMilitantRepository = crmMilitantRepository;
        this.okHttpClient = okHttpClient;
    }

    @Override
    public Page<TeamMemberResponse> findAllTeamMember(Account account, User user, Pageable pageable) throws IOException {

        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        OkHttpClient client = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder()
                .url(crmConfig.getBaseUrl()
                        + appConfig.getCrm().getGetEndpoints().getMembers()
                        + String.format("?page=%d&size=%d", pageable.getPageNumber(), pageable.getPageSize()))
                .addHeader(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken())
                .addHeader("X-TenantID", crmConfig.getTenantId())
                .addHeader("X-Id-Card", AccountHelper.formatIdCard(user.getIdCard(), crmConfig));

        Response response = client.newCall(requestBuilder.build()).execute();

        // Retry request if the access token get expired
        response = getResponse(account, client, requestBuilder, response);

        // Deserialize request response
        switch (response.code()) {
            case 200:
                // Process the response
                ResponseBody responseBody = response.body();

                Page<TeamMemberResponse> emptyPage = new PageImpl<>(new ArrayList<>(),
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), 0);
                if (responseBody == null) return emptyPage;

                String json = responseBody.string();
                if (StringUtils.isBlank(json)) return emptyPage;

                PageResponse<TeamMemberResponse> responsePage = objectMapper.readValue(json,
                        new TypeReference<PageResponse<TeamMemberResponse>>() {
                        });

                return new PageImpl<>(responsePage.getData(), pageable,
                        responsePage.getPagination().getTotalElements());

            default:
                throw ErrorHelper.handleExceptions(objectMapper, response);
        }
    }

    @Override
    public List<TeamLevel> findLevelMembers(Account account, String idCard) throws IOException {

        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        OkHttpClient client = new OkHttpClient();

        Request.Builder requestBuilder = new Request.Builder()
                .url(crmConfig.getBaseUrl() + appConfig.getCrm().getGetEndpoints().getLevels())
                .addHeader(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken())
                .addHeader("X-TenantID", crmConfig.getTenantId())
                .addHeader("X-Id-Card", AccountHelper.formatIdCard(idCard, crmConfig));


        Response response = client.newCall(requestBuilder.build()).execute();

        // Retry request if the access token get expired
        response = getResponse(account, client, requestBuilder, response);

        switch (response.code()) {
            case 200:

                ResponseBody responseBody = response.body();

                if (responseBody == null) return new ArrayList<>();

                String json = responseBody.string();

                return objectMapper.readValue(json, new TypeReference<ArrayList<TeamLevel>>() {
                });
            default:
                throw ErrorHelper.handleExceptions(objectMapper, response);
        }
    }

    @Override
    public CbaUser findUser(Account account, String idCard) throws IOException {
        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        OkHttpClient client = new OkHttpClient();

        Request.Builder requestBuilder = new Request.Builder()
                .url(crmConfig.getBaseUrl() + appConfig.getCrm().getGetEndpoints().getCbaUser())
                .addHeader(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken())
                .addHeader("X-TenantID", crmConfig.getTenantId())
                .addHeader("X-Id-Card", AccountHelper.formatIdCard(idCard, crmConfig));


        Response response = client.newCall(requestBuilder.build()).execute();

        // Retry request if the access token get expired
        response = getResponse(account, client, requestBuilder, response);

        switch (response.code()) {
            case 200:
                ResponseBody responseBody = response.body();
                if (responseBody == null) return null;

                String json = responseBody.string();
                return objectMapper.readValue(json, CbaUser.class);
            default:
                throw ErrorHelper.handleExceptions(objectMapper, response);
        }
    }

    @Override
    public void addHeader(Account account, CbaHeaderRequest request) throws IOException {

        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        okhttp3.MediaType mediaType = okhttp3.MediaType.parse(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String url = appConfig.getCrm().getPostEndpoints().getCbaHeader();

        String json = new Gson().toJson(request);
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
    public void save(Account account, MilitantRequest militantRequest) throws IOException {

        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        okhttp3.MediaType mediaType = okhttp3.MediaType.parse(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String url = appConfig.getCrm().getPostEndpoints().getMembers();

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
    public void update(Account account, String memberId, TeamMemberRequest memberRequest) throws IOException {
        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        okhttp3.MediaType mediaType = okhttp3.MediaType.parse(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String url = appConfig.getCrm().getPutEndpoints().getMembers();

        String json = new Gson().toJson(memberRequest);
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, json);

        Request.Builder requestBuilder = new Request.Builder()
                .url(crmConfig.getBaseUrl() + url)
                .addHeader(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .addHeader("X-TenantID", crmConfig.getTenantId())
                .addHeader("id", memberId)
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
    public void patchAddress(Account account, String memberId, Map<String, Object> addressPatch) throws IOException {
        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        okhttp3.MediaType mediaType = okhttp3.MediaType.parse(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String url = appConfig.getCrm().getPutEndpoints().getAddress().replace("{id}", memberId);

        String json = new Gson().toJson(addressPatch);
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
    public void verifyTeamMembersEmailAddress(Account account, String email, String idCard) throws IOException {
        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        String url = appConfig.getCrm().getGetEndpoints().getTeamMembersEmailAddress();

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
    public void verifyTeamMembersPhoneNumbers(Account account, String countryID, String type, String phone, String idCard) throws IOException {
        AccessToken accessToken = crmOAuth2Repository.authenticate(account);
        CrmConfig crmConfig = account.getConfiguration().getCrmConfig();

        String url = appConfig.getCrm().getGetEndpoints().getTeamMembersPhoneNumber().replace("{type}", type);

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

    private Response getResponse(Account account, OkHttpClient client, Request.Builder requestBuilder,
                                 Response response) throws IOException {

        AccessToken accessToken;

        if (response.code() == 401) {
            accessToken = crmOAuth2Repository.refreshToken(account);

            // Modify request to add new access token
            requestBuilder.header(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken());
            response = client.newCall(requestBuilder.build()).execute();
        }

        return response;
    }

    private Response retryRequest(Account account, Request.Builder requestBuilder)
            throws IOException {

        AccessToken accessToken = crmOAuth2Repository.refreshToken(account);

        // Modify request to add new access token
        requestBuilder.header(HttpHeaders.AUTHORIZATION, accessToken.prepareAccessToken());
        return okHttpClient.newCall(requestBuilder.build()).execute();
    }
}
