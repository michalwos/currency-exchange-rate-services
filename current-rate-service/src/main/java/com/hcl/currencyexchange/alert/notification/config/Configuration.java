package com.hcl.currencyexchange.alert.notification.config;

import com.hcl.currencyexchange.common.config.CommonConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("current-rate-service")
public class Configuration extends CommonConfiguration {
}
