package com.hcl.currencyexchange.alert.demo.config;

import com.hcl.currencyexchange.common.data.CurrencyExchangeRate;
import com.hcl.currencyexchange.common.data.UserRateLimitSetting;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@org.springframework.context.annotation.Configuration
public class ProducerConfiguration {

    @Autowired
    private Configuration configuration;

    @Bean
    public ProducerFactory<Long, UserRateLimitSetting> userSettingsProducerFactory() {
        return new DefaultKafkaProducerFactory<>(Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                configuration.getKafkaBootstrapServer(),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                LongSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class,
                ProducerConfig.ACKS_CONFIG,  "all",
                ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,  "true"));
    }

    @Bean(name = "userSettingsTemplate")
    public KafkaTemplate<Long, UserRateLimitSetting> kafkaUserSettingsTemplate() {
        return new KafkaTemplate<>(userSettingsProducerFactory());
    }

    @Bean
    public ProducerFactory<String, CurrencyExchangeRate> demoCurrencyRateProducerFactory() {
        return new DefaultKafkaProducerFactory<>(Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                configuration.getKafkaBootstrapServer(),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class,
                ProducerConfig.ACKS_CONFIG,  "all",
                ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,  "true"));
    }

    @Bean(name = "demoCurrencyRateTemplate")
    public KafkaTemplate<String, CurrencyExchangeRate> kafkaDemoCurrencyRateTemplate() {
        return new KafkaTemplate<>(demoCurrencyRateProducerFactory());
    }
}
