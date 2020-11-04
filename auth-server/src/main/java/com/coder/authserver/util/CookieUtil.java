package com.coder.authserver.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    @Value("${accessTokenCookieName}")
    private String accessTokenCookieName;

    public HttpCookie createRefreshTokenCookie(String token) {

        return ResponseCookie.from(accessTokenCookieName, token)
                .maxAge(7*24*60*60)
                .httpOnly(true)
                .path("/")
                .build();
    }
}
