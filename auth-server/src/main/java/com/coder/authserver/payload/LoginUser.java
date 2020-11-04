package com.coder.authserver.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginUser {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
