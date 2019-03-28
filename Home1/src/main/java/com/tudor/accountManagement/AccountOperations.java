package com.tudor.accountManagement;

import com.tudor.appMenu.RetrieveInfoFromConsole;
import com.tudor.dataLoading.AccountData;
import com.tudor.modelClasses.Account;
import com.tudor.modelClasses.User;
import com.tudor.staticVariables.AccountCurrency;

import java.math.BigDecimal;

public class AccountOperations {

    private AccountData accountData = AccountData.getInstance();
    private RetrieveInfoFromConsole scan = new RetrieveInfoFromConsole();

    public boolean printAccount (User user){
        boolean out = false;
        for(Account a : accountData.getAccountList()){
            if(a.getUserName().equals(user.getName())) {
                System.out.println(a.toString());
                out = true;
            }
        }

        return out;
    }

    public boolean createAccount(User user){
        System.out.println("Please provide account number:");
        String accountNumber = scan.getAccountNumberFromConsole();
        String accountName = user.getName();
        System.out.println("Please input balance amount:");
        BigDecimal amount = scan.getBalanceFromConsole();
        System.out.println("Please input your currency('Ron' or 'Euro')");
        AccountCurrency accountCurr = scan.getCurrencyFromConsole();
        Account acc = new Account(accountNumber, accountName, amount, accountCurr);

        if(!accountData.checkAccount(acc)){
            accountData.getAccountList().add(acc);
            return true;
        }
        return false;
    }
}
