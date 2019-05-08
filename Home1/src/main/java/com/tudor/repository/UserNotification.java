package com.tudor.repository;

import com.tudor.model.Notification;
import com.tudor.staticVariables.FactorySession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class UserNotification {
    private final Logger logger = LogManager.getLogger(UserAccounts.class.getName());

    public void addNotification(Notification notification) {
        try (Session session = FactorySession.getSession()) {
            session.beginTransaction();

            session.save(notification);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error("Connection error updating account data");
        }
    }
}
