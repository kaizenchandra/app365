package com.synechisveltiosi.apis.app365.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class DefaultRemoteTokenServices extends RemoteTokenServices {

    private String checkTokenEndpointUrl;
    private RestOperations restTemplate = new RestTemplate();
    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

    public DefaultRemoteTokenServices() {
        ((RestTemplate) this.restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            protected boolean hasError(HttpStatus statusCode) {
                return false;
            }
        });
    }

    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("X-Requested-With", "XMLHttpRequest");

        Map<String, Object> map = getForMap(this.checkTokenEndpointUrl, headers);
        String email = String.valueOf(map.get("email"));

        if (map.containsKey("message")) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("check_token returned error: " + map.get("message"));
            }

            throw new InvalidTokenException(accessToken);
        } else if (StringUtils.isEmpty(email) || !EmailValidator.getInstance().isValid(email)) {
            this.logger.debug("check_token returned email attribute: " + email);
            throw new InvalidTokenException(accessToken);
        } else {
            return this.tokenConverter.extractAuthentication(map);
        }
    }

    @Override
    public void setCheckTokenEndpointUrl(String checkTokenEndpointUrl) {
        this.checkTokenEndpointUrl = checkTokenEndpointUrl;
    }

    @Override
    public void setRestTemplate(RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void setAccessTokenConverter(AccessTokenConverter accessTokenConverter) {
        this.tokenConverter = accessTokenConverter;
    }

    private Map<String, Object> getForMap(String path, HttpHeaders headers) {
        //noinspection ConstantConditions
        if (headers.getContentType() == null) {
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }

        // noinspection unchecked
        return (Map) this.restTemplate.exchange(path, HttpMethod.GET, new HttpEntity(headers), Map.class, new Object[0]).getBody();
    }
}
