package com.hcl.currencyexchange.alert.notification.service;

import com.hcl.currencyexchange.common.CurrencyExchangeConstants;
import com.hcl.currencyexchange.common.data.CurrencyExchangeRate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CurrentRateService {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @KafkaListener(topics = CurrencyExchangeConstants.CURRENCY_EXCHANGE_RATES_TOPIC, groupId = "current-rate-service")
    public void showCurrentRateForEur(CurrencyExchangeRate rate) {
        if ("EUR".equals(rate.getCurrency())) {
            logger.log(Level.INFO, "\nEUR: " + rate.getRate() + "" +
                    "\n(" + rate.getSourceId() + ", " + LocalDateTime.ofInstant(rate.getTimestamp().toInstant(), ZoneId.systemDefault()) + ")");
        }

    }
}
