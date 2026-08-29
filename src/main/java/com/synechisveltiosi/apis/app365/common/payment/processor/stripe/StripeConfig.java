package com.synechisveltiosi.apis.app365.common.payment.processor.stripe;

import com.stripe.net.RequestOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

    private final String apiKey;
    private final Integer connectionTimeout;
    private final Integer readTimeout;

    public StripeConfig(@Value("${app365.stripe.api-key}") String apiKey,
                        @Value("${app365.stripe.http.connection-timeout}") Integer connectionTimeout,
                        @Value("${app365.stripe.http.read-timeout}") Integer readTimeout) {

        this.apiKey = apiKey;
        this.connectionTimeout = connectionTimeout * 1000;
        this.readTimeout = readTimeout * 1000;
    }

    @Bean
    public RequestOptions requestOptions() {
        return RequestOptions.builder()
                .setApiKey(apiKey)
                .setConnectTimeout(connectionTimeout) // in milliseconds
                .setReadTimeout(readTimeout) // in milliseconds
                .build();
    }
}
