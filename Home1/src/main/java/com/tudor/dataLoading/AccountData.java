package com.tudor.dataLoading;

import com.tudor.exceptions.LoadFileException;
import com.tudor.modelClasses.Account;
import com.tudor.staticVariables.AccountCurrency;
import com.tudor.staticVariables.FilePaths;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.tudor.staticVariables.AccountCurrency.getCurrency;

public final class AccountData {

    private final static AccountData accountsData = new AccountData();
    private List<Account> accountList = new ArrayList<>();

    public static AccountData getInstance() {
        return accountsData;
    }

    private AccountData() {
        try {
            loadAccountData(FileSystems.getDefault().getPath(FilePaths.ACCOUNT_FILE_PATH));
        } catch (LoadFileException e){
            System.out.println(e.getMessage());
        }
    }

    private void loadAccountData (Path path) throws LoadFileException {
        String fileName = path.getFileName().toString();

        if(!Files.exists(path)){
            throw new LoadFileException("File " + fileName + " not found");
        }

        try(BufferedReader br = Files.newBufferedReader(path)){
            String line;
            while((line = br.readLine()) != null){
                Optional<Account> newAccount = getAccountIfValid(line);
                if(newAccount.isPresent()){
                    if(!checkAccount(newAccount.get())) {
                        this.accountList.add(newAccount.get());
                    }
                }
            }
        } catch (IOException e){
            throw new LoadFileException("Error retrieving data: " + e.getMessage());
        }

        if(accountList.size() == 0) {
            throw new LoadFileException(fileName + " does not contain valid data");
        }
    }

    private Optional<Account> getAccountIfValid(String line){
        Optional<Account> testAccount = Optional.empty();
        line = line.replaceAll("\\s+", " ");

        String[] data = line.split(" ");

        if(data.length == 4){
            String accountNumber = data[0].toUpperCase();
            String accountName = data[1];
            AccountCurrency accCurr = getCurrency(data[3]);

            if(accCurr != AccountCurrency.Invalid && isValidAccountFormat(accountNumber)) {
                boolean isValidBalance = true;
                double balance = 0;
                try {
                    balance = Double.parseDouble(data[2]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid balance");
                    isValidBalance = false;
                }
                if(isValidBalance){
                    testAccount = Optional.of(new Account(accountNumber, accountName, balance, accCurr));
                }
            }
        }

        return testAccount;
    }

    public boolean checkAccount(Account account){
        if(this.accountList.size() > 0){
            for(Account a : this.accountList){
                if(a.equals(account)){
                    return true;
                }
            }
        }

        return false;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public boolean isValidAccountFormat(String accountNumber){
        return accountNumber.matches("^RO\\d{2}\\p{Upper}{4}\\d{16}$");
    }
}
