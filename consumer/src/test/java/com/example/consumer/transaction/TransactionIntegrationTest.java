package com.example.consumer.transaction;

import com.example.consumer.base.BaseIntegrationTest;
import com.example.consumer.client.ClientEntity;
import com.example.consumer.client.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class TransactionIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ClientRepository clientRepository;

    @Value("${kafka.topic.name.transaction}")
    private String topicName;

    @Autowired
    private TransactionRepository transactionRepository;
    @Test
    public void createTransaction() {
        final var givenTransaction = getTransaction();
        kafkaProducer.send(
                topicName,
                givenTransaction.getClientId().toString(),
                givenTransaction
        );

        await().until(() -> transactionRepository.existsByClientId(givenTransaction.getClientId()));

        final var entity = getTransactionEntity(givenTransaction.getClientId());
        Assertions.assertEquals(givenTransaction.getClientId(), entity.getClient().getId());
        Assertions.assertEquals(givenTransaction.getCreatedAt(), entity.getCreatedAt());
        Assertions.assertEquals(givenTransaction.getTransactionType(), entity.getTransactionType().getName());
        Assertions.assertEquals(givenTransaction.getPrice(), entity.getPrice());
        Assertions.assertEquals(givenTransaction.getBank(), entity.getBank());
        Assertions.assertEquals(givenTransaction.getQuantity(), entity.getQuantity());

        final var clientEntity = entity.getClient();
        Assertions.assertEquals(givenTransaction.getClientId(), clientEntity.getId());
        Assertions.assertTrue(clientEntity.isBlank());
        Assertions.assertNull(clientEntity.getEmail());

    }

    @Test
    public void createTransactionIfClientExists() {
        final var client = getClient();
        clientRepository.save(client);

        final var givenTransaction = getTransaction();

        kafkaProducer.send(
                topicName,
                givenTransaction.getClientId().toString(),
                givenTransaction
        );

        await().until(() -> transactionRepository.existsByClientId(givenTransaction.getClientId()));

        final var entity = getTransactionEntity(givenTransaction.getClientId());
        Assertions.assertEquals(givenTransaction.getClientId(), entity.getClient().getId());
        Assertions.assertEquals(givenTransaction.getCreatedAt(), entity.getCreatedAt());
        Assertions.assertEquals(givenTransaction.getTransactionType(), entity.getTransactionType().getName());
        Assertions.assertEquals(givenTransaction.getPrice(), entity.getPrice());
        Assertions.assertEquals(givenTransaction.getBank(), entity.getBank());
        Assertions.assertEquals(givenTransaction.getQuantity(), entity.getQuantity());

        final var clientEntity = entity.getClient();
        Assertions.assertEquals(givenTransaction.getClientId(), clientEntity.getId());
        Assertions.assertEquals(client.getEmail(), clientEntity.getEmail());
        Assertions.assertFalse(clientEntity.isBlank());

    }

    private TransactionDto getTransaction() {
        return TransactionDto.builder()
                .transactionType(TransactionType.INCOME.getName())
                .price(100.00)
                .clientId(111L)
                .quantity(1)
                .bank("bank1")
                .createdAt(LocalDateTime.of(2022, 10, 22, 0, 0, 0))
                .build();
    }

    private ClientEntity getClient() {
        return ClientEntity.builder()
                .id(111L)
                .email("test@test.com")
                .build();
    }

    private TransactionEntity getTransactionEntity(final Long clientId) {
        return transactionRepository.findByClientId(clientId);
    }

}
