package com.example.consumer.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class TransactionListener {

    @Autowired
    private TransactionService transactionService;

    @KafkaListener(
            topics = "${kafka.topic.name.transaction}",
            properties = {"spring.json.value.default.type=com.example.consumer.transaction.TransactionDto"}
    )
    public void onApplicationEvent(final TransactionDto transactionDto) {

        transactionService.saveTransaction(transactionDto);
    }
}