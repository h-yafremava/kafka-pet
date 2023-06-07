package com.example.producer.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "/api/v1/transactions", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TransactionController {

    @Autowired
    final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(ACCEPTED)
    public void createTransaction(@Valid @RequestBody final TransactionDto transactionDto) {
        transactionService.sendTransaction(transactionDto);
    }
}

