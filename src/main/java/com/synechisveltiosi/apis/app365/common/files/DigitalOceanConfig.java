package com.synechisveltiosi.apis.app365.common.files;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Alfredo Martinez <martin3zra@gmail.com> on 10/3/18.
 */
@Configuration
public class DigitalOceanConfig {
    @Value("${app365.digitalocean.spaces.key}")
    private String DO_SPACES_ACCESS_KEY;

    @Value("${app365.digitalocean.spaces.secret}")
    private String DO_SPACES_ACCESS_SECRET;

    @Value("${app365.digitalocean.spaces.endpoint}")
    private String DO_SPACES_ENDPOINT;

    @Value("${app365.digitalocean.spaces.region}")
    private String DO_SPACES_REGION;

    @Bean
    public AmazonS3 getCredentials() {
        BasicAWSCredentials creds = new BasicAWSCredentials(DO_SPACES_ACCESS_KEY, DO_SPACES_ACCESS_SECRET);
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(DO_SPACES_ENDPOINT, DO_SPACES_REGION))
                .withCredentials(new AWSStaticCredentialsProvider(creds)).build();
    }
}
