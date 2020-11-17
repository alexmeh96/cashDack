package com.coder.authserver.util;

import com.coder.authserver.model.Token;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    public HttpCookie createTokenCookie(Token token) {
        return ResponseCookie.from(token.getTokenName(), token.getTokenValue())
                .maxAge(token.getDuration())
                .httpOnly(true)
                .path("/")
                .build();
    }

    public HttpHeaders createTokenCookieHeader(Token refreshToken) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, createTokenCookie(refreshToken).toString());
        return responseHeaders;
    }
}
