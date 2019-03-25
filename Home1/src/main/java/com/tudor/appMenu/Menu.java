package com.tudor.appMenu;

import com.tudor.authentication.UserAuthentication;
import com.tudor.exceptions.LogException;

import java.util.Scanner;

public class Menu {
    private UserAuthentication accessingUser = new UserAuthentication();

    public void runApp(){
        Scanner s = new Scanner(System.in);

        boolean run = true;

        printMenu();

        while(run){

            int choice;
            while(true){
                System.out.print("Choose an option(1 for options menu): ");
                if(s.hasNextInt()){
                    choice = s.nextInt();
                    s.nextLine();
                    break;
                } else {
                    System.out.println("Please provide a valid input");
                }
                s.nextLine();
            }

            switch(choice){
                case 1:
                    printMenu();
                    break;
                case 2:
                    try {
                        accessingUser.logIn();
                    } catch (LogException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    if(accessingUser.logOut()){
                        System.out.println("Successfully logged out");
                    } else {
                        System.out.println("There is no accessingUser logged in");
                    }
                    break;
                case 4:
                    if(accessingUser.noUserLogged()){
                        run = false;
                    } else {
                        System.out.println("Cannot accessingUser out, app is in use");
                    }
                    break;
                default:
                    break;
            }
        }
    }
    private void printMenu(){
        System.out.println("Main menu:");
        System.out.println("\t1. Print this menu");
        if(accessingUser.noUserLogged()) {
            System.out.println("\t2. Log in");
        } else {
            System.out.println("\t3. Log out");
        }
        if(accessingUser.noUserLogged()) {
            System.out.println("\t4. Exit");
        }
    }
}
