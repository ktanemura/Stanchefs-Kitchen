package com.stanchefskitchen.data.providers;

import com.stanchefskitchen.data.models.Account;
import com.stanchefskitchen.data.models.AccountType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tyler Wong
 */
public class AccountProvider {
    private static Connection connection = DatabaseProvider.getConnection();

    private static final String ALL_ACCOUNTS = "SELECT * FROM account;";
    private static final String ACCOUNT_BY_ID = 
            "SELECT * FROM account WHERE id = %d;";
    private static final String LOGIN = 
            "SELECT * FROM account WHERE username = ? AND password = ?;";
    private static final String UPDATE_PASSWORD = 
            "UPDATE account SET password = ? WHERE id = ?";
    private static final String INSERT_NEW_ACCOUNT = 
            "INSERT INTO account(username, password, firstname, lastname, type) VALUES(?, ?, ?, ?, ?::accounttype);";
    private static final String ACCOUNT_BY_NAME = "SELECT * FROM account " +
            "WHERE firstName = ? AND lastName = ?";
    private static final String USERNAME_AVAILABILITY = "SELECT id FROM account"
            + " WHERE username = ?;";
    
    
    public static List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();

        try {
            ResultSet results = connection.createStatement().executeQuery(ALL_ACCOUNTS);

            while (results.next()) {
                accounts.add(new Account(results.getInt(Account.ID),
                        results.getString(Account.USERNAME),
                        results.getString(Account.PASSWORD),
                        results.getString(Account.FIRST_NAME),
                        results.getString(Account.LAST_NAME),
                        AccountType.valueOf(results.getString(Account.TYPE))));
            }
        }
        catch (SQLException e) {
            System.out.println("Could not get accounts");
        }

        return accounts;
    }
    
    public static Account login(String username, String password) {
        Account account = null;
        
        try {
            PreparedStatement statement = connection.prepareStatement(LOGIN);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            
            while (result.next()) {
                account = new Account(result.getInt(Account.ID),
                        result.getString(Account.USERNAME),
                        result.getString(Account.PASSWORD),
                        result.getString(Account.FIRST_NAME),
                        result.getString(Account.LAST_NAME),
                        AccountType.valueOf(result.getString(Account.TYPE)));
            }
            
        }
        catch (SQLException e) {
            System.out.println("Incorrect username or password.");
        }
        
        return account;
    }

    public static Account getAccountById(int accountId) {
        Account account = null;

        try {
            ResultSet results = connection.createStatement().executeQuery(
                    String.format(ACCOUNT_BY_ID, accountId));

            while (results.next()) {
                account = new Account(results.getInt(Account.ID),
                        results.getString(Account.USERNAME),
                        results.getString(Account.PASSWORD),
                        results.getString(Account.FIRST_NAME),
                        results.getString(Account.LAST_NAME),
                        AccountType.valueOf(results.getString(Account.TYPE)));
            }
        }
        catch (SQLException e) {
            System.out.println("Could not get account with id " 
                    + String.valueOf(accountId));
        }

        return account;
    }
    
    public static int addAccount(Account account) {
        int id = -1;
        
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(
                    INSERT_NEW_ACCOUNT, new String[] { Account.ID });
            statement.setString(1, account.getUsername());
            statement.setString(2, account.getPassword());
            statement.setString(3, account.getFirstName());
            statement.setString(4, account.getLastName());
            statement.setString(5, account.getType().name());
            statement.executeUpdate();
            
            ResultSet result = statement.getGeneratedKeys();
            
            while (result.next()) {
                id = result.getInt(Account.ID);
            }
            
            connection.commit();
            connection.setAutoCommit(true);
        }
        catch (SQLException e) {
            System.out.println("Failed to add account");
            id = -1;
            
            try {
                System.out.println("Transaction is being rolled back");
                connection.rollback();
                connection.setAutoCommit(true);
            } 
            catch(SQLException ex) {
                System.out.println("Could not roll back");
            }
        }
        
        return id;
    }

    public static boolean changePassword(Account account, String newPass) {
        boolean success;
        
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(UPDATE_PASSWORD);
            statement.setString(1, newPass);
            statement.setInt(2, account.getId());
            statement.executeUpdate();
            connection.commit();
            success = true;
            connection.setAutoCommit(true);
        }
        catch (SQLException e) {
            System.out.println("Error updating password");
            success = false;
            
            try {
                System.out.println("Transaction is being rolled back");
                connection.rollback();
                connection.setAutoCommit(true);
            } 
            catch(SQLException ex) {
                System.out.println("Could not roll back");
            }
        }
     
        return success;
    }
    
    public static int getAccountIDByName(String first, String last) {
        try {
            PreparedStatement statement = connection.prepareStatement(ACCOUNT_BY_NAME);
            statement.setString(1, first);
            statement.setString(2, last);
            ResultSet results = statement.executeQuery();
            if (results.getFetchSize() == 0) {
                return -1;
            }
            
            return results.getInt(1);
        }
        catch (SQLException e) {
            System.out.println("Error getting account");
            return -1;
        }
    }
    
    public static boolean usernameExists(String username) {
        boolean exists = true;
        try {
            PreparedStatement statement = connection
                    .prepareStatement(USERNAME_AVAILABILITY);
            statement.setString(1, username);
            ResultSet results = statement.executeQuery();
            if (results.getFetchSize() == 0) {
                exists = false;
            }
        }
        catch (SQLException e) {
            System.out.println("Error checking if username exists");
        }
        return exists;
    }
    
}
