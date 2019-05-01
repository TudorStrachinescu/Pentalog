package com.tudor;

import com.tudor.service.TestData;
import com.tudor.view.MainMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.tudor.staticVariables.FactorySession.closeFactory;

/**
 * Main class containing the static main method that starts the application.
 */

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        TestData.loadTestData();

        try{
            MainMenu menu = new MainMenu();
            menu.runApp();

        } catch (Exception e) {
            logger.error("Application error");
        } finally {
            closeFactory();
        }
    }
}