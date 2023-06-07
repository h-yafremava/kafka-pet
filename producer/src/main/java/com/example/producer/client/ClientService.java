package com.example.producer.client;

import com.example.producer.producer.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.producer.producer.TopicName.CLIENTS;

@Service
public class ClientService {

    @Autowired
    private ProducerService producerService;

    public void sendClient(final ClientDto client) {
        producerService.send(
                CLIENTS.getTopicName(),
                client.getClientId().toString(),
                client
        );
    }
}
