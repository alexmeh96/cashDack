package com.coder.authserver.util;

import com.coder.authserver.dto.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
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
}
