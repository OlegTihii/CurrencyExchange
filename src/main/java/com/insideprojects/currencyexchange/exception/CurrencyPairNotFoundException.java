package com.insideprojects.currencyexchange.exception;

public class CurrencyPairNotFoundException extends RuntimeException {

    public CurrencyPairNotFoundException(String message) {
        super(message);
    }
}
