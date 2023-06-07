package com.example.consumer.transaction;

import com.example.consumer.client.ClientEntity;
import com.example.consumer.client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionMapper transactionMapper;

    @Transactional
    public void saveTransaction(final TransactionDto transaction) {
        final var client = clientRepository.findById(transaction.getClientId())
                .orElseGet(() ->
                        clientRepository.save(
                                ClientEntity.builder()
                                        .id(transaction.getClientId())
                                        .blank(true)
                                        .build()
                        )
                );
        final var entity = transactionMapper.toEntity(transaction, client);

        transactionRepository.save(entity);
    }
}
