package com.synechisveltiosi.apis.app365.security.exception;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.*;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


@JsonSerialize(
        using = SimpleOAuth2ExceptionJackson1Serializer.class
)
@JsonDeserialize(
        using = SimpleOAuth2ExceptionJackson1Deserializer.class
)
@com.fasterxml.jackson.databind.annotation.JsonSerialize(
        using = SimpleOAuth2ExceptionJackson2Serializer.class
)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(
        using = SimpleOAuth2ExceptionJackson2Deserializer.class
)
public class SimpleOAuth2Exception extends OAuth2Exception {

    public static final String ERROR = "error";
    public static final String DESCRIPTION = "description";
    public static final String URI = "error_uri";
    public static final String INVALID_REQUEST = "invalid_request";
    public static final String INVALID_CLIENT = "invalid_client";
    public static final String INVALID_GRANT = "invalid_grant";
    public static final String UNAUTHORIZED_CLIENT = "unauthorized_client";
    public static final String UNSUPPORTED_GRANT_TYPE = "unsupported_grant_type";
    public static final String INVALID_SCOPE = "invalid_scope";
    public static final String INSUFFICIENT_SCOPE = "insufficient_scope";
    public static final String INVALID_TOKEN = "invalid_token";
    public static final String REDIRECT_URI_MISMATCH = "redirect_uri_mismatch";
    public static final String UNSUPPORTED_RESPONSE_TYPE = "unsupported_response_type";
    public static final String ACCESS_DENIED = "access_denied";
    private Map<String, String> additionalInformation = null;
    private int httpErrorCode = 400;
    private String oAuth2ErrorCode = "invalid_request";

    public SimpleOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public SimpleOAuth2Exception(String msg) {
        super(msg);
    }

    public static OAuth2Exception create(String errorCode, String errorMessage) {
        if (errorMessage == null) {
            errorMessage = errorCode == null ? "OAuth Error" : errorCode;
        }

        return "invalid_client".equals(errorCode)
                ? new InvalidClientException(errorMessage) : ("unauthorized_client".equals(errorCode)
                ? new UnauthorizedClientException(errorMessage) : ("invalid_grant".equals(errorCode)
                ? new InvalidGrantException(errorMessage) : ("invalid_scope".equals(errorCode)
                ? new InvalidScopeException(errorMessage) : ("invalid_token".equals(errorCode)
                ? new InvalidTokenException(errorMessage) : ("invalid_request".equals(errorCode)
                ? new InvalidRequestException(errorMessage) : ("redirect_uri_mismatch".equals(errorCode)
                ? new RedirectMismatchException(errorMessage) : ("unsupported_grant_type".equals(errorCode)
                ? new UnsupportedGrantTypeException(errorMessage) : ("unsupported_response_type".equals(errorCode)
                ? new UnsupportedResponseTypeException(errorMessage) : ("access_denied".equals(errorCode)
                ? new UserDeniedAuthorizationException(errorMessage) : new OAuth2Exception(errorMessage))))))))));
    }

    public static OAuth2Exception valueOf(Map<String, String> errorParams) {
        String errorCode = errorParams.get("error");
        String errorMessage = errorParams.containsKey("description") ? errorParams.get("description") : null;
        OAuth2Exception ex = create(errorCode, errorMessage);
        Set<Map.Entry<String, String>> entries = errorParams.entrySet();
        Iterator var5 = entries.iterator();

        while (var5.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) var5.next();
            String key = entry.getKey();
            if (!"error".equals(key) && !"description".equals(key)) {
                ex.addAdditionalInformation(key, entry.getValue());
            }
        }

        return ex;
    }

    public String getOAuth2ErrorCode() {
        return oAuth2ErrorCode;
    }

    public void setOAuth2ErrorCode(String oAuth2ErrorCode) {
        this.oAuth2ErrorCode = oAuth2ErrorCode;
    }

    public int getHttpErrorCode() {
        return httpErrorCode;
    }

    public void setHttpErrorCode(int httpErrorCode) {
        this.httpErrorCode = httpErrorCode;
    }

    public Map<String, String> getAdditionalInformation() {
        return this.additionalInformation;
    }

    public void setAdditionalInformation(Map<String, String> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public void addAdditionalInformation(String key, String value) {
        if (this.additionalInformation == null) {
            //noinspection unchecked
            this.additionalInformation = new TreeMap();
        }

        this.additionalInformation.put(key, value);
    }

    public String toString() {
        return this.getSummary();
    }

    public String getSummary() {
        StringBuilder builder = new StringBuilder();
        String delim = "";
        String error = this.getOAuth2ErrorCode();
        if (error != null) {
            builder.append(delim).append("error=\"").append(error).append("\"");
            delim = ", ";
        }

        String errorMessage = this.getMessage();
        if (errorMessage != null) {
            builder.append(delim).append("description=\"").append(errorMessage).append("\"");
            delim = ", ";
        }

        Map<String, String> additionalParams = this.getAdditionalInformation();
        if (additionalParams != null) {
            for (Iterator var6 = additionalParams.entrySet().iterator(); var6.hasNext(); delim = ", ") {
                //noinspection unchecked
                Map.Entry<String, String> param = (Map.Entry) var6.next();
                builder.append(delim).append(param.getKey()).append("=\"").append(param.getValue()).append("\"");
            }
        }

        return builder.toString();
    }
}
