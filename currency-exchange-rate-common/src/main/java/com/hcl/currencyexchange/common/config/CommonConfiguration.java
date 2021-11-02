package com.hcl.currencyexchange.common.config;

public abstract class CommonConfiguration {
    private String kafkaBootstrapServer;

    public String getKafkaBootstrapServer() {
        return kafkaBootstrapServer;
    }

    public void setKafkaBootstrapServer(String kafkaBootstrapServer) {
        this.kafkaBootstrapServer = kafkaBootstrapServer;
    }
}
