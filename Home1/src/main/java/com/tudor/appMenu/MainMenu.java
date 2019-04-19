package com.tudor.appMenu;

import com.tudor.authentication.UserAuthentication;
import com.tudor.exceptions.UserLogException;

/**
 *
 */

public class MainMenu {
    private UserAuthentication accessingUser = new UserAuthentication();

    /**
     *
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
                    if(!accessingUser.noUserLogged()) {
                        AccountMenu accountDetails = new AccountMenu(accessingUser.getLoggedUser());
                        accountDetails.run();
                    } else {
                        System.out.println("Invalid option!");
                    }
                    break;
                case 4:
                    if(accessingUser.noUserLogged()){
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
    private void printMenu(){
        System.out.println("Main menu:");
        if(accessingUser.noUserLogged()) {
            System.out.println("\t1. Log in");

        } else {
            System.out.println("\t2. Log out");
            System.out.println("\t3. Account menu");
        }
        if(accessingUser.noUserLogged()) {
            System.out.println("\t4. Exit");
        }
    }
}
