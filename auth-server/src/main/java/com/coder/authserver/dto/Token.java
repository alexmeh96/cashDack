package com.coder.authserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: TCMALTUNKAN - MEHMET ANIL ALTUNKAN
 * @Date: 30.12.2019:09:39, Pzt
 **/
@Data
@AllArgsConstructor
public class Token {
    private String tokenName;
    private String tokenValue;
    private Long duration;
    private LocalDateTime expiryDate;

}