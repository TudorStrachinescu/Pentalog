package com.tudor.appMenu;

import com.tudor.accountManagement.AccountOperations;
import com.tudor.authentication.AuthenticatedUserData;
import com.tudor.modelClasses.User;

/**
 * Class used to create and display an account menu.
 */

class AccountMenu {
    private AccountOperations op = new AccountOperations();
    private AuthenticatedUserData userData = AuthenticatedUserData.getInstance();
    private User accessingUser;

    /**
     * Creates an AccountMenu object for the the user received as parameter.
     *
     * @param accessingUser the User object for which the menu will be created for
     */

    AccountMenu(User accessingUser) {
        this.accessingUser = accessingUser;
    }

    /**
     * Displays a menu and allows the user to make various account operations.
     * <p>
     * These operations include creating new accounts, inspecting currently stored accounts,
     * and transferring money between accounts of the same currency.
     *
     * @see AccountOperations
     */

    void run(){
        boolean run = true;

        printMenu();

        while(run){
            RetrieveInfoFromConsole scan = new RetrieveInfoFromConsole();

            System.out.print("Choose an option: ");

            int choice = scan.getIntFromConsole();

            switch(choice){
                case 1:
                    if(op.createAccount(accessingUser)) {
                        System.out.println("Account created");
                    } else {
                        System.out.println("Account already exists for " + accessingUser.getName());
                    }
                    break;
                case 2:
                    if(userData.getUserAccounts().size() < 1){
                        System.out.println("There is no account information for " + accessingUser.getName());
                    } else {
                        op.printAccounts(userData.getUserAccounts());
                        System.out.println("User: " + accessingUser.getName());
                    }
                    break;
                case 3:
                    if(userData.getUserAccounts().size() < 1){
                        System.out.println("There is no account information for " + accessingUser.getName());
                    } else {
                        op.ownAccountsTransfer();
                    }
                    break;
                case 4:
                    run = false;
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
            if(run) {
                printMenu();
            }
        }
    }

    /**
     * Prints the account menu options to console.
     */

    private void printMenu(){
        System.out.println("Account menu:");
        System.out.println("\t1. Create account");
        System.out.println("\t2. Inspect account");
        System.out.println("\t3. Transfer between own accounts");
        System.out.println("\t4. Back");
    }
}
