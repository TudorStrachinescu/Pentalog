package com.tudor.service;

import com.tudor.model.*;
import com.tudor.staticVariables.AccountCurrency;
import com.tudor.staticVariables.FactorySession;
import com.tudor.staticVariables.Type;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.math.BigDecimal;

public class TestData {
    private static final Logger logger = LogManager.getLogger(TestData.class.getName());

    public static void loadTestData(){

        User u1 = new User("tudor", "password");
        User u2 = new User("alina", "buhuhuu");
        User u3 = new User("user", "1234");

        Person p1 = new Person(u1, "a happy place", "tudor", "strachinescu", "email1");
        Person p2 = new Person(u2, "a happy place", "alina", "strachinescu", "email2");
        Person p3 = new Person(u3, "nowhere", "mihai", "pop", "email3");

        Notification n11 = new Notification(u1, "tudor first notification");
        Notification n12 = new Notification(u1, "tudor second notification");
        Notification n13 = new Notification(u1, "tudor third notification");

        Notification n21 = new Notification(u2, "alina first notification");
        Notification n22 = new Notification(u2, "alina second notification");

        Account a11 = new Account(u1, "ro12abcd1234567890123456", BigDecimal.valueOf(1000), AccountCurrency.EURO);
        Account a12 = new Account(u1, "ro56asdf0987654321234567", BigDecimal.valueOf(524), AccountCurrency.RON);
        Account a13 = new Account(u1, "ro54wgsd1234567890123456", BigDecimal.valueOf(354), AccountCurrency.RON);
        Account a14 = new Account(u1, "ro23wers1234567890123456", BigDecimal.valueOf(6845), AccountCurrency.EURO);

        Account a21 = new Account(u2, "ro45zzds1234567890123456", BigDecimal.valueOf(364), AccountCurrency.EURO);
        Account a22 = new Account(u2, "ro67poiy1234567890123456", BigDecimal.valueOf(4684), AccountCurrency.EURO);

        Account a31 = new Account(u2, "ro00aaaa1234567890123456", BigDecimal.valueOf(354), AccountCurrency.EURO);
        Account a32 = new Account(u2, "ro11bbbb1234567890123456", BigDecimal.valueOf(4843), AccountCurrency.RON);
        Account a33 = new Account(u2, "ro22cccc1234567890123456", BigDecimal.valueOf(864), AccountCurrency.RON);
        Account a34 = new Account(u2, "ro33dddd1234567890123456", BigDecimal.valueOf(8355), AccountCurrency.EURO);

        Transaction t1 = new Transaction("ro78tujh1234567890123456", BigDecimal.valueOf(100), "account details 1", a11, Type.INCOMING);
        Transaction t2 = new Transaction("ro90abcd1237890123456345", BigDecimal.valueOf(321), "account details 1", a12, Type.OUTGOING);
        Transaction t3 = new Transaction("ro23dsae1234567890123456", BigDecimal.valueOf(15), "account details 1", a13, Type.OUTGOING);
        Transaction t4 = new Transaction("ro45zzds1234567890123456", BigDecimal.valueOf(321), "account details 1", a21, Type.INCOMING);

        try (Session session = FactorySession.getSession()) {
            session.beginTransaction();

            session.save(u1);
            session.save(u2);
            session.save(u3);

            session.save(p1);
            session.save(p2);
            session.save(p3);

            session.save(n11);
            session.save(n12);
            session.save(n13);
            session.save(n21);
            session.save(n22);

            session.save(a11);
            session.save(a12);
            session.save(a13);
            session.save(a14);
            session.save(a21);
            session.save(a22);
            session.save(a31);
            session.save(a32);
            session.save(a33);
            session.save(a34);

            session.save(t1);
            session.save(t2);
            session.save(t3);
            session.save(t4);

            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error("Connection error loading test data");
        }
    }
}
