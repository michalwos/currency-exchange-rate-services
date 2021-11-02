package com.hcl.currencyexchange.alert.notification.config;

import com.hcl.currencyexchange.common.data.CurrencyExchangeRate;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ConsumerConfiguration {

    @Autowired
    private com.hcl.currencyexchange.alert.notification.config.Configuration configuration;

    @Bean
    public ConsumerFactory<String, CurrencyExchangeRate> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                configuration.getKafkaBootstrapServer());
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "current-rate-service");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(CurrencyExchangeRate.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CurrencyExchangeRate> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, CurrencyExchangeRate> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
