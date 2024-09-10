package com.insideprojects.currencyexchange.controller;

import com.google.gson.Gson;
import com.insideprojects.currencyexchange.dto.ExchangeDto;
import com.insideprojects.currencyexchange.service.ExchangeService;
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

        ExchangeDto exchangeDto = exchangeService.exchangeRateCalculation(baseCurrency, targetCurrency, amountCurrencyConverted);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(exchangeDto));
    }
}
