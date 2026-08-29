package com.synechisveltiosi.apis.app365.accounts.config;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class MailConfig implements Serializable {

    private static final long serialVersionUID = 0L;

    private String host;
    private Integer port;
    private String username;
    private String password;
    private String protocol;
    private Boolean authenticationRequired;
    private Boolean tlsEnable;
    private Boolean tlsRequired;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Boolean isAuthenticationRequired() {
        return authenticationRequired != null && authenticationRequired;
    }

    public void setAuthenticationRequired(Boolean authenticationRequired) {
        this.authenticationRequired = authenticationRequired;
    }

    public Boolean isTlsEnable() {
        return tlsEnable != null && tlsEnable;
    }

    public void setTlsEnable(Boolean tlsEnable) {
        this.tlsEnable = tlsEnable;
    }

    public Boolean isTlsRequired() {
        return tlsRequired != null && tlsRequired;
    }

    public void setTlsRequired(Boolean tlsRequired) {
        this.tlsRequired = tlsRequired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MailConfig that = (MailConfig) o;

        return new EqualsBuilder()
                .append(host, that.host)
                .append(port, that.port)
                .append(username, that.username)
                .append(password, that.password)
                .append(protocol, that.protocol)
                .append(authenticationRequired, that.authenticationRequired)
                .append(tlsEnable, that.tlsEnable)
                .append(tlsRequired, that.tlsRequired)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(host)
                .append(port)
                .append(username)
                .append(password)
                .append(protocol)
                .append(authenticationRequired)
                .append(tlsEnable)
                .append(tlsRequired)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("host", host)
                .append("port", port)
                .append("username", username)
                .append("password", password)
                .append("protocol", protocol)
                .append("authenticationRequired", authenticationRequired)
                .append("tlsEnable", tlsEnable)
                .append("tlsRequired", tlsRequired)
                .toString();
    }
}
