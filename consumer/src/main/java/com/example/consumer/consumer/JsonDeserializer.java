package com.example.consumer.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;


public class JsonDeserializer<T> implements Deserializer<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Class<T> targetType;

    public JsonDeserializer(final Class<T> targetType) {
        this.targetType = targetType;
    }

    @Override
    public T deserialize(final String topic, final byte[] data) {
        try {
            if (data == null) {
                return null;
            }
            return objectMapper.readValue(data, targetType);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
