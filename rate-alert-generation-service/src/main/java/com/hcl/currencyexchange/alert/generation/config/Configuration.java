package com.hcl.currencyexchange.alert.generation.config;

import com.hcl.currencyexchange.common.config.CommonConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("rate-alert-generation-service")
public class Configuration extends CommonConfiguration {
}
