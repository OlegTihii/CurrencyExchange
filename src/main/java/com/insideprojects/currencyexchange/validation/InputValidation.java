package com.insideprojects.currencyexchange.validation;

import com.insideprojects.currencyexchange.exception.InvalidInputParametersException;

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

}
