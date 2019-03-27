package com.tudor.appMenu;

import com.tudor.dataLoading.AccountData;
import com.tudor.staticVariables.AccountCurrency;

import java.util.Scanner;

public final class RetrieveInfoFromConsole {
    private Scanner s = new Scanner(System.in);

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

    public String getStringFromConsole(){
        return s.nextLine();
    }

    public AccountCurrency getCurrencyFromConsole(){
        String in;

        while(true){
            in = s.nextLine().toUpperCase();
            AccountCurrency c = AccountCurrency.getCurrency(in);
            if(c != AccountCurrency.Invalid){
                return c;
            }
            System.out.println("Please input currency('Ron' or 'Euro')");
        }
    }

    public String getAccountNumberFromConsole(){
        AccountData data = AccountData.getInstance();
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
