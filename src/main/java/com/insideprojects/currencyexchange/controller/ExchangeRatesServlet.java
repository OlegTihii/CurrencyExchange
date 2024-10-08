package com.insideprojects.currencyexchange.controller;

import com.google.gson.Gson;
import com.insideprojects.currencyexchange.dto.ExchangeDto;
import com.insideprojects.currencyexchange.service.ExchangeService;
import com.insideprojects.currencyexchange.validation.InputValidation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(urlPatterns = "/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    ExchangeService exchangeService = new ExchangeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ExchangeDto> allExchangeRate = exchangeService.getAllExchangeRate();
        response.getWriter().write(new Gson().toJson(allExchangeRate));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String baseCurrency = request.getParameter("baseCurrencyCode");
        String targetCurrency = request.getParameter("targetCurrencyCode");
        BigDecimal rate = new BigDecimal(request.getParameter("rate"));

        InputValidation.lengthCurrencyCode(baseCurrency);
        InputValidation.lengthCurrencyCode(targetCurrency);

        ExchangeDto exchangeDto = exchangeService.saveExchangeRate(baseCurrency, targetCurrency, rate);

        response.getWriter().write(new Gson().toJson(exchangeDto));
    }
}
