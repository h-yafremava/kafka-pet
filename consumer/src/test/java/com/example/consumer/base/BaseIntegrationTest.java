package com.example.consumer.base;

import com.example.consumer.base.testproducer.TestKafkaProducer;
import com.example.consumer.testcontainer.DbContainer;
import com.example.consumer.testcontainer.KafkaTestContainer;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;


@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
public abstract class BaseIntegrationTest {

    @Autowired
    protected TestKafkaProducer kafkaProducer;

    static final List<String> TABLES = List.of(
            "transaction",
            "client"
    );

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Container
    public static final KafkaContainer KAFKA_CONTAINER = KafkaTestContainer.getInstance();
    @Container
    public static final DbContainer COCKROACHDB_CONTAINER = DbContainer.getInstance();

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("kafka.consumer.bootstrap-servers", KAFKA_CONTAINER::getBootstrapServers);
        registry.add("kafka.producer.bootstrap-servers", KAFKA_CONTAINER::getBootstrapServers);
        registry.add("spring.datasource.url", COCKROACHDB_CONTAINER::getJdbcUrl);
    }

    @AfterEach
    @Transactional
    public void cleanup() {
        TABLES.forEach(table -> jdbcTemplate.update("delete from " + table));
    }
}
