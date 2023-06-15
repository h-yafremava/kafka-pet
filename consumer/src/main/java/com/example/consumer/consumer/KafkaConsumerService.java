package com.example.consumer.consumer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaConsumerService<T> {

    @Autowired
    private KafkaConsumerFactory<T> kafkaConsumerFactory;

    public void subscribeToTopic(
            final String topic,
            final Class<T> targetType,
            final String groupId,
            final Consumer<T> consumer
    ) {
        final var topics = Collections.singletonList(topic);
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final var kafkaConsumer = kafkaConsumerFactory.createConsumer(groupId, targetType);
        final var consumerLoop = new KafkaConsumerLoop<T>(topics, kafkaConsumer, consumer);
        executor.submit(consumerLoop);

        addShutdownHook(consumerLoop, executor);
    }

    private void addShutdownHook(
            final KafkaConsumerLoop<T> kafkaConsumer,
            final ExecutorService executor
    ) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            kafkaConsumer.shutdown();
            executor.shutdown();
            try {
                executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }
}
