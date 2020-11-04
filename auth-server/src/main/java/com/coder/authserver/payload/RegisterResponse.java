package com.coder.authserver.payload;

import lombok.Data;

@Data
public class RegisterResponse {
    private String message;

    public RegisterResponse(String message) {
        this.message = message;
    }
}
