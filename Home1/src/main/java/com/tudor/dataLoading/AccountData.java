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

/**
 * Class used to load account information from a local resource file.
 *
 * @see FilePaths
 */

public final class AccountData {

    private final Logger logger = LogManager.getLogger(AccountData.class.getName());
    private List<Account> accountList = new ArrayList<>();

    /**
     * Constructor without parameters that starts a data loading process.
     * <p>
     * Data loading might fail for IO reasons such as file not existing or data within the file being invalid.
     * Whichever the case, the event will be logged using a logger.
     *
     * @see Logger
     */

    public AccountData() {
        try {
            loadAccountData(FileSystems.getDefault().getPath(FilePaths.ACCOUNT_FILE_PATH));
        } catch (LoadFileException e){
            logger.debug(e.getMessage());
        }
    }

    /**
     * Attempts to load data and in case of failure it logs the event that causes it.
     *
     * @param path                  the path to which the file resource can be found
     * @throws LoadFileException    if file is not found or invalid
     *
     * @see Logger
     */

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
                    if(checkAccount(this.getAccountList(), newAccount.get())) {
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

    /**
     * Takes a string input and checks if it has data suitable for an account object.
     *
     * @param line                      given string of data
     * @return                          a Optional wrapping an Account object if compatible or empty otherwise
     * @throws LoadAccountException     if invalid account data is found
     *
     * @see Optional
     * @see Account
     */

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

                double amount;
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

    /**
     * Checks if an account already exists within a list of accounts.
     *
     * @param accounts  the list of accounts to search in
     * @param account   the account to verify
     * @return          <code>false</code> if the given account is already present within the given
     *                  list or <code>true</code> otherwise
     */

    public static boolean checkAccount(List<Account> accounts, Account account){
        for(Account a : accounts){
            if(a.equals(account)){
                return false;
            }
        }

        return true;
    }

    /**
     * Gets the list of stored accounts
     *
     * @return  accountList
     */

    public List<Account> getAccountList() {
        return accountList;
    }

    /**
     * Check if input string has a valid account format.
     *
     * @param accountNumber     string to be checked
     * @return                  <code>true</code> if the provided string has a valid format and
     *                          <code>false</code> otherwise
     */

    public boolean isValidAccountFormat(String accountNumber){
        return accountNumber.matches("^RO\\d{2}\\p{Upper}{4}\\d{16}$");
    }
}
