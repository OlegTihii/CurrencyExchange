package com.insideprojects.currencyexchange.controller;

import com.google.gson.Gson;
import com.insideprojects.currencyexchange.dto.CurrencyDto;
import com.insideprojects.currencyexchange.service.CurrenciesService;
import com.insideprojects.currencyexchange.validation.InputValidation;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/currency/*")
public class CurrencyServlet extends HttpServlet {
    CurrenciesService currenciesService = new CurrenciesService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String currencyCode = request.getPathInfo().substring(1).toUpperCase();

        InputValidation.lengthCurrencyCode(currencyCode);

        CurrencyDto currency = currenciesService.findByCode(currencyCode);
        response.getWriter().write(new Gson().toJson(currency));
    }
}
