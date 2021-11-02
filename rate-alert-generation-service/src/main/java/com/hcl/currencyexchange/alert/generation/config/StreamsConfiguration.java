package com.hcl.currencyexchange.alert.generation.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hcl.currencyexchange.alert.generation.processor.CurrencyRateLimitStoreProcessor;
import com.hcl.currencyexchange.common.CurrencyExchangeConstants;
import com.hcl.currencyexchange.common.data.CurrencyExchangeRate;
import com.hcl.currencyexchange.common.data.CurrencyRateAlert;
import com.hcl.currencyexchange.common.data.UserRateLimitSetting;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.Stores;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.KafkaStreamsInfrastructureCustomizer;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanConfigurer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class StreamsConfiguration {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Bean(name =
            KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs() {
        return new KafkaStreamsConfiguration(Map.of(
                StreamsConfig.APPLICATION_ID_CONFIG, "rateAlertsGeneration",
                StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class.getName()
        ));
    }

    @Bean
    public StreamsBuilderFactoryBeanConfigurer streamsBuilderFactoryBeanConfigurer() {
        return streamsBuilderFactoryBean ->
                streamsBuilderFactoryBean.setInfrastructureCustomizer(new KafkaStreamsInfrastructureCustomizer() {
                    @Override
                    public void configureBuilder(StreamsBuilder builder) {
                        builder.addGlobalStore(
                                Stores.keyValueStoreBuilder(
                                        Stores.persistentKeyValueStore(CurrencyExchangeConstants.CURRENCY_RATE_LIMIT_STORE_NAME),
                                        Serdes.String(),
                                        Serdes.serdeFrom(new JsonSerializer<>(new TypeReference<Set<Long>>(){}), new JsonDeserializer<>(new TypeReference<Set<Long>>(){}))),
                                CurrencyExchangeConstants.USER_SETTINGS_TOPIC,
                                Consumed.with(Serdes.Long(), new JsonSerde<>(UserRateLimitSetting.class)),
                                () -> new CurrencyRateLimitStoreProcessor());
                    }
                });
    }

    @Bean
    public KStream<String, CurrencyExchangeRate> kStream(StreamsBuilder kStreamBuilder) {
        KStream<String, CurrencyExchangeRate> stream = kStreamBuilder.stream(CurrencyExchangeConstants.CURRENCY_EXCHANGE_RATES_TOPIC,
                Consumed.with(Serdes.String(), new JsonSerde<>(CurrencyExchangeRate.class)));
        stream.transform(() -> new Transformer<String, CurrencyExchangeRate, KeyValue<Long,CurrencyRateAlert>>() {
            private KeyValueStore<String, Set<Long>> store;
            private ProcessorContext context;

            @Override
            public void init(ProcessorContext processorContext) {
                this.context = processorContext;
                store = context.getStateStore(CurrencyExchangeConstants.CURRENCY_RATE_LIMIT_STORE_NAME);
            }

            @Override
            public KeyValue<Long,CurrencyRateAlert> transform(String s, CurrencyExchangeRate currencyExchangeRate) {
                String currentCurrencyRate = currencyExchangeRate.getCurrency() + "," + currencyExchangeRate.getRate().toString();
                String lowerCurrencyRate = currencyExchangeRate.getCurrency() + "," + "0";
                try (KeyValueIterator<String, Set<Long>> it = store.range(lowerCurrencyRate, currentCurrencyRate)){
                    while (it.hasNext()) {
                         KeyValue<String, Set<Long>> keyValue= it.next();
                        Set<Long> userIds = keyValue.value;
                        logger.log(Level.INFO, "Key="+keyValue.key + ", Value="+keyValue.value);
                        userIds.forEach(userId -> {
                            CurrencyRateAlert alert = new CurrencyRateAlert();
                            alert.setCurrency(currencyExchangeRate.getCurrency());
                            alert.setRate(currencyExchangeRate.getRate());
                            alert.setUserId(userId);
                            context.forward(userId, alert);
                        });
                    }
                }
                return null;
            }

            @Override
            public void close() {

            }
        }).to(CurrencyExchangeConstants.RATE_ALERTS_TOPIC, Produced.with(Serdes.Long(), new JsonSerde<>(CurrencyRateAlert.class)));
        stream.print(Printed.toSysOut());
        return stream;
    }
}