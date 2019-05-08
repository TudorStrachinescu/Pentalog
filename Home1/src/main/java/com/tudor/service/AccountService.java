package com.tudor.service;

import com.tudor.exceptions.AccountException;
import com.tudor.model.Account;
import com.tudor.model.Transaction;
import com.tudor.model.User;
import com.tudor.repository.UserAccounts;
import com.tudor.staticVariables.AccountCurrency;
import com.tudor.staticVariables.Type;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class contains the methods for operations that can be made with an Account.
 */

public class AccountService {

    private NotificationService notifications = new NotificationService();
    private UserAccounts accountCheck = new UserAccounts();
    private AuthenticatedUserData accountData = AuthenticatedUserData.getInstance();
    private RetrieveInfoFromConsole scan = new RetrieveInfoFromConsole();

    /**
     * Formats and prints to console the account data for the accounts within a list provided as parameter.
     *
     * @param accounts list of Account objects
     */

    public void printAccounts(List<Account> accounts) {
        int i = 1;
        System.out.println("Available accounts:");
        for (Account a : accounts) {
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
     * @return <code>true</code> if a new Account has been created and <code>false</code> otherwise
     * @see Account
     * @see User
     * @see AuthenticatedUserData
     */

    public Optional<Account> createAccount() {
        System.out.println("Please provide account number:");
        String accountNumber = scan.getAccountNumberFromConsole();
        System.out.println("Please input balance amount:");
        BigDecimal amount = scan.getBigDecimalFromConsole();
        System.out.println("Please input your currency('RON' or 'EURO')");
        AccountCurrency accountCurr = scan.getCurrencyFromConsole(true);
        if (!accountCheck.checkAccount(accountNumber).isPresent()) {
            return Optional.of(new Account(accountData.getLoggedUser(), accountNumber, amount, accountCurr));
        }

        return Optional.empty();
    }

    public void addAccount(Account account) throws AccountException {
        if (accountCheck.addAccount(account)) {
            accountData.setUserAccounts(accountCheck.getUserAccounts());
        } else {
            throw new AccountException("Account already exists");
        }
    }

    /**
     * Transfers an amount of money between Accounts owned by the authenticated User.
     * <p>
     * Method checks first to see if there are any two accounts usable for transfer,
     * and if possible the user will be prompted to specify the accounts by their
     * displayed index, and to choose an amount.
     */

    public void ownAccountsTransfer() {
        if (transferPossible()) {

            System.out.println("Please choose currency for the desired transfer");
            currencyMenu();

            AccountCurrency c = scan.getCurrencyFromConsole(false);

            while (accountsOfCurrecy(c) < 2) {
                System.out.println("Please choose a valid currency");
                currencyMenu();
                c = scan.getCurrencyFromConsole(false);
            }

            List<Account> usableAccounts = getUsableAccounts(c);

            Account accountFrom = chooseAccountFrom(usableAccounts);

            usableAccounts.remove(accountFrom);

            printAccounts(usableAccounts);

            System.out.println("Please choose account to transfer to");
            int indexTo = scan.getIntInRangeFromConsole(1, usableAccounts.size());

            Account accountTo = new Account();
            for (Account a : accountData.getUserAccounts()) {
                if (a.equals(usableAccounts.get(indexTo - 1))) {
                    accountTo = a;
                }
            }

            System.out.println("Please choose the amount to transfer between accounts");
            BigDecimal amount = scan.getBigDecimalFromConsole();

            if (accountFrom.withdrawal(amount)) {
                accountTo.deposit(amount);

                executeTransfer(accountFrom, accountTo, amount);
            } else {
                System.out.println("Insufficient funds");
            }
        } else {
            System.out.println("Transaction not possible, you need at least 2 accounts of same currency.");
        }
    }

    public void transferPayment() {
        Account accountFrom = chooseAccountFrom(accountData.getUserAccounts());
        System.out.println("Please input account to transfer to:");
        String accountNumber;

        do {
            accountNumber = scan.getAccountNumberFromConsole();
            if (accountFrom.getAccountNumber().equalsIgnoreCase(accountNumber)) {
                System.out.println("You cannot transfer to same account");
            }
        } while (accountFrom.getAccountNumber().equalsIgnoreCase(accountNumber));

        Optional<Account> accountTo = accountCheck.checkAccount(accountNumber);
        if(accountTo.isPresent()){
            if(accountTo.get().getAccountType().equals(accountFrom.getAccountType())) {
                System.out.println("Please select amount to transfer");
                BigDecimal amount = scan.getBigDecimalFromConsole();

                if (accountFrom.withdrawal(amount)) {
                    accountTo.get().deposit(amount);

                    executeTransfer(accountFrom, accountTo.get(), amount);
                } else {
                    System.out.println("Insufficient funds");
                }
            } else{
                System.out.println("Cannot transfer between accounts with different currency");
            }
        } else {
            System.out.println("Account provided does not exist");
        }
    }

    private void executeTransfer(Account accountFrom, Account accountTo, BigDecimal amount){
        Transaction from = new Transaction(accountTo.getAccountNumber(), amount, "payment to " + accountTo.getUserName(),
                accountFrom, Type.OUTGOING);
        Transaction to = new Transaction(accountFrom.getAccountNumber(), amount, "payment from " +
                accountFrom.getUserName(), accountTo, Type.INCOMING);
        String details = "transferred " + amount + " " + accountFrom.getAccountType() + " to " +
                accountTo.getUserName() + "(" + accountTo.getAccountNumber()+ ')';
        notifications.addNotification(accountData.getLoggedUser(), details);

        accountCheck.updateAccount(accountFrom, from);
        accountCheck.updateAccount(accountTo, to);
        System.out.println("Transfer complete!");
    }

    private Account chooseAccountFrom(List<Account> usableAccounts) {
        Account account = new Account();
        printAccounts(usableAccounts);

        System.out.println("Please choose account to transfer from");
        int indexFrom = scan.getIntInRangeFromConsole(1, usableAccounts.size());

        for (Account a : accountData.getUserAccounts()) {
            if (a.equals(usableAccounts.get(indexFrom - 1))) {
                account = a;
            }
        }

        return account;
    }

    /**
     * Filters authenticated user accounts based on their currency property
     *
     * @param c the currency to filter by
     * @return the list of Accounts with the given currency
     * @see Account
     * @see AccountCurrency
     */

    private List<Account> getUsableAccounts(AccountCurrency c) {
        List<Account> usableAccounts = new ArrayList<>();

        for (Account a : accountData.getUserAccounts()) {
            if (a.getAccountType() == c) {
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
     * @return <code>true</code> if transfer is possible and <code>false</code> otherwise
     */

    private boolean transferPossible() {
        return accountsOfCurrecy(AccountCurrency.EURO) >= 2 || accountsOfCurrecy(AccountCurrency.RON) >= 2;
    }

    /**
     * Gets the number of accounts of the same currency given as parameter
     *
     * @param c currency to filter by
     * @return the number of accounts of the given currency
     */

    private int accountsOfCurrecy(AccountCurrency c) {
        int index = 0;
        for (Account a : accountData.getUserAccounts()) {
            if (a.getAccountType() == c) {
                index++;
            }
        }

        return index;
    }

    /**
     * Displays the available currency usable for transfers between own accounts.
     */

    private void currencyMenu() {
        if (accountsOfCurrecy(AccountCurrency.RON) >= 2) {
            System.out.println("RON");
        }
        if (accountsOfCurrecy(AccountCurrency.EURO) >= 2) {
            System.out.println("EURO");
        }
    }
}
