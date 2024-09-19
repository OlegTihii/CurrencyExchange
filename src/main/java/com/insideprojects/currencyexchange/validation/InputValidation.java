package com.insideprojects.currencyexchange.validation;

import com.insideprojects.currencyexchange.exception.InvalidInputParametersException;

import java.math.BigDecimal;

public class InputValidation {
    public static void lengthCurrencyCode(String code) throws InvalidInputParametersException {
        if (code.length() != 3 || code.isBlank()) {
            throw new InvalidInputParametersException("The currency code must consist of three characters");
        }
    }

    public static void lengthCurrencySign(String sign) throws InvalidInputParametersException {
        if (sign.length() != 1 || sign.isBlank()) {
            throw new InvalidInputParametersException("The length of the currency symbol must not exceed one character");
        }
    }

    public static void lengthCurrencyPair(String codePair) throws InvalidInputParametersException {
        if (codePair.length() != 6 || codePair.isBlank()) {
            throw new InvalidInputParametersException("The currency pair must be 6 characters long");
        }
    }

    public static void validationAmount(BigDecimal amount) throws InvalidInputParametersException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidInputParametersException("Amount must be greater than zero");
        }
    }
}
