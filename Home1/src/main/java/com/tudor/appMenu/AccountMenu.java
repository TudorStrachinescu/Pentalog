package com.tudor.appMenu;

import com.tudor.accountManagement.AccountOperations;
import com.tudor.modelClasses.User;

class AccountMenu {
    private AccountOperations op = new AccountOperations();
    private User accessingUser;

    AccountMenu(User accessingUser) {
        this.accessingUser = accessingUser;
    }

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
                    if(!op.printAccount(accessingUser)){
                        System.out.println("There is no account information for " + accessingUser.getName());
                    }
                    break;
                case 3:
                    run = false;
                    break;
                default:
                    break;
            }

            printMenu();
        }
    }

    private void printMenu(){
        System.out.println("Account Menu:");
        System.out.println("\t1. Create account");
        System.out.println("\t2. Inspect account");
        System.out.println("\t3. Back");
    }
}
