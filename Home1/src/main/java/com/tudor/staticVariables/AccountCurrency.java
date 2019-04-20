package com.tudor.staticVariables;

/**
 * Enum object for currency representation.
 */

public enum AccountCurrency {
    RON,
    EURO,
    INVALID;

    /**
     * Returns corresponding currency format based on given string.
     *
     * @param currency  given string representation of the currency
     * @return          AccountCurrency corresponding with the given string
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