package com.tudor;

import com.tudor.service.AppConfig;
import com.tudor.view.MainMenu;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Main class containing the static main method that starts the application.
 */

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        MainMenu menu = new MainMenu();
        menu.runApp();
    }
}