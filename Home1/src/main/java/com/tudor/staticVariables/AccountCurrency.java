package com.tudor.staticVariables;

public enum AccountCurrency {
    Ron,
    Euro,
    Invalid;

    public static AccountCurrency getCurrency(String currency){
        switch(currency.toUpperCase()){
            case "RON":
                return Ron;
            case "EURO":
                return Euro;
            default:
                return Invalid;
        }
    }
}