package com.insideprojects.currencyexchange.service;

import com.insideprojects.currencyexchange.dao.CurrenciesDAO;
import com.insideprojects.currencyexchange.dao.ExchangeDao;
import com.insideprojects.currencyexchange.dto.ExchangeDto;
import com.insideprojects.currencyexchange.mapper.ExchangeRateMapper;
import com.insideprojects.currencyexchange.model.Currency;
import com.insideprojects.currencyexchange.model.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

        if(exchangeRate == null){
            throw new IllegalArgumentException("Currency pair not found");
        }

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

    public ExchangeDto exchangeRateCalculation(String baseCurrency, String targetCurrency, BigDecimal amount) {
        ExchangeRate exchangeRate = exchangeDao.findByCodes(baseCurrency, targetCurrency);

        // Direct exchange rate
        if (exchangeRate != null) {
            exchangeRate.setAmount(amount);
            BigDecimal count = exchangeCalculation(exchangeRate.getRate(), amount);
            exchangeRate.setConvertedAmount(count);

            return ExchangeRateMapper.INSTANCE.exchangeToExchangeDto(exchangeRate);

            //Reverse exchange rate
        } else {
            exchangeRate = exchangeDao.findByCodes(targetCurrency, baseCurrency);
            {
                if (exchangeRate != null) {
                    exchangeRate = reverseExchangeRateToDirectExchangeRate(exchangeRate);
                    exchangeRate.setAmount(amount);
                    BigDecimal count = exchangeCalculation(exchangeRate.getRate(), amount);
                    exchangeRate.setConvertedAmount(count);

                    return ExchangeRateMapper.INSTANCE.exchangeToExchangeDto(exchangeRate);

                    // Cross exchange rate
                } else {
                    ExchangeRate usdBaseCurrency = exchangeDao.findByCodes("USD", baseCurrency);
                    ExchangeRate usdTargetCurrency = exchangeDao.findByCodes("USD", targetCurrency);
                    exchangeRate = crossExchangeRate(usdBaseCurrency, usdTargetCurrency);
                    exchangeRate.setAmount(amount);
                    BigDecimal count = exchangeCalculation(exchangeRate.getRate(), amount);
                    exchangeRate.setConvertedAmount(count);

                    return ExchangeRateMapper.INSTANCE.exchangeToExchangeDto(exchangeRate);
                }
            }
        }
    }

    private String[] currencyCodeParser(String code) {
        if (code == null || code.length() != 6) {
            throw new IllegalArgumentException("Incorrect currency pair format");
        }

        String baseCode = code.substring(0, 3);
        String targetCode = code.substring(3, 6);
        return new String[]{baseCode, targetCode};
    }

    private BigDecimal exchangeCalculation(BigDecimal rate, BigDecimal amount) {
        return rate.multiply(amount).setScale(2, RoundingMode.HALF_DOWN);
    }

    private ExchangeRate reverseExchangeRateToDirectExchangeRate(ExchangeRate exchangeRate) {
        exchangeRate.setRate(BigDecimal.ONE.divide(exchangeRate.getRate(), 2, RoundingMode.HALF_UP));

        return new ExchangeRate(
                exchangeRate.getTargetCurrencyId(),
                exchangeRate.getBaseCurrencyId(),
                exchangeRate.getRate()
        );
    }

    private ExchangeRate crossExchangeRate(ExchangeRate usdBaseCurrency, ExchangeRate usdTargetCurrency) {
        BigDecimal rate = usdBaseCurrency.getRate().divide(usdTargetCurrency.getRate(), 2, RoundingMode.HALF_UP);

        return new ExchangeRate(
                usdBaseCurrency.getTargetCurrencyId(),
                usdTargetCurrency.getTargetCurrencyId(),
                rate
        );
    }
}

