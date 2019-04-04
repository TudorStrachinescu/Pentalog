package com.tudor.accountManagement;

import com.tudor.appMenu.RetrieveInfoFromConsole;
import com.tudor.authentication.AuthenticatedUserData;
import com.tudor.dataLoading.AccountData;
import com.tudor.modelClasses.Account;
import com.tudor.modelClasses.User;
import com.tudor.staticVariables.AccountCurrency;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountOperations {

    private AuthenticatedUserData accountData = AuthenticatedUserData.getInstance();
    private RetrieveInfoFromConsole scan = new RetrieveInfoFromConsole();

    public void printAccounts(List<Account> accounts){
        int i = 1;
        System.out.println("Available accounts:");
        for(Account a : accounts){
            System.out.print("Account " + i + ": ");
            System.out.println(a.toString());
            i++;
        }
    }

    public boolean createAccount(User user){
        System.out.println("Please provide account number:");
        String accountNumber = scan.getAccountNumberFromConsole();
        String accountName = user.getName();
        System.out.println("Please input balance amount:");
        BigDecimal amount = scan.getBalanceFromConsole();
        System.out.println("Please input your currency('RON' or 'EURO')");
        AccountCurrency accountCurr = scan.getCurrencyFromConsole();
        Account acc = new Account(accountNumber, accountName, amount, accountCurr);

        if(!AccountData.checkAccount(accountData.getUserAccounts(),acc)){
            accountData.add(acc);
            return true;
        }
        return false;
    }

    public void ownAccountsTransfer(){
        if(transferPossible()){

            System.out.println("Please choose currency for the desired transfer");
            currencyMenu();

            AccountCurrency c = scan.getCurrencyFromConsole();

            while(accountsOfCurrecy(c) < 2){
                System.out.println("Please choose a valid currency");
                currencyMenu();
                c = scan.getCurrencyFromConsole();
            }

            List<Account> usableAccounts = getUsableAccounts(c);

            printAccounts(usableAccounts);

            System.out.println("Please choose account to transfer from");
            int indexFrom = scan.getIntInRangeFromConsole(1, usableAccounts.size());

            Account accountFrom = new Account();
            for(Account a : accountData.getUserAccounts()){
                if(a.equals(usableAccounts.get(indexFrom - 1))){
                    accountFrom = a;
                }
            }

            usableAccounts.remove(indexFrom - 1);

            printAccounts(usableAccounts);

            System.out.println("Please choose account to transfer to");
            int indexTo = scan.getIntInRangeFromConsole(1, usableAccounts.size());

            Account accountTo = new Account();
            for(Account a : accountData.getUserAccounts()){
                if(a.equals(usableAccounts.get(indexTo - 1))){
                    accountTo = a;
                }
            }

            System.out.println("Please choose the amount to transfer between accounts");
            int amount = scan.getIntFromConsole();

            if(accountFrom.withdrawal(BigDecimal.valueOf(amount))){
                accountTo.deposit(BigDecimal.valueOf(amount));
                System.out.println("Transfer complete!");
            } else {
                System.out.println("Insufficient funds");
            }

        } else {
            System.out.println("Transaction not possible, you need at least 2 accounts of same currency.");
        }
    }

    private List<Account> getUsableAccounts(AccountCurrency c){
        List<Account> usableAccounts = new ArrayList<>();

        for(Account a : accountData.getUserAccounts()) {
            if (a.getAccountType() == c){
                usableAccounts.add(a);
            }
        }

        return usableAccounts;
    }

    private boolean transferPossible(){
        return accountsOfCurrecy(AccountCurrency.EURO) >= 2 || accountsOfCurrecy(AccountCurrency.RON) >= 2;
    }

    private int accountsOfCurrecy(AccountCurrency c){
        int index = 0;
        for(Account a: accountData.getUserAccounts()){
            if(a.getAccountType() == c){
                index++;
            }
        }

        return index;
    }

    private void currencyMenu(){
        if(accountsOfCurrecy(AccountCurrency.RON) >= 2) {
            System.out.println("RON");
        }
        if(accountsOfCurrecy(AccountCurrency.EURO) >= 2){
            System.out.println("EURO");
        }
    }
}
