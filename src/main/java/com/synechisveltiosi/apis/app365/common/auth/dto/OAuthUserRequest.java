package com.synechisveltiosi.apis.app365.common.auth.dto;

public class OAuthUserRequest {

    private String name;
    private String email;
    private String password;
    private Boolean active = Boolean.FALSE;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OAuthUserRequest withName(String name) {
        setName(name);
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OAuthUserRequest withEmail(String email) {
        setEmail(email);
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public OAuthUserRequest withPassword(String password) {
        setPassword(password);
        return this;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public OAuthUserRequest withActive(Boolean active) {
        setActive(active);
        return this;
    }
}
