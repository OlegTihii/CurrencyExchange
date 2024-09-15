package com.insideprojects.currencyexchange.controller;

import com.google.gson.Gson;
import com.insideprojects.currencyexchange.dto.ExchangeDto;
import com.insideprojects.currencyexchange.service.ExchangeService;
import com.insideprojects.currencyexchange.validation.InputValidation;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(urlPatterns = "/exchange")
public class CurrencyExchangeConversionServlet extends HttpServlet {
    ExchangeService exchangeService = new ExchangeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String baseCurrency = request.getParameter("from");
        String targetCurrency = request.getParameter("to");
        BigDecimal amountCurrencyConverted = new BigDecimal(request.getParameter("amount"));

        InputValidation.lengthCurrencyCode(baseCurrency);
        InputValidation.lengthCurrencyCode(targetCurrency);
        InputValidation.validationAmount(amountCurrencyConverted);

        ExchangeDto exchangeDto = exchangeService.exchangeRateCalculation(baseCurrency, targetCurrency, amountCurrencyConverted);

        response.getWriter().write(new Gson().toJson(exchangeDto));
    }
}
