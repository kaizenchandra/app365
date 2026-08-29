package com.synechisveltiosi.apis.app365.security;

import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.OAuth2ExceptionRenderer;

public class DefaultOAuth2AuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

    @Override
    public void setExceptionRenderer(OAuth2ExceptionRenderer exceptionRenderer) {
        super.setExceptionRenderer(exceptionRenderer);
    }
}
