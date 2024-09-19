package com.insideprojects.currencyexchange.dto;

import com.google.gson.annotations.SerializedName;

public class ExceptionDto {

    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
