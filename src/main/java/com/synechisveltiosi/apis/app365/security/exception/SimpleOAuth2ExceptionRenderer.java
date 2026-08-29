package com.synechisveltiosi.apis.app365.security.exception;

import com.synechisveltiosi.apis.app365.security.DefaultMappingJackson2HttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.oauth2.provider.error.DefaultOAuth2ExceptionRenderer;

import java.util.ArrayList;
import java.util.List;

public class SimpleOAuth2ExceptionRenderer extends DefaultOAuth2ExceptionRenderer {

    public SimpleOAuth2ExceptionRenderer() {
        setMessageConverters(getMessageConverters());
    }

    private List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> result = new ArrayList<>();
        result.add(new DefaultMappingJackson2HttpMessageConverter());
        return result;
    }
}
