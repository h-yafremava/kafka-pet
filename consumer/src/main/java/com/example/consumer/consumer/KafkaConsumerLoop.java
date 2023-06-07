package com.example.consumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class KafkaConsumerLoop<T> implements Runnable {

    private final KafkaConsumer<String, T> kafkaConsumer;
    private final List<String> topics;
    private final Consumer<T> consumer;

    public KafkaConsumerLoop(
            final List<String> topics,
            final KafkaConsumer<String, T> kafkaConsumer,
            final Consumer<T> consumer
    ) {
        this.kafkaConsumer = kafkaConsumer;
        this.topics = topics;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        try {
            kafkaConsumer.subscribe(topics);

            while (true) {
                final var records = kafkaConsumer.poll(Duration.ofMillis(100));
                for (final var record : records) {
                    consumer.accept(record.value());
                    log.info("Received message: " + record.value());
                }
            }
        } catch (WakeupException e) {
            // ignore for shutdown
        } finally {
            kafkaConsumer.close();
        }
    }

    public void shutdown() {
        kafkaConsumer.wakeup();
    }
}

