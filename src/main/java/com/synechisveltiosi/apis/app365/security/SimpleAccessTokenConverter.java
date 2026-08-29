package com.synechisveltiosi.apis.app365.security;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.UnauthorizedException;
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.synechisveltiosi.apis.app365.users.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;

import java.util.*;

public class SimpleAccessTokenConverter implements AccessTokenConverter {

    private final UserService userService;
    private UserAuthenticationConverter userTokenConverter = new DefaultUserAuthenticationConverter();
    private boolean includeGrantType;

    public SimpleAccessTokenConverter(UserService userService) {
        this.userService = userService;
    }

    public void setUserTokenConverter(UserAuthenticationConverter userTokenConverter) {
        this.userTokenConverter = userTokenConverter;
    }

    public void setIncludeGrantType(boolean includeGrantType) {
        this.includeGrantType = includeGrantType;
    }

    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        OAuth2Request clientToken = authentication.getOAuth2Request();
        if (!authentication.isClientOnly()) {
            response.putAll(this.userTokenConverter.convertUserAuthentication(authentication.getUserAuthentication()));
        } else if (clientToken.getAuthorities() != null && !clientToken.getAuthorities().isEmpty()) {
            response.put("authorities", AuthorityUtils.authorityListToSet(clientToken.getAuthorities()));
        }

        if (token.getScope() != null) {
            response.put("scope", token.getScope());
        }

        if (token.getAdditionalInformation().containsKey("jti")) {
            response.put("jti", token.getAdditionalInformation().get("jti"));
        }

        if (token.getExpiration() != null) {
            response.put("exp", token.getExpiration().getTime() / 1000L);
        }

        if (this.includeGrantType && authentication.getOAuth2Request().getGrantType() != null) {
            response.put("grant_type", authentication.getOAuth2Request().getGrantType());
        }

        response.putAll(token.getAdditionalInformation());
        response.put("client_id", clientToken.getClientId());
        if (clientToken.getResourceIds() != null && !clientToken.getResourceIds().isEmpty()) {
            response.put("aud", clientToken.getResourceIds());
        }

        return response;
    }

    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(value);
        Map<String, Object> info = new HashMap<>(map);
        info.remove("exp");
        info.remove("aud");
        info.remove("client_id");
        info.remove("scope");
        if (map.containsKey("exp")) {
            token.setExpiration(new Date((Long) map.get("exp") * 1000L));
        }

        if (map.containsKey("jti")) {
            info.put("jti", map.get("jti"));
        }

        token.setScope(this.extractScope(map));
        token.setAdditionalInformation(info);
        return token;
    }

    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        Map<String, String> parameters = new HashMap<>();
        Set<String> scope = this.extractScope(map);

        Map<String, Object> newMap = new HashMap<>();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            newMap.put(entry.getKey(), entry.getValue());
        }

        String email = String.valueOf(map.get("email"));

        Optional<User> userOptional = userService.findByEmail(email);
        if (!userOptional.isPresent()) throw new UnauthorizedException();

        User newUser = userOptional.get();

        newMap.put("user_name", newUser);

        Authentication user = this.userTokenConverter.extractAuthentication(newMap);
        String clientId = (String) map.get("client_id");
        parameters.put("client_id", clientId);
        if (this.includeGrantType && map.containsKey("grant_type")) {
            parameters.put("grant_type", (String) map.get("grant_type"));
        }

        //noinspection unchecked
        Set<String> resourceIds = new LinkedHashSet(map.containsKey("aud") ? this.getAudience(map) : Collections.emptySet());
        Collection<? extends GrantedAuthority> authorities = null;
        if (user == null && map.containsKey("authorities")) {
            String[] roles = (String[]) ((Collection) map.get("authorities")).toArray(new String[0]);
            authorities = AuthorityUtils.createAuthorityList(roles);
        }

        OAuth2Request request = new OAuth2Request(parameters, clientId, authorities, true, scope, resourceIds, null, null, null);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(request, user);
        oAuth2Authentication.setDetails(newUser);

        return oAuth2Authentication;
    }

    private Collection<String> getAudience(Map<String, ?> map) {
        Object auds = map.get("aud");
        if (auds instanceof Collection) {

            //noinspection unchecked
            Collection<String> result = (Collection) auds;
            return result;
        } else {
            return Collections.singleton((String) auds);
        }
    }

    private Set<String> extractScope(Map<String, ?> map) {
        Set<String> scope = Collections.emptySet();
        if (map.containsKey("scope")) {
            Object scopeObj = map.get("scope");
            if (scopeObj instanceof String) {
                //noinspection unchecked
                scope = new LinkedHashSet(Arrays.asList(((String) scopeObj).split(" ")));
            } else if (Collection.class.isAssignableFrom(scopeObj.getClass())) {
                //noinspection unchecked
                Collection<String> scopeColl = (Collection) scopeObj;

                //noinspection unchecked
                scope = new LinkedHashSet(scopeColl);
            }
        }

        //noinspection unchecked
        return scope;
    }
}
