package com.tudor.staticVariables;

/**
 *
 */

public enum AccountCurrency {
    RON,
    EURO,
    INVALID;

    /**
     *
     * @param currency
     * @return
     */

    public static AccountCurrency getCurrency(String currency){
        switch(currency.toUpperCase()){
            case "RON":
                return RON;
            case "EURO":
                return EURO;
            default:
                return INVALID;
        }
    }
}