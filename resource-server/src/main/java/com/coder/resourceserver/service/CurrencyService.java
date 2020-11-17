package com.coder.resourceserver.service;

import com.coder.resourceserver.dao.Currency;
import com.coder.resourceserver.repo.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {
    @Autowired
    public CurrencyRepository currencyRepository;

    public Currency getCurrency() {
        return currencyRepository.getOne(1l);
    }
}
