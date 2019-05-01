package com.tudor.repository;

import com.tudor.service.AuthenticatedUserData;
import com.tudor.service.RetrieveInfoFromConsole;
import com.tudor.exceptions.UserLogException;
import com.tudor.model.Account;
import com.tudor.model.User;
import com.tudor.staticVariables.FactorySession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used for user authentication operations.
 */

public class UserAuthentication {
    private final Logger logger = LogManager.getLogger(UserAuthentication.class.getName());

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
        List<Account> list = new ArrayList<>();

        try (Session session = FactorySession.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("select a from Account a where a.accountUser.name = :name");
            query.setParameter("name", userData.getLoggedUser().getName());

            list = query.getResultList();
        } catch (HibernateException e) {
            logger.error("Error retrieving account data from server");
        }

        return list;
    }
}