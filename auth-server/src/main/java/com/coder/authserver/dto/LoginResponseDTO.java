package com.coder.authserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String tokenValue;
    private Long duration;
    private String email;
}
