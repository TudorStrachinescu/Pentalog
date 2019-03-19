package com.tudor;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    private static UserData data = UserData.getInstance();
    private static Scanner s = new Scanner(System.in);
    private static final String FILE_PATH = "src\\main\\resources\\data.txt";

    private static String loggedUser = null;

    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath(FILE_PATH);

        if (!data.loadUsers(path)) {
            System.out.println("File does not exist or does not contain valid data");
            return;
        }

        System.out.println("Data loaded");

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
                    logIn();
                    break;
                case 3:
                    logOut();
                    break;
                case 4:
                    if(exit()){
                        run = false;
                    }
                    break;
                default:
                    break;
            }
        }

    }

    private static void printMenu(){
        System.out.println("Main menu:");
        System.out.println("\t1. Print this menu");
        System.out.println("\t2. Log in");
        System.out.println("\t3. Log out");
        System.out.println("\t4. Exit");
    }

    private static void logIn(){
        if(loggedUser == null) {
            System.out.println("Logging in");
            System.out.println("Name: ");
            String userName = s.nextLine();
            System.out.println("Password: ");
            String userPassword = s.nextLine();

            if (data.exists(userName, userPassword)) {
                System.out.println("Welcome " + userName);
                loggedUser = userName;
            } else {
                System.out.println("Wrong username/password");
            }
        } else {
            System.out.println("Cannot log in while app is in use by " + loggedUser);
        }
    }

    private static void logOut(){
        if(loggedUser == null){
            System.out.println("There are no users logged in");
        } else {
            loggedUser = null;
            System.out.println("Successfully logged out");
        }
    }

    private static boolean exit(){
        if(loggedUser != null){
            System.out.println("Cannot exit, app in use by " + loggedUser);
            return false;
        }

        return true;
    }
}