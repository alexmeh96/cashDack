package com.coder.resourceserver.controller;

import com.coder.resourceserver.dto.CurrencyDTO;
import com.coder.resourceserver.dao.Currency;
import com.coder.resourceserver.mappers.ContentMapper;
import com.coder.resourceserver.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials = "true")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/content")
public class MainController {

    @Autowired
    private CurrencyService currencyService;

//    @GetMapping("/all")
//    public String allAccess() {
//        return "Public Content.";
//    }
//
//    @GetMapping("/user")
//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//    public String userAccess() {
//        return "User Content.";
//    }
//
//    @GetMapping("/mod")
//    @PreAuthorize("hasRole('MODERATOR')")
//    public String moderatorAccess() {
//        return "Moderator Board.";
//    }
//
//    @GetMapping("/admin")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String adminAccess() {
//        return "Admin Board.";
//    }

    @GetMapping("currency")
    public CurrencyDTO getCurrency() {

        Currency currency = currencyService.getCurrency();
        CurrencyDTO currencyDTO = ContentMapper.toCurrencyDTO(currency);

        return currencyDTO;
    }

}
