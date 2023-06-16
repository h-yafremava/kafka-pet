package com.example.consumer.client;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;



@Slf4j
@Component
@AllArgsConstructor
public class ClientListener{

    @Autowired
    private ClientService clientService;

    @KafkaListener(
            topics = "${kafka.topic.name.client}",
            properties = {"spring.json.value.default.type=com.example.consumer.client.ClientDto"}
    )
    public void saveClient(final ClientDto clientDto) {
        clientService.saveClient(clientDto);
    }
}