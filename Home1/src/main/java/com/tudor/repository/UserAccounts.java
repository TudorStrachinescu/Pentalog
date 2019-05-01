package com.tudor.repository;

import com.tudor.model.Account;
import com.tudor.service.AuthenticatedUserData;
import com.tudor.staticVariables.FactorySession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserAccounts {
    private final Logger logger = LogManager.getLogger(UserAccounts.class.getName());

    private AuthenticatedUserData userData = AuthenticatedUserData.getInstance();

    public boolean checkAccount(String accountNumber){
        List<Account> list = new ArrayList<>();

        try(Session session = FactorySession.getSession()){
            session.beginTransaction();

            Query query = session.createQuery("from Account where account_number = :account ");
            query.setParameter("account", accountNumber);

            list = query.getResultList();
        } catch (HibernateException e){
            logger.error("Connection error retrieving account number");
        }

        return list.size() > 0;

    }

    /**
     * Adds a new account received as parameter to the account list.
     * <p>
     * Also writer the new account to the account resource file.
     *
     * @param account   the account to be added to the existing user accounts
     */

    public void add(Account account){
        try(Session session = FactorySession.getSession()){
            session.beginTransaction();

            session.save(account);
            session.getTransaction().commit();
        } catch (Exception e){
            logger.error("Connection error updating database");
        }

        userData.setUserAccounts(getUserAccounts());
    }


    /**
     * Filters all existing accounts only by the authenticated user name.
     *
     * @return  the list of accounts for the authenticated user
     */

    public List<Account> getUserAccounts(){
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

    public boolean updateAccountAmount(String accountNumber, BigDecimal amount){
        int result = 0;
        try(Session session = FactorySession.getSession()){
            session.beginTransaction();

            Query query = session.createQuery("update Account set balance = :balance where account_number = :account ");
            query.setParameter("balance", accountNumber);
            query.setParameter("account", amount);

            result = query.executeUpdate();
        } catch (HibernateException e){
            logger.error("Connection error updating account data");
        }

        return result == 1;
    }
}
