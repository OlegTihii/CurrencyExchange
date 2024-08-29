package com.insideprojects.currencyexchange.controller;


import com.google.gson.Gson;
import com.insideprojects.currencyexchange.dto.CurrencyDto;
import com.insideprojects.currencyexchange.service.CurrenciesService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/currencies")
public class CurrenciesServlet extends HttpServlet {
    CurrenciesService currenciesService = new CurrenciesService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<CurrencyDto> currencies = currenciesService.getAllCurrencies();

        response.setContentType("application/x-www-form-urlencoded");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(currencies));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String code = request.getParameter("code").toUpperCase();
        String sign = request.getParameter("sign");

        CurrencyDto currency = new CurrencyDto();
        currency.setCode(code);
        currency.setCurrencyName(name);
        currency.setSign(sign);

        currency = currenciesService.saveCurrency(currency);

        response.setContentType("application/x-www-form-urlencoded");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(new Gson().toJson(currency));
    }
}
