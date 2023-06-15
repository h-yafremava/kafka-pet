package com.example.consumer.transaction;

import com.example.consumer.consumer.KafkaConsumerConfiguration;
import com.example.consumer.consumer.KafkaConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import static com.example.consumer.consumer.TopicName.TRANSACTIONS;

@Slf4j
@Component
public class TransactionListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private KafkaConsumerConfiguration configuration;
    @Autowired
    private KafkaConsumerService<TransactionDto> kafkaConsumerService;
    @Autowired
    private TransactionService transactionService;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        log.info("---------TransactionListener is working-------------");
        kafkaConsumerService.subscribeToTopic(
                TRANSACTIONS.getTopicName(),
                TransactionDto.class,
                configuration.getGroupIds().get(TRANSACTIONS.getTopicName()),
                record -> transactionService.saveTransaction(record)
        );
    }
}