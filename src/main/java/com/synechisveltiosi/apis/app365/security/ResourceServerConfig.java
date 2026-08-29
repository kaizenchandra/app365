package com.synechisveltiosi.apis.app365.security;

import com.synechisveltiosi.apis.app365.common.auth.service.OAuth2Service;
import com.synechisveltiosi.apis.app365.config.AppConfig;
import com.synechisveltiosi.apis.app365.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    private final String baseUrl;
    private final String resourceId;
    private final AuthenticationEntryPoint entryPoint;
    private final UserService userService;
    private final AppConfig appConfig;

    @Autowired
    public ResourceServerConfig(@Value("${app365.oauth.base-url}") String baseUrl,
                                @Value("${app365.oauth.resource-id}") String resourceId,
                                AppConfig appConfig, AuthenticationEntryPoint entryPoint, UserService userService) {

        this.baseUrl = baseUrl;
        this.resourceId = resourceId;
        this.appConfig = appConfig;
        this.entryPoint = entryPoint;
        this.userService = userService;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
                .tokenServices(tokenService())
                .resourceId(resourceId)
                .authenticationEntryPoint(entryPoint)
                .stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, appConfig.getNonProtectedEndpoints().getGetEndpoints())
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, appConfig.getNonProtectedEndpoints().getPostEndpoints())
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.PUT, appConfig.getNonProtectedEndpoints().getPutEndpoints())
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .accessDeniedHandler(new DefaultAccessDeniedHandler());
    }

    @Bean
    public ResourceServerTokenServices tokenService() {
        RemoteTokenServices tokenServices = new DefaultRemoteTokenServices();
        tokenServices.setCheckTokenEndpointUrl(baseUrl + OAuth2Service.CHECK_TOKEN_ENDPOINT);
        tokenServices.setAccessTokenConverter(new SimpleAccessTokenConverter(userService));

        return tokenServices;
    }
}