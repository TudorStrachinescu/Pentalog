package com.tudor;

import com.tudor.logging.LogException;
import com.tudor.logging.Logger;
import com.tudor.users.LoadFileException;
import com.tudor.users.UserData;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    private static UserData data = UserData.getInstance();
    private static Logger log = Logger.getInstance();
    private static Scanner s = new Scanner(System.in);
    private static final String FILE_PATH = "src" + File.separator + "main" +
            File.separator + "resources" + File.separator +"data.txt";

    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath(FILE_PATH);

        try {
            data.loadUsers(path);
        } catch (LoadFileException e){
            System.out.println(e.getMessage());
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
                    try {
                        log.logIn();
                    } catch (LogException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    if(log.logOut()){
                        System.out.println("Successfully logged out");
                    } else {
                        System.out.println("There is no user logged in");
                    }
                    break;
                case 4:
                    if(log.noUserLogged()){
                        run = false;
                    } else {
                        System.out.println("Cannot log out, app is in use");
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
}