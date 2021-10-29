package com.hcl.currencyexchange.fcimportservice.service;

import com.hcl.currencyexchange.fcimportservice.config.Configuration;
import com.hcl.currencyexchange.fcimportservice.source.FcCurrencyExchangeRate;
import com.hcl.currencyexchange.fcimportservice.source.FcCurrencyExchangeRateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class FcImportService {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private Configuration configuration;

    @Autowired
    private KafkaTemplate<String, CurrencyExchangeRates> kafkaTemplate;

    @Autowired
    private FcCurrencyExchangeRateProxy currencyExchangeRateProxy;

    @Scheduled(fixedRateString = "${fc-import-service.interval}", initialDelay=1000)
    public void importCurrencyExchangeRates() {
        FcCurrencyExchangeRate rates = currencyExchangeRateProxy.getExchangeRates(configuration.getApiKey());
        CurrencyExchangeRates resultRates = new CurrencyExchangeRates();
        resultRates.setSourceId("freecurrencyapi");
        resultRates.setTimestamp(new Date());
        resultRates.setRates(rates.getData().entrySet().stream().map(entry -> {
            CurrencyExchangeRate rate = new CurrencyExchangeRate();
            rate.setCurrency(entry.getKey());
            rate.setRate(entry.getValue());
            return rate;
        }).collect(Collectors.toList()));
        ListenableFuture<SendResult<String, CurrencyExchangeRates>> listener = kafkaTemplate.send("currency_exchange_rates", resultRates);
        listener.addCallback(new ListenableFutureCallback() {
            @Override
            public void onSuccess(Object result) {

            }

            @Override
            public void onFailure(Throwable ex) {
                logger.log(Level.SEVERE, "Unable to send message to kafka: ", ex);
            }
        });
    }
}
