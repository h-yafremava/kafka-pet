package com.example.producer.client;

import com.example.producer.BaseIntegrationTest;
import com.example.producer.producer.JsonDeserializer;
import com.example.producer.producer.TopicName;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class ClientIntegrationTest extends BaseIntegrationTest<ClientDto> {

    private static Consumer<String, ClientDto> consumer;

    @BeforeAll
    public static void createKafkaConsumer() {
        consumer = new KafkaConsumer<>(getProperties(KAFKA_CONTAINER.getBootstrapServers()),
                new StringDeserializer(),
                new JsonDeserializer<>(ClientDto.class));
    }

    @Test
    public void createClient() {
        final var givenClient = getClient();

        Assertions.assertTrue(
                restTemplate.postForEntity(
                                "/api/v1/clients",
                                givenClient,
                                Void.class)
                        .getStatusCode()
                        .is2xxSuccessful()
        );

        final var record = getRecord(TopicName.CLIENTS.getTopicName(), consumer);
        Assertions.assertEquals(givenClient, record.value());
        Assertions.assertEquals(givenClient.getClientId().toString(), record.key());

    }

    private ClientDto getClient() {
        return ClientDto.builder()
                .clientId(111L)
                .email("test@test.com")
                .build();
    }
}
