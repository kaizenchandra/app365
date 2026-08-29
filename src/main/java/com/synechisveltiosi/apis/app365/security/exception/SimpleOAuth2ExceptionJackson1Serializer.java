package com.synechisveltiosi.apis.app365.security.exception;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.util.Map;

public class SimpleOAuth2ExceptionJackson1Serializer extends JsonSerializer<OAuth2Exception> {

    @Override
    public void serialize(OAuth2Exception value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("status", value.getHttpErrorCode());
        jgen.writeStringField("type", value.getOAuth2ErrorCode());
        String errorMessage = value.getMessage();
        if (errorMessage != null) {
            errorMessage = HtmlUtils.htmlEscape(errorMessage);
        }
        jgen.writeStringField("message", errorMessage);
        if (value.getAdditionalInformation() != null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                jgen.writeStringField(key, add);
            }
        }
        jgen.writeEndObject();
    }
}
