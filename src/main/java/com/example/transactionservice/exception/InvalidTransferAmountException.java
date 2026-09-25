package com.example.transactionservice.exception;

public class InvalidTransferAmountException extends RuntimeException {

    public InvalidTransferAmountException(String message) {
        super(message);
    }
}