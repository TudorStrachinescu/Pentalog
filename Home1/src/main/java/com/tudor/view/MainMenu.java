package com.tudor.view;

import com.tudor.service.AuthenticatedUserData;
import com.tudor.service.RetrieveInfoFromConsole;
import com.tudor.repository.UserAuthentication;
import com.tudor.exceptions.UserLogException;

/**
 * Class used to create and display the main menu of the application.
 */

public class MainMenu {
    private UserAuthentication accessingUser = new UserAuthentication();
    private AuthenticatedUserData userData = AuthenticatedUserData.getInstance();

    /**
     * Displays and allows the user to choose options.
     * <p>
     * These options include logging in using username and password, logging out and inspecting accounts owned.
     */

    public void runApp(){
        boolean run = true;

        printMenu();

        while(run){
            RetrieveInfoFromConsole scan = new RetrieveInfoFromConsole();

            System.out.print("Choose an option: ");

            int choice = scan.getIntFromConsole();

            switch(choice){
                case 1:
                    try {
                        accessingUser.logIn();
                    } catch (UserLogException e){
                        System.out.println(e.getMessage());
                    }

                    break;
                case 2:
                    if(accessingUser.logOut()){
                        System.out.println("Successfully logged out");
                    } else {
                        System.out.println("There is no accessingUser logged in");
                    }

                    break;
                case 3:
                    if(!userData.noUserLogged()) {
                        AccountMenu accountDetails = new AccountMenu(userData.getLoggedUser());
                        accountDetails.run();
                    } else {
                        System.out.println("Invalid option!");
                    }
                    break;
                case 4:
                    if(userData.noUserLogged()){
                        run = false;
                    } else {
                        System.out.println("You must log out before closing app");
                    }

                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }

            if(run){
                printMenu();
            } else {
                System.out.println("Closing Application ...");
            }
        }
    }

    /**
     * Prints the main menu to the console.
     */

    private void printMenu(){
        System.out.println("Main menu:");
        if(userData.noUserLogged()) {
            System.out.println("\t1. Log in");

        } else {
            System.out.println("\t2. Log out");
            System.out.println("\t3. Account menu");
        }
        if(userData.noUserLogged()) {
            System.out.println("\t4. Exit");
        }
    }
}
