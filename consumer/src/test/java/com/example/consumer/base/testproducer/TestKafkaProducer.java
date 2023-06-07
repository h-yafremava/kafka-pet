package com.example.consumer.base.testproducer;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

@Slf4j
@Service
@AllArgsConstructor
public class TestKafkaProducer {

    @Autowired
    private KafkaProducer<String, Object> producer;

    public void send(final String topic, final String key, final Object value) {
        ProducerRecord<String, Object> record = new ProducerRecord<>(topic, key, value);
        producer.send(record);
    }

    @PreDestroy
    public void closeProducer() {
        producer.close();
    }

}
