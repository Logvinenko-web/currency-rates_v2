package com.anton.rate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FiatDto {
    private String currency;
    private double rate;
}
