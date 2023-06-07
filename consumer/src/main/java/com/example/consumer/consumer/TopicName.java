package com.example.consumer.consumer;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TopicName {
    CLIENTS("clients"),
    TRANSACTIONS("transactions");

    private final String topicName;

}
