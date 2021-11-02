package com.hcl.currencyexchange.alert.demo.service;

import com.hcl.currencyexchange.alert.demo.config.Configuration;
import com.hcl.currencyexchange.alert.demo.source.FcCurrencyExchangeRate;
import com.hcl.currencyexchange.alert.demo.source.FcCurrencyExchangeRateProxy;
import com.hcl.currencyexchange.common.CurrencyExchangeConstants;
import com.hcl.currencyexchange.common.data.CurrencyExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.logging.Logger;

@Service
public class FcImportService {

    private static final String FREECURRENCYAPI = "freecurrencyapi";

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private Configuration configuration;

    @Autowired
    private KafkaTemplate<String, CurrencyExchangeRate> kafkaTemplate;

    @Autowired
    private FcCurrencyExchangeRateProxy currencyExchangeRateProxy;

    @Scheduled(fixedRateString = "${fc-import-service.interval}", initialDelay=1000)
    public void importCurrencyExchangeRates() {
        FcCurrencyExchangeRate rates = currencyExchangeRateProxy.getExchangeRates(configuration.getApiKey());
        rates.getData().entrySet().forEach(entry -> {
            CurrencyExchangeRate rate = new CurrencyExchangeRate();
            rate.setCurrency(entry.getKey());
            rate.setSourceId(FREECURRENCYAPI);
            rate.setRate(entry.getValue());
            rate.setTimestamp(new Date());
            kafkaTemplate.send(CurrencyExchangeConstants.CURRENCY_EXCHANGE_RATES_TOPIC, FREECURRENCYAPI, rate);
        });
    }
}
