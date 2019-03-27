package com.tudor.authentication;

import com.tudor.appMenu.RetrieveInfoFromConsole;
import com.tudor.exceptions.UserLogException;
import com.tudor.modelClasses.User;
import com.tudor.dataLoading.UserData;

public class UserAuthentication {

    private UserData data = UserData.getInstance();
    private User loggedUser = null;

    public void logIn() throws UserLogException {
        RetrieveInfoFromConsole scan = new RetrieveInfoFromConsole();

        if (loggedUser == null) {
            System.out.println("Logging in");
            System.out.println("Name: ");
            String userName = scan.getStringFromConsole();
            System.out.println("Password: ");
            String userPassword = scan.getStringFromConsole();

            User user = new User(userName, userPassword);

            if (data.checkUser(user)) {
                System.out.println("Welcome " + userName);
                loggedUser = user;
            } else {
                throw new UserLogException("Wrong username/password");
            }
        } else {
            throw new UserLogException("Cannot log in.\nApp in use by " + loggedUser.getName());
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