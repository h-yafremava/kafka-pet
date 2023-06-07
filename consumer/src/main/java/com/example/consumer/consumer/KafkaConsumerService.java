package com.example.consumer.consumer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaConsumerService<T> {

    @Autowired
    private KafkaConsumerConfiguration configuration;

    @Autowired
    private KafkaConsumerFactory<T> kafkaConsumerFactory;

    public void subscribeToTopic(
            final String topic,
            final Class<T> targetType,
            final String groupId,
            final Consumer<T> consumer
    ) {
        final var topics = Collections.singletonList(topic);
        final ExecutorService executor = Executors.newFixedThreadPool(configuration.getNumConsumers());
        final List<KafkaConsumerLoop<T>> kafkaConsumers = new ArrayList<>();
        for (int i = 0; i < configuration.getNumConsumers(); i++) {
            final var kafkaConsumer = kafkaConsumerFactory.createConsumer(groupId, targetType);
            final var consumerLoop = new KafkaConsumerLoop<T>(topics, kafkaConsumer, consumer);
            kafkaConsumers.add(consumerLoop);
            executor.submit(consumerLoop);
        }

        addShutdownHook(kafkaConsumers, executor);
    }

    private void addShutdownHook(
            final List<KafkaConsumerLoop<T>> kafkaConsumers,
            final ExecutorService executor
    ) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (final KafkaConsumerLoop<T> item : kafkaConsumers) {
                item.shutdown();
            }
            executor.shutdown();
            try {
                executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }
}
