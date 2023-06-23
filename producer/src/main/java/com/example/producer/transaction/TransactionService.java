package com.example.producer.transaction;

import com.example.producer.producer.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.producer.producer.TopicName.TRANSACTIONS;

@Service
public class TransactionService {

    @Autowired
    private ProducerService producerService;

    public void sendTransaction(final TransactionDto transaction) {
        producerService.send(
                TRANSACTIONS.getTopicName(),
                transaction.getClientId().toString(),
                transaction
        );
    }
}
