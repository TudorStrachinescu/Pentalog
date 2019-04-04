package com.tudor.authentication;

import com.tudor.modelClasses.Account;
import com.tudor.modelClasses.User;

import java.util.List;

public class AuthenticatedUserData {
    private static AuthenticatedUserData ourInstance = new AuthenticatedUserData();

    private User loggedUser;
    private List<Account> userAccounts;

    public static AuthenticatedUserData getInstance() {
        return ourInstance;
    }

    private AuthenticatedUserData() {
    }

    void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    void setUserAccounts(List<Account> userAccounts) {
        this.userAccounts = userAccounts;
    }

    User getLoggedUser() {
        return loggedUser;
    }

    public List<Account> getUserAccounts() {
        return userAccounts;
    }

    public void add(Account account){
        userAccounts.add(account);
    }

    void clearData(){
        loggedUser = null;
        userAccounts.clear();
    }
}
