package com.hcl.currencyexchange.common.data;

import java.math.BigDecimal;

public class UserRateLimitSetting {

    private Long  userId;
    private String currency;
    private BigDecimal rate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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
