package com.insideprojects.currencyexchange.service;

import com.insideprojects.currencyexchange.dao.CurrenciesDAO;
import com.insideprojects.currencyexchange.dao.ExchangeDao;
import com.insideprojects.currencyexchange.dto.ExchangeDto;
import com.insideprojects.currencyexchange.mapper.ExchangeRateMapper;
import com.insideprojects.currencyexchange.model.Currency;
import com.insideprojects.currencyexchange.model.ExchangeRate;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ExchangeService {

    ExchangeDao exchangeDao = new ExchangeDao();
    CurrenciesDAO currenciesDAO = new CurrenciesDAO();

    public List<ExchangeDto> getAllExchangeRate() {
        List<ExchangeRate> exchangeRateList = exchangeDao.findAll();
        return exchangeRateList.stream()
                .map(ExchangeRateMapper.INSTANCE::exchangeToExchangeDto)
                .collect(Collectors.toList());

    }

    public ExchangeDto findByCode(String code) {
        String[] strings = currencyCodeParser(code);
        String baseCode = strings[0];
        String targetCode = strings[1];
        ExchangeRate exchangeRate = exchangeDao.findByCodes(baseCode, targetCode);
        return ExchangeRateMapper.INSTANCE.exchangeToExchangeDto(exchangeRate);
    }

    public ExchangeDto saveExchangeRate(String baseCode, String targetCode, BigDecimal rate) {
        Currency baseCurrency = currenciesDAO.findByCode(baseCode);
        Currency targetCurrency = currenciesDAO.findByCode(targetCode);

        if (baseCurrency == null || targetCurrency == null) {
            throw new IllegalArgumentException("Currency not found");
        }

        ExchangeRate exchangeRate = new ExchangeRate(baseCurrency, targetCurrency, rate);

        exchangeDao.saveExchangeRate(exchangeRate);
        return ExchangeRateMapper.INSTANCE.exchangeToExchangeDto(exchangeRate);
    }

    public ExchangeDto updateExchangeRate(String code, BigDecimal rate) {
        String[] currencyCodes = currencyCodeParser(code);
        String baseCode = currencyCodes[0];
        String targetCode = currencyCodes[1];
        ExchangeRate exchangeRate = exchangeDao.findByCodes(baseCode, targetCode);

        if (exchangeRate == null) {
            throw new IllegalArgumentException("Currency pair not found");
        }

        exchangeRate.setRate(rate);
        ExchangeRate updatedExchangeRate = exchangeDao.updateExchangeRate(exchangeRate);

        return ExchangeRateMapper.INSTANCE.exchangeToExchangeDto(updatedExchangeRate);
    }

    public ExchangeDto exchangeRateCalculation(ExchangeDto exchangeDto, int amount) {
        return null;
    }

    private String[] currencyCodeParser(String code) {
        if (code == null || code.length() != 6) {
            throw new IllegalArgumentException("Incorrect currency pair format");
        }

        String baseCode = code.substring(0, 3);
        String targetCode = code.substring(3, 6);
        return new String[]{baseCode, targetCode};
    }
}

