package com.tudor;

import com.tudor.appMenu.MainMenu;
import com.tudor.modelClasses.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Main class containing the static main method that starts the application.
 */

public class Main {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Notification.class)
                .addAnnotatedClass(Account.class)
                .addAnnotatedClass(Transaction.class)
                .buildSessionFactory();

        Session session = factory.openSession();

        factory.close();

        MainMenu menu = new MainMenu();
        menu.runApp();
    }


}