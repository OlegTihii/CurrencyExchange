package com.insideprojects.currencyexchange.exception;

public class CurrencyPairAlreadyExistsException extends RuntimeException {

    public CurrencyPairAlreadyExistsException(String message) {
        super(message);
    }
}
