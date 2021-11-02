package com.hcl.currencyexchange.alert.generation.processor;

import com.hcl.currencyexchange.common.CurrencyExchangeConstants;
import com.hcl.currencyexchange.common.data.UserRateLimitSetting;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.HashSet;
import java.util.Set;

public class CurrencyRateLimitStoreProcessor implements Processor<Long, UserRateLimitSetting, Void, Void> {

    private KeyValueStore<String, Set<Long>> store;

    @Override
    public void init(ProcessorContext<Void, Void> context) {
        store = context.getStateStore(CurrencyExchangeConstants.CURRENCY_RATE_LIMIT_STORE_NAME);
    }

    @Override
    public void process(Record<Long, UserRateLimitSetting> record) {
        String key = record.value().getCurrency() + "," + record.value().getRate().toString();
        Set<Long> userIds = store.get(key);
        if (userIds == null) {
            userIds = new HashSet<>();
        }
        userIds.add(record.value().getUserId());
        store.put(key, userIds);
    }

    @Override
    public void close() {

    }
}
