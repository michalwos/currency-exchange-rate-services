package com.hcl.currencyexchange.alert.notification.config;

import com.hcl.currencyexchange.common.config.CommonConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("rate-alert-service")
public class Configuration extends CommonConfiguration {
}
