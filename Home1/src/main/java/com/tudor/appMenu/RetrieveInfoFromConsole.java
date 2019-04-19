package com.tudor.appMenu;

import com.tudor.dataLoading.AccountData;
import com.tudor.staticVariables.AccountCurrency;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 *
 */

public final class RetrieveInfoFromConsole {
    private Scanner s = new Scanner(System.in);

    /**
     *
     * @param low
     * @param high
     * @return
     */

    public int getIntInRangeFromConsole(int low, int high){
        int s = getIntFromConsole();
        while(s < low || s > high){
            System.out.println("Please enter a number between " + low + " and " + high);
            s = getIntFromConsole();
        }

        return s;
    }

    /**
     *
     * @return
     */

    public BigDecimal getBalanceFromConsole(){
        return new BigDecimal(getIntFromConsole());
    }

    /**
     *
     * @return
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
     *
     * @return
     */

    public String getStringFromConsole(){
        return s.nextLine();
    }

    /**
     *
     * @return
     */

    public AccountCurrency getCurrencyFromConsole(){
        String in;

        while(true){
            in = s.nextLine().toUpperCase();
            AccountCurrency c = AccountCurrency.getCurrency(in);
            if(c != AccountCurrency.INVALID){
                return c;
            }
            System.out.println("Please input currency('RON' or 'EURO')");
        }
    }

    /**
     *
     * @return
     */

    public String getAccountNumberFromConsole(){
        AccountData data = new AccountData();
        String in;

        while(true){
            in = s.nextLine().toUpperCase();
            if(data.isValidAccountFormat(in)){
                return in;
            }
            System.out.println("Please input a valid accountNumber (RO12ABCD1234567890123456)");
        }
    }
}
