package com.example.consumer.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@Component
public class KafkaConsumerFactory<T> {

    @Autowired
    private KafkaConsumerConfiguration configuration;

    public KafkaConsumer<String, T> createConsumer(
            final String consumerGroupId,
            final Class<T> targetType
    ) {
        return new KafkaConsumer<>(
                getConsumerProps(consumerGroupId),
                new StringDeserializer(),
                new JsonDeserializer<T>(targetType)
        );
    }

    private Properties getConsumerProps(
            final String consumerGroupId
    ) {
        final var props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, configuration.getBootstrapServers());
        props.put(GROUP_ID_CONFIG, consumerGroupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return props;
    }

}
