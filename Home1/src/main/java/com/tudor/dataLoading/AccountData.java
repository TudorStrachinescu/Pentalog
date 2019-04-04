package com.tudor.dataLoading;

import com.tudor.exceptions.LoadAccountException;
import com.tudor.exceptions.LoadFileException;
import com.tudor.modelClasses.Account;
import com.tudor.staticVariables.AccountCurrency;
import com.tudor.staticVariables.FilePaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.tudor.staticVariables.AccountCurrency.getCurrency;

public final class AccountData {

    private final Logger logger = LogManager.getLogger(AccountData.class.getName());
    private List<Account> accountList = new ArrayList<>();

    public AccountData() {
        try {
            loadAccountData(FileSystems.getDefault().getPath(FilePaths.ACCOUNT_FILE_PATH));
        } catch (LoadFileException e){
            logger.debug(e.getMessage());
        }
    }

    private void loadAccountData (Path path) throws LoadFileException {
        String fileName = path.getFileName().toString();

        if(!Files.exists(path)){
            throw new LoadFileException("File " + fileName + " not found");
        }

        try(BufferedReader br = Files.newBufferedReader(path)){
            String line;
            Optional<Account> newAccount = Optional.empty();

            int index = 1;

            while((line = br.readLine()) != null){
                try {
                    newAccount = getAccountIfValid(line);
                } catch (LoadAccountException e){
                    logger.debug("Line " + index + ": " + e.getMessage());
                }
                if(newAccount.isPresent()){
                    if(!checkAccount(this.getAccountList(), newAccount.get())) {
                        this.accountList.add(newAccount.get());
                    }
                }
                index++;
            }
        } catch (IOException e){
            throw new LoadFileException("Error retrieving data: " + e.getMessage());
        }

        if(accountList.size() == 0) {
            throw new LoadFileException(fileName + " does not contain valid data");
        }
    }

    private Optional<Account> getAccountIfValid(String line) throws LoadAccountException {
        Optional<Account> testAccount = Optional.empty();
        line = line.replaceAll("\\s+", " ");

        String[] data = line.split(" ");

        if(data.length == 4){
            String accountNumber = data[0].toUpperCase();
            if(!isValidAccountFormat(accountNumber)){
                throw new LoadAccountException("INVALID account number");
            }
            String accountName = data[1];
            AccountCurrency accCurr = getCurrency(data[3]);

            if(accCurr != AccountCurrency.INVALID && isValidAccountFormat(accountNumber)) {

                double amount = 0d;
                try {
                    amount = Double.parseDouble(data[2]);
                } catch (NumberFormatException e) {
                    throw new LoadAccountException("INVALID balance");
                }
                BigDecimal balance = new BigDecimal(amount);
                testAccount = Optional.of(new Account(accountNumber, accountName, balance, accCurr));
            } else {
                throw new LoadAccountException("INVALID currency");
            }
        }

        return testAccount;
    }

    public static boolean checkAccount(List<Account> accounts, Account account){
        for(Account a : accounts){
            if(a.equals(account)){
                return true;
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
