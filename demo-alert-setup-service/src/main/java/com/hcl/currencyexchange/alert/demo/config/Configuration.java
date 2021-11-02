package com.hcl.currencyexchange.alert.demo.config;

import com.hcl.currencyexchange.common.config.CommonConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("demo-alert-setup-service")
public class Configuration extends CommonConfiguration {
}
