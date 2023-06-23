package com.example.consumer.base.testproducer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
public class KafkaConfig {

    @Value("${kafka.producer.bootstrap-servers}")
    private String server;

    @Bean
    public KafkaProducer<String, Object> kafkaProducer() {
        Properties properties = new Properties();
        properties.put(BOOTSTRAP_SERVERS_CONFIG, server);
        properties.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(VALUE_SERIALIZER_CLASS_CONFIG, KafkaJsonSerializer.class.getName());
        return new KafkaProducer<>(properties);
    }
}
