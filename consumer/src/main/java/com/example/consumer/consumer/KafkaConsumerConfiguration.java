package com.example.consumer.consumer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Component
@ConfigurationProperties(prefix = "kafka.consumer")
@Getter
@Setter
public class KafkaConsumerConfiguration {
    @NotBlank
    private String bootstrapServers;
    @NotBlank
    private Integer numConsumers;
}
