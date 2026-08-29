package com.synechisveltiosi.apis.app365.security;

import com.synechisveltiosi.apis.app365.common.rest.response.ErrorResponse;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import java.io.IOException;

public class DefaultMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {
        super.writeInternal(transformObject(object), outputMessage);
    }

    protected Object transformObject(Object object) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(0);
        error.setMessage(object.toString());

        // Override OAuth2Exception in API
        if (object instanceof OAuth2Exception) {
            OAuth2Exception ex = (OAuth2Exception) object;
            error.setStatus(ex.getHttpErrorCode());
            error.setType(ex.getOAuth2ErrorCode());
            error.setMessage(ex.getMessage());
        }

        return error;
    }
}
