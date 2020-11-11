package com.coder.authserver.payload;

import com.coder.authserver.dto.Token;
import com.coder.authserver.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Token token;

    private UserDto user;
}
