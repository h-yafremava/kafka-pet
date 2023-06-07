package com.example.producer.client;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/clients", produces = APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ClientController {

    @Autowired
    final ClientService clientService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void createClient(@Valid @RequestBody final ClientDto clientDto) {
        clientService.sendClient(clientDto);

    }
}
