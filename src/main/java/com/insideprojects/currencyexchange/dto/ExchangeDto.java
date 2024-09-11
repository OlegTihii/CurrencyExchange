package com.insideprojects.currencyexchange.dto;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class ExchangeDto {

    @SerializedName("id")
    private Integer id;

    @SerializedName("baseCurrency")
    private CurrencyDto baseCurrencyId;

    @SerializedName("targetCurrency")
    private CurrencyDto targetCurrencyId;

    @SerializedName("rate")
    private BigDecimal rate;

    @SerializedName("amount")
    private BigDecimal amount;

    @SerializedName("convertedAmount")
    private BigDecimal convertedAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CurrencyDto getBaseCurrencyId() {
        return baseCurrencyId;
    }

    public void setBaseCurrencyId(CurrencyDto baseCurrencyId) {
        this.baseCurrencyId = baseCurrencyId;
    }

    public CurrencyDto getTargetCurrencyId() {
        return targetCurrencyId;
    }

    public void setTargetCurrencyId(CurrencyDto targetCurrencyId) {
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
