package com.coder.authserver.payload;

import com.coder.authserver.dto.Token;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Token token;

    private Long id;
    private String username;
    private String email;
    private List<String> roles;

}
