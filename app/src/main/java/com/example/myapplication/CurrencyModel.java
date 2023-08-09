package com.example.myapplication;

public class CurrencyModel {
    private String currency;
    private int currencyImg;

    public CurrencyModel(String currency, int currencyImg) {
        this.currency = currency;
        this.currencyImg = currencyImg;
    }

    public String getCurrency() {
        return currency;
    }

    public int getCurrencyImg() {
        return currencyImg;
    }
}
