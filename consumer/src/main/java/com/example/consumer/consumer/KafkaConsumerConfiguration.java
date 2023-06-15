package com.example.consumer.consumer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "kafka.consumer")
@Getter
@Setter
public class KafkaConsumerConfiguration {
    @NotBlank
    private String bootstrapServers;
    @NotEmpty
    Map<String, String> groupIds;
    @NotBlank
    private String autoOffsetReset;
}
