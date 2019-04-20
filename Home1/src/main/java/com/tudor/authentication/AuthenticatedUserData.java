package com.tudor.authentication;

import com.tudor.modelClasses.Account;
import com.tudor.modelClasses.User;
import com.tudor.staticVariables.FilePaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.List;

/**
 * A class containing authenticated user data.
 */

public class AuthenticatedUserData {
    private final Logger logger = LogManager.getLogger(AuthenticatedUserData.class.getName());
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

    void setUserAccounts(List<Account> userAccounts) {
        this.userAccounts = userAccounts;
    }

    /**
     * Gets the user currently authenticated
     *
     * @return loggedUser
     */

    User getLoggedUser() {
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
     * Adds a new account received as parameter to the account list.
     * <p>
     * Also writer the new account to the account resource file.
     *
     * @param account   the account to be added to the existing user accounts
     */

    public void add(Account account){
        userAccounts.add(account);
        addAccountToFile(account);
    }

    /**
     * Adds an account to the resource file containing all accounts.
     *
     * @param account   the account to be added to the file
     */

    private void addAccountToFile(Account account){
        try (PrintWriter accountFile = new PrintWriter(new FileWriter(FilePaths.ACCOUNT_FILE_PATH, true))){
            accountFile.append(account.toFile());
        } catch (IOException e){
            logger.debug(e.getMessage());
        }
    }

    /**
     * Resets all instance data.
     */

    void clearData(){
        loggedUser = null;
        userAccounts.clear();
    }
}
