package com.tudor.service;

import com.tudor.repository.AccountRepository;
import com.tudor.repository.UserRepository;
import com.tudor.exceptions.UserException;
import com.tudor.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class used for user authentication operations.
 */

@Service
public class UserService {

    private AuthenticatedUserData userData = AuthenticatedUserData.getInstance();
    @Autowired
    private AccountRepository accountData;
    @Autowired
    private UserRepository userRepository;

    /**
     * Prompts the user for name and password and saves the user as authenticated
     * within AuthenticatedUserData.
     *
     * @throws UserException     if user authentication fails
     *
     * @see AuthenticatedUserData
     */

    public void logIn() throws UserException {
        RetrieveInfoFromConsole scan = new RetrieveInfoFromConsole();

        if (userData.getLoggedUser() == null) {
            System.out.println("Logging in");
            System.out.println("Name: ");
            String userName = scan.getStringFromConsole();
            System.out.println("Password: ");
            String userPassword = scan.getStringFromConsole();
            Optional<User> user = Optional.empty();
            try {
                user = userRepository.findByNameAndPassword(userName, userPassword);
            } catch (RuntimeException e){
                e.printStackTrace();
            }

            if (user.isPresent()) {
                System.out.println("Welcome " + userName);
                userData.setLoggedUser(user.get());
//                userData.setUserAccounts(accountData.getUserAccounts());
            } else {
                throw new UserException("Wrong username/password");
            }
        } else {
            throw new UserException("Cannot log in.\nApp in use by " + userData.getLoggedUser().getName());
        }
    }

    /**
     * Clears authentication data if any.
     *
     * @return  <code>true</code> if successful and <code>false</code> if no user is authenticated
     */

    public boolean logOut(){
        if(userData.getLoggedUser() == null) {
            return false;
        }

        userData.clearData();
        return true;
    }
}