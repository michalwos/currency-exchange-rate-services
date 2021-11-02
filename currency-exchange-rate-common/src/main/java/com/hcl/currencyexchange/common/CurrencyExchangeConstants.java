package com.hcl.currencyexchange.common;

public final class CurrencyExchangeConstants {

    private CurrencyExchangeConstants() {
    }

    public static final String CURRENCY_EXCHANGE_RATES_TOPIC = "currency_exchange_rates";
    public static final String USER_SETTINGS_TOPIC = "user_settings";
    public static final String RATE_ALERTS_TOPIC = "rate_alerts";

    public static final String CURRENCY_RATE_LIMIT_STORE_NAME = "CurrencyRateLimitsStore";
}
