package com.example.producer.transaction;

import com.example.producer.BaseIntegrationTest;
import com.example.producer.producer.JsonDeserializer;
import com.example.producer.producer.TopicName;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class TransactionIntegrationTest extends BaseIntegrationTest<TransactionDto> {

    private static Consumer<String, TransactionDto> consumer;

    @BeforeAll
    public static void createKafkaConsumer() {
        consumer = new KafkaConsumer<>(getProperties(KAFKA_CONTAINER.getBootstrapServers()),
                new StringDeserializer(),
                new JsonDeserializer<>(TransactionDto.class));
    }

    @Test
    public void createTransaction() {
        final var givenTransaction = getTransaction();

        Assertions.assertTrue(
                restTemplate.postForEntity(
                                "/api/v1/transactions",
                                givenTransaction,
                                Void.class)
                        .getStatusCode()
                        .is2xxSuccessful()
        );

        final var record = getRecord(TopicName.TRANSACTIONS.getTopicName(), consumer);
        Assertions.assertEquals(givenTransaction, record.value());
        Assertions.assertEquals(givenTransaction.getClientId().toString(), record.key());

    }

    private TransactionDto getTransaction() {
        return TransactionDto.builder()
                .transactionType(TransactionType.INCOME)
                .price(100.00)
                .clientId(111L)
                .quantity(1)
                .bank("bank1")
                .createdAt(LocalDateTime.of(2022, 10, 22, 0, 0, 0))
                .build();
    }
}
