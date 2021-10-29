package com.hcl.currencyexchange.fcimportservice.source;

import java.math.BigDecimal;
import java.util.Map;

public class FcCurrencyExchangeRate {

    private Map<String, BigDecimal> data;

    public Map<String, BigDecimal> getData() {
        return data;
    }

    public void setData(Map<String, BigDecimal> data) {
        this.data = data;
    }
}
