package com.coder.resourceserver.mappers;

import com.coder.resourceserver.dao.Currency;
import com.coder.resourceserver.dto.CurrencyDTO;

import java.util.Date;

public class ContentMapper {
    public static CurrencyDTO toCurrencyDTO(Currency currency) {
        return new CurrencyDTO(currency.getEur(),
                currency.getUsd(),
                currency.getRub(),
                new Date()
        );
    }
}
