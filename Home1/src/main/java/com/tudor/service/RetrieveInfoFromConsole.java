package com.tudor.service;

import com.tudor.staticVariables.AccountCurrency;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Class used to put together different types of expected formats that are required
 * by the application as console inputs.
 * <p>
 * The requested types also include validation.
 */

public final class RetrieveInfoFromConsole {
    private Scanner s = new Scanner(System.in);

    /**
     * Captures from console a numeric input within the given range.
     * <p>
     * If the input is out of range the user will be prompted for a valid input
     * until a valid input is provided.
     *
     * @param low   low end of the required input
     * @param high  high end of the required input
     * @return      the value provided from console between <code>low</code> and
     *              <code>high</code>
     */

    int getIntInRangeFromConsole(int low, int high){
        int s = getIntFromConsole();
        while(s < low || s > high){
            System.out.println("Please enter a number between " + low + " and " + high);
            s = getIntFromConsole();
        }

        return s;
    }

    /**
     * Captures a integer numeric value from the console and returns it as a BigDecimal.
     *
     * @return  a BigDecimal object containing the numeric input provided from console
     *
     * @see BigDecimal
     */

    BigDecimal getBalanceFromConsole(){
        return new BigDecimal(getIntFromConsole());
    }

    /**
     * Captures a numeric value from the console.
     * <p>
     * If the value is not numeric the user will be prompted for a valid input until
     * a valid value is provided.
     *
     * @return  a numeric value provided from console
     */

    public int getIntFromConsole(){
        Scanner s = new Scanner(System.in);

        int out;

        while(true){
            if(s.hasNextInt()){
                out = s.nextInt();
                s.nextLine();
                break;
            } else {
                System.out.println("Please provide a numeric value");
            }
            s.nextLine();
        }

        return out;
    }

    /**
     * A wrapper method for the nextLine method of the Scanner class.
     *
     * @return  a string containing the line provided from console
     *
     * @see Scanner
     */

    String getStringFromConsole(){
        return s.nextLine();
    }

    /**
     * Captures a valid currency type from console.
     * <p>
     * If the currency type is invalid the user will be prompted for
     * a valid input until one is provided.
     *
     * @return an AccountCurrency value
     *
     * @see AccountCurrency
     */

    AccountCurrency getCurrencyFromConsole(boolean tip){
        String in;

        while(true){
            in = s.nextLine().toUpperCase();
            AccountCurrency c = AccountCurrency.getCurrency(in);
            if(c != AccountCurrency.INVALID){
                return c;
            }
            if(tip) {
                System.out.println("Please input currency('RON' or 'EURO')");
            }
        }
    }

    /**
     * Captures a valid account number from console.
     * <p>
     * If the account number format is invalid the user will be prompted
     * for one until a valid one is provided.
     * Format should be: RO12ABCD1234567890123456
     *
     * @return a string with a valid account format
     */

    String getAccountNumberFromConsole(){
        String in;

        while(true){
            in = s.nextLine().toUpperCase();
            if(isValidAccountFormat(in)){
                return in;
            }
            System.out.println("Please input a valid accountNumber (RO12ABCD1234567890123456)");
        }
    }


    private boolean isValidAccountFormat(String accountNumber){
        return accountNumber.matches("^RO\\d{2}\\p{Upper}{4}\\d{16}$");
    }
}
