
package com.synechisveltiosi.apis.app365.common.auth.dto;

import com.synechisveltiosi.apis.app365.common.util.RegistrationChannel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocialTokenRequest {

    @JsonProperty("token")
    private String token;

    @JsonProperty("channel")
    private RegistrationChannel channel;

    @JsonIgnore
    private String language;

    @JsonIgnore
    private String timeZoneId;

    @JsonIgnore
    private Integer timeZoneOffset;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SocialTokenRequest withToken(String token) {
        this.token = token;
        return this;
    }

    public RegistrationChannel getChannel() {
        return channel;
    }

    public void setChannel(RegistrationChannel channel) {
        this.channel = channel;
    }

    public SocialTokenRequest withChannel(RegistrationChannel channel) {
        this.channel = channel;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public SocialTokenRequest withLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public SocialTokenRequest withTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
        return this;
    }

    public Integer getTimeZoneOffset() {
        return timeZoneOffset;
    }

    public void setTimeZoneOffset(Integer timeZoneOffset) {
        this.timeZoneOffset = timeZoneOffset;
    }

    public SocialTokenRequest withTimeZoneOffset(Integer timeZoneOffset) {
        this.timeZoneOffset = timeZoneOffset;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SocialTokenRequest that = (SocialTokenRequest) o;

        return new EqualsBuilder()
                .append(token, that.token)
                .append(channel, that.channel)
                .append(language, that.language)
                .append(timeZoneId, that.timeZoneId)
                .append(timeZoneOffset, that.timeZoneOffset)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(token)
                .append(channel)
                .append(language)
                .append(timeZoneId)
                .append(timeZoneOffset)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("token", token)
                .append("channel", channel)
                .append("language", language)
                .append("timeZoneId", timeZoneId)
                .append("timeZoneOffset", timeZoneOffset)
                .toString();
    }
}
