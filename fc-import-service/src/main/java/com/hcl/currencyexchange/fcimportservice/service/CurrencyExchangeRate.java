package com.hcl.currencyexchange.fcimportservice.service;

import java.math.BigDecimal;

public class CurrencyExchangeRate {

    private String currency;
    private BigDecimal rate;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
