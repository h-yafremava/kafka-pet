package com.example.producer.testcontainer;

import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public class KafkaTestContainer extends KafkaContainer {

    private KafkaTestContainer() {
        super(DockerImageName.parse("confluentinc/cp-kafka:7.3.2"));
    }

    public static KafkaTestContainer getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public void stop() {

    }

    private static class LazyHolder {

        private static final KafkaTestContainer INSTANCE = new KafkaTestContainer();

    }
}