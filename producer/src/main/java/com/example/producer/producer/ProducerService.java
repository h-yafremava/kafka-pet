package com.example.producer.producer;

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
public class ProducerService {

    @Autowired
    private final KafkaProducer<String, Object> producer;

    public void send(final String topic, final String key, final Object value) {
        log.info(value.toString());
        final var record = new ProducerRecord<>(topic, key, value);
        producer.send(record, (recordMetadata, exception) -> {
            if (exception == null) {
                log.info("Topic: " + recordMetadata.topic() +
                        ", partition: " + recordMetadata.partition() +
                        ", record written to offset " +
                        recordMetadata.offset() + " timestamp " +
                        recordMetadata.timestamp());
            } else {
                log.error(exception.getMessage());
            }
        });
    }

    @PreDestroy
    public void closeProducer() {
        producer.close();
    }
}
