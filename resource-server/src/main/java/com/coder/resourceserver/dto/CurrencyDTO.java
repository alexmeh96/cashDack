package com.coder.resourceserver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDTO {
    @JsonProperty("EUR")
    private float eur;
    @JsonProperty("USD")
    private float usd;
    @JsonProperty("RUB")
    private float rub;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
}
