package com.synechisveltiosi.apis.app365.security.config;

import com.synechisveltiosi.apis.app365.security.DefaultOAuth2AuthenticationEntryPoint;
import com.synechisveltiosi.apis.app365.security.exception.SimpleOAuth2ExceptionRenderer;
import com.synechisveltiosi.apis.app365.security.exception.SimpleWebResponseExceptionTranslator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.DefaultOAuth2ExceptionRenderer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends ResourceServerConfigurerAdapter {

    @Value("${app365.oauth.realm}")
    private String realm;

    @Value("${app365.oauth.type-name}")
    private String typeName;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,
                        "/api/v1/users",
                        "/api/v1/users/*",
                        "/api/v1/users/social",
                        "/api/v1/users/social/*",
                        "/api/v1/auth/password/reset",
                        "/api/v1/auth/password/reset/*")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.PUT,
                        "/api/v1/auth/password/reset",
                        "/api/v1/auth/password/reset/*")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/manage/*")
                .permitAll();
    }

    @Bean
    public DefaultOAuth2AuthenticationEntryPoint getClientAuthenticationEntryPoint() {
        DefaultOAuth2AuthenticationEntryPoint entryPoint = new DefaultOAuth2AuthenticationEntryPoint();
        entryPoint.setRealmName(realm);
        entryPoint.setTypeName(typeName);
        entryPoint.setExceptionRenderer(getExceptionRenderer());
        entryPoint.setExceptionTranslator(getWebResponseExceptionTranslator());
        return entryPoint;
    }

    @Bean
    public DefaultOAuth2ExceptionRenderer getExceptionRenderer() {
        return new SimpleOAuth2ExceptionRenderer();
    }

    @Bean
    public WebResponseExceptionTranslator getWebResponseExceptionTranslator() {
        return new SimpleWebResponseExceptionTranslator();
    }
}
