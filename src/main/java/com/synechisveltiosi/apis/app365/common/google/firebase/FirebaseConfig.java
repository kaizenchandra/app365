package com.synechisveltiosi.apis.app365.common.google.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class FirebaseConfig {

    private final String databaseUrl;
    private final String serviceAccountKeyPath;

    public FirebaseConfig(@Value("${app365.google.firebase.database.url}") String databaseUrl,
                          @Value("${app365.google.firebase.service-account-key-path}") String serviceAccountKeyPath) {

        this.databaseUrl = databaseUrl;
        this.serviceAccountKeyPath = serviceAccountKeyPath;
    }

    @Bean
    public FirebaseOptions firebaseOptions() throws IOException {
        Resource resource = new ClassPathResource(serviceAccountKeyPath);

        return new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                .setDatabaseUrl(databaseUrl)
                .build();
    }
}
