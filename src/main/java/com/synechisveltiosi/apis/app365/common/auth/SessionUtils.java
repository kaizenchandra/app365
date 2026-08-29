package com.synechisveltiosi.apis.app365.common.auth;

import com.synechisveltiosi.apis.app365.common.auth.dto.AccessToken;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.UnauthorizedException;
import com.synechisveltiosi.apis.app365.users.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import javax.validation.constraints.NotNull;

public final class SessionUtils {

    public static @NotNull User getLoggedUser() throws NotFoundException {
        Object authObj = SecurityContextHolder.getContext().getAuthentication();
        if (authObj instanceof OAuth2Authentication) {
            Authentication authentication = ((OAuth2Authentication) authObj).getUserAuthentication();
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                if (authentication.getPrincipal() instanceof User)
                    return (User) authentication.getPrincipal();

                return (User) authentication.getDetails();
            }
        }

        throw new NotFoundException("User not found.");
    }

    public static @NotNull AccessToken getAccessToken() throws UnauthorizedException {
        Object authObj = SecurityContextHolder.getContext().getAuthentication();
        if (authObj instanceof OAuth2Authentication) {
            Object authObjDetails = ((OAuth2Authentication) authObj).getDetails();
            if (authObjDetails instanceof OAuth2AuthenticationDetails) {
                OAuth2AuthenticationDetails authenticationDetails = (OAuth2AuthenticationDetails) authObjDetails;

                return new AccessToken()
                        .withAccessToken(authenticationDetails.getTokenValue())
                        .withTokenType(authenticationDetails.getTokenType());
            }
        }

        throw new UnauthorizedException();
    }
}
