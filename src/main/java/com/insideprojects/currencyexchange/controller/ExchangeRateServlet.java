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

@WebServlet(urlPatterns = "/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    ExchangeService exchangeService = new ExchangeService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String currencyPairCode = request.getPathInfo().substring(1).toUpperCase();

        InputValidation.lengthCurrencyPair(currencyPairCode);

        ExchangeDto exchangeDto = exchangeService.findByCode(currencyPairCode);
        response.getWriter().write(new Gson().toJson(exchangeDto));
    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String currencyPairCode = request.getPathInfo().substring(1).toUpperCase();
        String tmp = request.getReader().readLine();
        String rateString = tmp.replace("rate=", "");
        BigDecimal rate = new BigDecimal(rateString);

        InputValidation.lengthCurrencyPair(currencyPairCode);

        ExchangeDto exchangeDto = exchangeService.updateExchangeRate(currencyPairCode, rate);

        response.getWriter().write(new Gson().toJson(exchangeDto));
    }
}
