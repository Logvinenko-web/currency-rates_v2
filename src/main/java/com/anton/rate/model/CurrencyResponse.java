package com.anton.rate.model;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrencyResponse {

    private List<FiatDto> fiat;
    private List<CryptoDto> crypto;
}
