package com.tudor.authentication;

import com.tudor.exceptions.LogException;
import com.tudor.users.User;
import com.tudor.users.UserData;
import java.util.Scanner;

public class UserAuthentication {

    private static UserData data = UserData.getInstance();
    private User loggedUser = null;

    public void logIn() throws LogException {

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

    public User getLoggedUser() {
        return loggedUser;
    }
}