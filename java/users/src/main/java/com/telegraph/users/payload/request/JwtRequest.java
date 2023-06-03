package com.telegraph.users.payload.request;


import jakarta.validation.constraints.NotBlank;

public class JwtRequest {
    @NotBlank
    private String accessToken;

    public JwtRequest(String accessToken) {
        this.accessToken = accessToken;
    }

    public JwtRequest() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "JwtRequest{" +
                "accessToken='" + accessToken + '\'' +
                '}';
    }
}