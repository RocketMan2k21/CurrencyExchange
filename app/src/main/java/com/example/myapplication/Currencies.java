package com.example.myapplication;

public enum Currencies {
    UAH("UAH"),
    USD("USD"),
    EUR("EUR"),
    PLN("PLN"),
    GBP("GBP");

    private final String currencyCode;

    Currencies(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }


}
