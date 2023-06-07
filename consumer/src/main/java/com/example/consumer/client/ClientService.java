package com.example.consumer.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    public void saveClient(final ClientDto client) {
        clientRepository.save(clientMapper.toEntity(client));
    }
}
