package com.example.producer;

import com.example.producer.testcontainer.KafkaTestContainer;
import org.apache.kafka.clients.consumer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT
)
@Testcontainers
public class BaseIntegrationTest<T> {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Container
    public static final KafkaContainer KAFKA_CONTAINER = KafkaTestContainer.getInstance();


    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("kafka.producer.bootstrap-servers", KAFKA_CONTAINER::getBootstrapServers);
    }
    public ConsumerRecord<String, T> getRecord(
            final String topic,
            final Consumer<String, T> consumer
    ) {
        consumer.subscribe(Collections.singletonList(topic));
        ConsumerRecords<String, T> records = null;
        while (records == null || records.count() == 0) {
            records = consumer.poll(Duration.ofMillis(100L));
        }
        consumer.close();
        return records.iterator().next();
    }

    protected static Properties getProperties(String bootstrapServers) {
        Properties properties = new Properties();
        properties.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(GROUP_ID_CONFIG, "group");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return properties;
    }
}
