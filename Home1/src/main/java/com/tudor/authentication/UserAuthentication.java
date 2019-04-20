package com.tudor.authentication;

import com.tudor.appMenu.RetrieveInfoFromConsole;
import com.tudor.dataLoading.AccountData;
import com.tudor.exceptions.UserLogException;
import com.tudor.modelClasses.Account;
import com.tudor.modelClasses.User;
import com.tudor.dataLoading.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used for user authentication operations.
 */

public class UserAuthentication {

    private AuthenticatedUserData userData = AuthenticatedUserData.getInstance();

    /**
     * Prompts the user for name and password and saves the user as authenticated
     * within AuthenticatedUserData.
     *
     * @throws UserLogException     if user authentication fails
     *
     * @see AuthenticatedUserData
     */

    public void logIn() throws UserLogException {
        RetrieveInfoFromConsole scan = new RetrieveInfoFromConsole();

        if (userData.getLoggedUser() == null) {
            UserData data = new UserData();
            System.out.println("Logging in");
            System.out.println("Name: ");
            String userName = scan.getStringFromConsole();
            System.out.println("Password: ");
            String userPassword = scan.getStringFromConsole();

            User user = new User(userName, userPassword);

            if (data.checkUser(user)) {
                System.out.println("Welcome " + userName);
                userData.setLoggedUser(user);
                userData.setUserAccounts(getUserAccounts());
            } else {
                throw new UserLogException("Wrong username/password");
            }
        } else {
            throw new UserLogException("Cannot log in.\nApp in use by " + userData.getLoggedUser().getName());
        }
    }

    /**
     * Clears authentication data if any.
     *
     * @return  <code>true</code> if successful and <code>false</code> if no user is authenticated
     */

    public boolean logOut(){
        if(userData.getLoggedUser() == null) {
            return false;
        }

        userData.clearData();
        return true;
    }

    /**
     * Filters all existing accounts only by the authenticated user name.
     *
     * @return  the list of accounts for the authenticated user
     */

    private List<Account> getUserAccounts(){
        AccountData data = new AccountData();
        List<Account> accounts = new ArrayList<>();

        for(Account a : data.getAccountList()){
            if(a.getUserName().equals(userData.getLoggedUser().getName())){
                accounts.add(a);
            }
        }

        return accounts;
    }

    /**
     * Checks if there is any user authenticated.
     *
     * @return  <code>true</code> if no user is authenticated and <code>false</code> otherwise
     */

    public boolean noUserLogged() {
        return(userData.getLoggedUser() == null);
    }

    /**
     * Gets the user data of the authenticated user
     *
     * @return  the authenticated user
     */

    public User getLoggedUser() {
        return userData.getLoggedUser();
    }
}