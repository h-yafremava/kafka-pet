package com.example.consumer.exception;


public class InvalidArgumentException extends RuntimeException{

    public InvalidArgumentException(final String message) {
        super(message);
    }
}