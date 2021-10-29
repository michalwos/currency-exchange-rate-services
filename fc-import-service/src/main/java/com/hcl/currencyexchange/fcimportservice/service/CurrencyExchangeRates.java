package com.hcl.currencyexchange.fcimportservice.service;

import java.util.Date;
import java.util.List;

public class CurrencyExchangeRates {

    private String sourceId;
    private Date timestamp;

    private List<CurrencyExchangeRate> rates;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<CurrencyExchangeRate> getRates() {
        return rates;
    }

    public void setRates(List<CurrencyExchangeRate> rates) {
        this.rates = rates;
    }
}
