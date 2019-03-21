package com.tudor.logging;

import com.tudor.users.User;
import com.tudor.users.UserData;
import java.util.Scanner;

public class Logger {
    private static final Logger instance = new Logger();

    private static UserData data = UserData.getInstance();
    private User loggedUser = null;

    public static Logger getInstance(){
        return Logger.instance;
    }

    public void logIn() throws LogException{

        if (loggedUser == null) {
            Scanner s = new Scanner(System.in);

            System.out.println("Logging in");
            System.out.println("Name: ");
            String userName = s.nextLine();
            System.out.println("Password: ");
            String userPassword = s.nextLine();

            User user = new User(userName, userPassword);

            if (data.checkUser(user)) {
                System.out.println("Welcome " + userName);
                loggedUser = user;
            } else {
                throw new LogException("Wrong username/password");
            }
        } else {
            throw new LogException("Cannot log in.\nApp in use by " + loggedUser.getName());
        }
    }

    public boolean logOut(){
        if(loggedUser == null) {
            return false;
        }

        loggedUser = null;
        return true;
    }

    public boolean noUserLogged() {
        return(loggedUser == null);
    }
}