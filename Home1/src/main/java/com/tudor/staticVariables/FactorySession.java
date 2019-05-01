package com.tudor.staticVariables;

import com.tudor.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactorySession {
    private static SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(Person.class)
            .addAnnotatedClass(Notification.class)
            .addAnnotatedClass(Account.class)
            .addAnnotatedClass(Transaction.class)
            .buildSessionFactory();

    public static Session getSession(){
        return factory.getCurrentSession();
    }

    public static void closeFactory(){
        factory.close();
    }
}
