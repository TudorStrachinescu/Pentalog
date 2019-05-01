package com.tudor.service;

import com.tudor.model.Account;
import com.tudor.model.User;

import java.util.List;

/**
 * A class containing authenticated user data.
 */

public class AuthenticatedUserData {
    private static AuthenticatedUserData ourInstance = new AuthenticatedUserData();

    private User loggedUser;
    private List<Account> userAccounts;

    /**
     * Returns the common instance of the AuthenticatedUserData class.
     *
     * @return common user data
     */

    public static AuthenticatedUserData getInstance() {
        return ourInstance;
    }

    /**
     * Overwritten no parameters constructor so that no other instance of this class may exist.
     */

    private AuthenticatedUserData() {
    }

    /**
     * Method to set the user currently logged into the application.
     *
     * @param loggedUser    user that has successfully logged in
     */

    void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    /**
     * Method to set the accounts only for the authenticated user.
     *
     * @param userAccounts  all accounts associated with the authenticated user
     */

    public void setUserAccounts(List<Account> userAccounts) {
        this.userAccounts = userAccounts;
    }

    /**
     * Gets the user currently authenticated
     *
     * @return loggedUser
     */

    public User getLoggedUser() {
        return loggedUser;
    }

    /**
     * Gets the accounts for the currently authenticated user.
     *
     * @return  userAccounts
     */

    public List<Account> getUserAccounts() {
        return userAccounts;
    }

    /**
     * Resets all instance data.
     */

    void clearData(){
        loggedUser = null;
        userAccounts.clear();
    }

    /**
     * Checks if there is any user authenticated.
     *
     * @return  <code>true</code> if no user is authenticated and <code>false</code> otherwise
     */

    public boolean noUserLogged() {
        return(loggedUser == null);
    }
}
