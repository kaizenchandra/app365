/*
 * Copyright (c) 2010 - 2016 MAGIC SOFTWARE BAY SRL. All rights reserved.
 *
 * All information contained herein is, and remains the property of MAGIC
 * SOFTWARE BAY SRL. The intellectual and technical concepts contained herein
 * are proprietary to MAGIC SOFTWARE BAY SRL and may be covered by Dominican
 * Republic and Foreign Patents, patents in process, and are protected by trade
 * secret or copyright law. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from MAGIC SOFTWARE BAY SRL.
 */

package com.synechisveltiosi.apis.app365.config;

import com.synechisveltiosi.apis.app365.common.http.request.HttpHeader;
import com.synechisveltiosi.apis.app365.common.util.date.DateFormatUtils;
import com.synechisveltiosi.apis.app365.common.util.enums.EnumJsonDeserializer;
import com.synechisveltiosi.apis.app365.common.util.enums.EnumJsonSerializer;
import com.synechisveltiosi.apis.app365.common.util.enums.StringToEnumConverterFactory;
import com.authy.AuthyApiClient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.textmagic.sdk.RestClient;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author Clivens Petit <peclevens@magicsoftbay.com>
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToEnumConverterFactory());
    }

    @Bean
    public ObjectMapper objectMapper() {
        SimpleModule module = new SimpleModule();
        module.setDeserializerModifier(new EnumJsonDeserializer());
        module.addSerializer(new EnumJsonSerializer());

        DateFormat dateFormat = new SimpleDateFormat(DateFormatUtils.ISO_8601_DATETIME_TIME_ZONE_STRING_FORMAT);

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(dateFormat);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(module);
        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }

    @Bean
    public OkHttpClient getOkHttpClient() { // TODO Review this object later
        return new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    @Bean
    public AuthyApiClient authyApiClient(@Value("${app365.twilio.api-key}") String apiKey) {
        return new AuthyApiClient(apiKey);
    }

    @Bean
    public RestClient restClient(@Value("${app365.textmagic.username}") String username,
                                 @Value("${app365.textmagic.api-key}") String apiKey) {

        return new RestClient(username, apiKey);
    }

    @Bean
    @RequestScope
    public HttpHeader httpHeader(HttpServletRequest request,
                                 @Value("${app365.subdomain-regex}") String subdomainRegex) {

        HttpHeader httpHeader = new HttpHeader(request);
        httpHeader.setSubdomainRegex(subdomainRegex);

        return httpHeader;
    }
}
