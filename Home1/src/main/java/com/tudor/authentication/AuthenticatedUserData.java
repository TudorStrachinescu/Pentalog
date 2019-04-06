package com.tudor.authentication;

import com.tudor.dataLoading.AccountData;
import com.tudor.modelClasses.Account;
import com.tudor.modelClasses.User;
import com.tudor.staticVariables.FilePaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.List;

public class AuthenticatedUserData {
    private final Logger logger = LogManager.getLogger(AuthenticatedUserData.class.getName());
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
        addAccountToFile(account);
    }

    private void addAccountToFile(Account account){
        try (PrintWriter accountFile = new PrintWriter(new FileWriter(FilePaths.ACCOUNT_FILE_PATH, true))){
            accountFile.append(account.toFile());
        } catch (IOException e){
            logger.debug(e.getMessage());
        }
    }

    void clearData(){
        loggedUser = null;
        userAccounts.clear();
    }
}
