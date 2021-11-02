package com.hcl.currencyexchange.alert.demo.source;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "fc-exchange-rates", url="${fc-import-service.apiUrl}")
public interface FcCurrencyExchangeRateProxy {

    @GetMapping("/latest?apikey={apiKey}&base_currency=USD")
    FcCurrencyExchangeRate getExchangeRates(@RequestParam String apiKey);
}
