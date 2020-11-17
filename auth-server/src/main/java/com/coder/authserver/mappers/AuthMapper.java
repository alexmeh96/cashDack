package com.coder.authserver.mappers;

import com.coder.authserver.dao.User;
import com.coder.authserver.dto.LoginResponseDTO;
import com.coder.authserver.model.Token;

public class AuthMapper {
    public static LoginResponseDTO toLoginResponseDTO(User user, Token token) {
        return new LoginResponseDTO(token.getTokenValue(),
                token.getDuration(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
