package com.hcl.currencyexchange.alert.demo.service;

import com.hcl.currencyexchange.common.CurrencyExchangeConstants;
import com.hcl.currencyexchange.common.data.CurrencyExchangeRate;
import com.hcl.currencyexchange.common.data.UserRateLimitSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

@Service
public class DemoAlertSetupService {

    @Autowired
    @Qualifier("userSettingsTemplate")
    private KafkaTemplate<Long, UserRateLimitSetting> userSettingsTemplate;

    @Autowired
    @Qualifier("demoCurrencyRateTemplate")
    private KafkaTemplate<String, CurrencyExchangeRate> currencyExchangeRateKafkaTemplate;

    @Scheduled(fixedRate = 2000, initialDelay = 3000)
    public void createCurrencyRateMessages() {
        CurrencyExchangeRate rate = new CurrencyExchangeRate();
        rate.setCurrency("EUR");
        rate.setRate(BigDecimal.valueOf(0.84 + 0.3 * new Random().nextDouble()));
        rate.setSourceId("DEMO SOURCE");
        rate.setTimestamp(new Date());
        currencyExchangeRateKafkaTemplate.send(CurrencyExchangeConstants.CURRENCY_EXCHANGE_RATES_TOPIC, rate.getCurrency(), rate);
    }

    public void createUserSettings() {
        UserRateLimitSetting settings = new UserRateLimitSetting();
        settings.setCurrency("EUR");
        settings.setRate(new BigDecimal("0.91"));
        settings.setUserId(1091l);
        userSettingsTemplate.send(CurrencyExchangeConstants.USER_SETTINGS_TOPIC, settings.getUserId(), settings);

        UserRateLimitSetting settings2 = new UserRateLimitSetting();
        settings2.setCurrency("EUR");
        settings2.setRate(new BigDecimal("1.1"));
        settings2.setUserId(111l);
        userSettingsTemplate.send(CurrencyExchangeConstants.USER_SETTINGS_TOPIC, settings2.getUserId(), settings2);

        UserRateLimitSetting settings3 = new UserRateLimitSetting();
        settings3.setCurrency("EUR");
        settings3.setRate(new BigDecimal("1.5"));
        settings3.setUserId(115l);
        userSettingsTemplate.send(CurrencyExchangeConstants.USER_SETTINGS_TOPIC, settings3.getUserId(), settings3);

        UserRateLimitSetting settings4 = new UserRateLimitSetting();
        settings4.setCurrency("EUR");
        settings4.setRate(new BigDecimal("1.1"));
        settings4.setUserId(11111111111l);
        userSettingsTemplate.send(CurrencyExchangeConstants.USER_SETTINGS_TOPIC, settings4.getUserId(), settings4);
    }
}
