package com.ecommerce.project.security.payload.request;

import jakarta.annotation.Nonnull;

public class LoginRequest {

    @Nonnull()
    private String username;

    @Nonnull()
    private String password;

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
}
