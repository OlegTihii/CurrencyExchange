package com.insideprojects.currencyexchange.model;

public class Currency {
    private int id;
    private String code;
    private String currencyName;
    private String sign;

    public Currency() {
    }

    public Currency(int id, String code, String currencyName, String sign) {
        this.id = id;
        this.code = code;
        this.currencyName = currencyName;
        this.sign = sign;
    }

    public Currency(String code, String currencyName, String sign) {
        this.code = code;
        this.currencyName = currencyName;
        this.sign = sign;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
