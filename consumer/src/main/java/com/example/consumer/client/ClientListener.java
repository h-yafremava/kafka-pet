package com.example.consumer.client;

import com.example.consumer.consumer.KafkaConsumerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import static com.example.consumer.consumer.TopicName.CLIENTS;


@Slf4j
@Component
@AllArgsConstructor
public class ClientListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private KafkaConsumerService<ClientDto> kafkaConsumerService;
    @Autowired
    private ClientService clientService;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        log.info("----------------ClientListener is working---------------");
        kafkaConsumerService.subscribeToTopic(
                CLIENTS.getTopicName(),
                ClientDto.class,
                "client-group",
                record -> clientService.saveClient(record)
        );
    }
}