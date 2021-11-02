package com.hcl.currencyexchange.common.data;

import java.math.BigDecimal;
import java.util.Date;

public class CurrencyExchangeRate {

    private String currency;
    private String sourceId;
    private BigDecimal rate;
    private Date timestamp;

    public String getSourceId() {
        return sourceId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
