package com.hcl.currencyexchange.fcimportservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("fc-import-service")
public class Configuration {
    private long interval;
    private String apiKey;
    private String apiUrl;
    private String kafkaBootstrapServer;

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getKafkaBootstrapServer() {
        return kafkaBootstrapServer;
    }

    public void setKafkaBootstrapServer(String kafkaBootstrapServer) {
        this.kafkaBootstrapServer = kafkaBootstrapServer;
    }
}
