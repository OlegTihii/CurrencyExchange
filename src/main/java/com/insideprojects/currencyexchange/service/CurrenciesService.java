package com.insideprojects.currencyexchange.service;

import com.insideprojects.currencyexchange.dao.CurrenciesDAO;
import com.insideprojects.currencyexchange.dto.CurrencyDto;
import com.insideprojects.currencyexchange.mapper.CurrencyMapper;
import com.insideprojects.currencyexchange.model.Currency;

import java.util.List;
import java.util.stream.Collectors;


public class CurrenciesService {

    CurrenciesDAO currenciesDAO = new CurrenciesDAO();

    public List<CurrencyDto> getAllCurrencies() {
        List<Currency> currencyList = currenciesDAO.findAll();
        return currencyList.stream()
                .map(CurrencyMapper.INSTANCE::currencyToCurrencyDto)
                .collect(Collectors.toList());
    }

    public CurrencyDto findByCode(String code) {
        Currency currency = currenciesDAO.findByCode(code);
        return CurrencyMapper.INSTANCE.currencyToCurrencyDto(currency);
    }

    public CurrencyDto saveCurrency(CurrencyDto currencyDto) {
        Currency currency = CurrencyMapper.INSTANCE.currencyDtoToCurrency(currencyDto);
        return CurrencyMapper.INSTANCE.currencyToCurrencyDto(currenciesDAO.save(currency));
    }


}
