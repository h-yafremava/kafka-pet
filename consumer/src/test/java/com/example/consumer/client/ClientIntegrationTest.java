package com.example.consumer.client;

import com.example.consumer.base.BaseIntegrationTest;
import com.example.consumer.consumer.TopicName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class ClientIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ClientRepository clientRepository;
    @Test
    public void createClient() {
        final var givenClient = getClient();
        kafkaProducer.send(
                TopicName.CLIENTS.getTopicName(),
                givenClient.getClientId().toString(),
                givenClient
        );

        await().until(() -> clientRepository.existsById(givenClient.getClientId()));
        final var entity = getClientEntity(givenClient.getClientId());
        Assertions.assertEquals(givenClient.getClientId(), entity.getId());
        Assertions.assertEquals(givenClient.getEmail(), entity.getEmail());

    }

    private ClientDto getClient() {
        return ClientDto.builder()
                .clientId(111L)
                .email("test@test.com")
                .build();
    }


    private ClientEntity getClientEntity(final Long id) {
        return clientRepository.findById(id)
                .orElse(null);
    }
}
