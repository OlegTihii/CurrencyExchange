package com.insideprojects.currencyexchange.dto;

import com.google.gson.annotations.SerializedName;

public class CurrencyDto {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String currencyName;

    @SerializedName("code")
    private String code;

    @SerializedName("sign")
    private String sign;

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
