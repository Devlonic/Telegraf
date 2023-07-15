package com.telegraph.messages.payload.request;


import jakarta.validation.constraints.NotBlank;
import org.springframework.util.StringUtils;

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

    public static JwtRequest toJWT(String accessToken){
        return  new JwtRequest(parseJwt(accessToken));

    }
    public static String parseJwt(String headerAuth) {
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}