package com.example.producer.producer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Component
@ConfigurationProperties(prefix = "kafka.producer")
@Getter
@Setter
public class KafkaProducerConfiguration {
    @NotBlank
    private String bootstrapServers;
}
