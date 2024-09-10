package com.insideprojects.currencyexchange.dto;

import com.insideprojects.currencyexchange.model.Currency;

import java.math.BigDecimal;

public class ExchangeDto {
    private int id;
    private Currency baseCurrencyId;
    private Currency targetCurrencyId;
    private BigDecimal rate;
    private BigDecimal amount;
    private BigDecimal convertedAmount;

    public ExchangeDto() {
    }

    public ExchangeDto(int id, Currency baseCurrencyId, Currency targetCurrencyId, BigDecimal rate) {
        this.id = id;
        this.baseCurrencyId = baseCurrencyId;
        this.targetCurrencyId = targetCurrencyId;
        this.rate = rate;
    }

    public ExchangeDto(int id, Currency baseCurrencyId, Currency targetCurrencyId, BigDecimal rate, BigDecimal amount, BigDecimal convertedAmount) {
        this.id = id;
        this.baseCurrencyId = baseCurrencyId;
        this.targetCurrencyId = targetCurrencyId;
        this.rate = rate;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Currency getBaseCurrencyId() {
        return baseCurrencyId;
    }

    public void setBaseCurrencyId(Currency baseCurrencyId) {
        this.baseCurrencyId = baseCurrencyId;
    }

    public Currency getTargetCurrencyId() {
        return targetCurrencyId;
    }

    public void setTargetCurrencyId(Currency targetCurrencyId) {
        this.targetCurrencyId = targetCurrencyId;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }
}
