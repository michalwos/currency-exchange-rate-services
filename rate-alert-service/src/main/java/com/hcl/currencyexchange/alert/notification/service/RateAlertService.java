package com.hcl.currencyexchange.alert.notification.service;

import com.hcl.currencyexchange.common.CurrencyExchangeConstants;
import com.hcl.currencyexchange.common.data.CurrencyRateAlert;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RateAlertService {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @KafkaListener(topics = CurrencyExchangeConstants.RATE_ALERTS_TOPIC, groupId = "rate-alert-service")
    public void showAlert(CurrencyRateAlert alert) {
        logger.log(Level.INFO, "\nAlert for user " + alert.getUserId() + ". Currency rate reached defined limit: " + alert.getCurrency() + "=" + alert.getRate());
    }
}
