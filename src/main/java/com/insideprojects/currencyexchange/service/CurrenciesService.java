package com.insideprojects.currencyexchange.service;

import com.insideprojects.currencyexchange.dao.CurrenciesDao;
import com.insideprojects.currencyexchange.dto.CurrencyDto;
import com.insideprojects.currencyexchange.mapper.CurrencyMapper;
import com.insideprojects.currencyexchange.model.Currency;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class CurrenciesService {

    CurrenciesDao currenciesDAO = new CurrenciesDao();

    public List<CurrencyDto> getAllCurrencies() {
        List<Currency> currencyList = currenciesDAO.findAll();
        return currencyList.stream()
                .map(CurrencyMapper.INSTANCE::currencyToCurrencyDto)
                .collect(Collectors.toList());
    }

    public CurrencyDto findByCode(String code) {
        Optional<Currency> currency = currenciesDAO.findByCode(code);
        return currency
                .map(CurrencyMapper.INSTANCE::currencyToCurrencyDto)
                .orElseThrow(() -> new RuntimeException("Currency not found for code: " + code));
    }

    public CurrencyDto saveCurrency(CurrencyDto currencyDto) {
        Currency currency = CurrencyMapper.INSTANCE.currencyDtoToCurrency(currencyDto);
        return CurrencyMapper.INSTANCE.currencyToCurrencyDto(currenciesDAO.save(currency));
    }


}
