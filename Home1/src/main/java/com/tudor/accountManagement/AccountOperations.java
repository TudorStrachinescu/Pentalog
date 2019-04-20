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
import java.util.Optional;

/**
 * This class contains the methods for operations that can be made with an Account.
 */

public class AccountOperations {

    private AuthenticatedUserData accountData = AuthenticatedUserData.getInstance();
    private RetrieveInfoFromConsole scan = new RetrieveInfoFromConsole();

    /**
     * Formats and prints to console the account data for the accounts within a list provided as parameter.
     *
     * @param accounts  list of Account objects
     */

    public void printAccounts(List<Account> accounts){
        int i = 1;
        System.out.println("Available accounts:");
        for(Account a : accounts){
            System.out.print("Account " + i + ": ");
            System.out.println(a.toString());
            i++;
        }
    }

    /**
     * Prompts the user for valid Account creation information.
     * The Account will be completed with the authenticated User data.
     * <p>
     * Information provided must be valid in order to proceed.
     *
     * @param user  the User object for which the account will be created
     * @return      <code>true</code> if a new Account has been created and <code>false</code> otherwise
     *
     * @see Account
     * @see User
     * @see AuthenticatedUserData
     */

    public boolean createAccount(User user){
        System.out.println("Please provide account number:");
        String accountNumber = scan.getAccountNumberFromConsole();
        String accountName = user.getName();
        System.out.println("Please input balance amount:");
        BigDecimal amount = scan.getBalanceFromConsole();
        System.out.println("Please input your currency('RON' or 'EURO')");
        AccountCurrency accountCurr = scan.getCurrencyFromConsole();
        Account acc = new Account(accountNumber, accountName, amount, accountCurr);

        if(AccountData.checkAccount(accountData.getUserAccounts(), acc)){
            accountData.add(acc);
            return true;
        }
        return false;
    }

    /**
     * Transfers an amount of money between Accounts owned by the authenticated User.
     * <p>
     * Method checks first to see if there are any two accounts usable for transfer,
     * and if possible the user will be prompted to specify the accounts by their
     * displayed index, and to choose an amount.
     */

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

            Optional<Account> accountFrom = Optional.empty();
            for(Account a : accountData.getUserAccounts()){
                if(a.equals(usableAccounts.get(indexFrom - 1))){
                    accountFrom = Optional.of(a);
                }
            }

            usableAccounts.remove(indexFrom - 1);

            printAccounts(usableAccounts);

            System.out.println("Please choose account to transfer to");
            int indexTo = scan.getIntInRangeFromConsole(1, usableAccounts.size());

            Optional<Account> accountTo = Optional.empty();
            for(Account a : accountData.getUserAccounts()){
                if(a.equals(usableAccounts.get(indexTo - 1))){
                    accountTo = Optional.of(a);
                }
            }

            System.out.println("Please choose the amount to transfer between accounts");
            int amount = scan.getIntFromConsole();

            if(accountFrom.isPresent() && accountTo.isPresent()) {
                if (accountFrom.get().withdrawal(BigDecimal.valueOf(amount))) {
                    accountTo.get().deposit(BigDecimal.valueOf(amount));
                    System.out.println("Transfer complete!");
                } else {
                    System.out.println("Insufficient funds");
                }
            }
        } else {
            System.out.println("Transaction not possible, you need at least 2 accounts of same currency.");
        }
    }

    /**
     * Filters authenticated user accounts based on their currency property
     *
     * @param c the currency to filter by
     * @return  the list of Accounts with the given currency
     *
     * @see Account
     * @see AccountCurrency
     */

    private List<Account> getUsableAccounts(AccountCurrency c){
        List<Account> usableAccounts = new ArrayList<>();

        for(Account a : accountData.getUserAccounts()) {
            if (a.getAccountType() == c){
                usableAccounts.add(a);
            }
        }

        return usableAccounts;
    }

    /**
     * Checks if a transfer is possible for the currently authenticated user.
     * <p>
     * A transfer is possible if there are at least 2 accounts with the same currency.
     *
     * @return  <code>true</code> if transfer is possible and <code>false</code> otherwise
     */

    private boolean transferPossible(){
        return accountsOfCurrecy(AccountCurrency.EURO) >= 2 || accountsOfCurrecy(AccountCurrency.RON) >= 2;
    }

    /**
     * Gets the number of accounts of the same currency given as parameter
     *
     * @param c currency to filter by
     * @return  the number of accounts of the given currency
     */

    private int accountsOfCurrecy(AccountCurrency c){
        int index = 0;
        for(Account a: accountData.getUserAccounts()){
            if(a.getAccountType() == c){
                index++;
            }
        }

        return index;
    }

    /**
     * Displays the available currency usable for transfers between own accounts.
     */

    private void currencyMenu(){
        if(accountsOfCurrecy(AccountCurrency.RON) >= 2) {
            System.out.println("RON");
        }
        if(accountsOfCurrecy(AccountCurrency.EURO) >= 2){
            System.out.println("EURO");
        }
    }
}
