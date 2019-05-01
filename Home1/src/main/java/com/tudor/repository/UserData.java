package com.tudor.repository;

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
 * Class used to load user data from a local resource file.
 */

final class UserData{

    private final Logger logger = LogManager.getLogger(UserData.class.getName());

    /**
     * Constructor without parameters that attempts to load the user data from file.
     */

    UserData(){}

    /**
     * Checks if a user already exists within the currently stored list of users.
     *
     * @param user  the user to search for
     * @return      <code>true</code> if the user already exists in users list and
     *              <code>false</code> otherwise
     */

    boolean checkUser(User user){
        List<User> users = new ArrayList<>();

        try (Session session = FactorySession.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from User where username = :name ");
            query.setParameter("name", user.getName());

            users = query.getResultList();
        } catch (HibernateException e) {
            logger.error("Connection error checking credentials");
        }


        if(users.size() > 0){
            for(User u : users){
                if(u.equals(user)){
                    return true;
                }
            }
        }

        return false;
    }
}