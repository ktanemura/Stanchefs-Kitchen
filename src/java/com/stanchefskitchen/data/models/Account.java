package com.stanchefskitchen.data.models;

/**
 *
 * @author Tyler Wong
 */
public class Account {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private AccountType type;
    
    public Account() {}
    
    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String FIRST_NAME = "firstname";
    public static final String LAST_NAME = "lastname";
    public static final String TYPE = "type";
    
    public Account(String username, String password, String firstName, String lastName, AccountType type) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
    }
    
    public Account(int id, String username, String password, String firstName, String lastName, AccountType type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
    }
    
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public AccountType getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return id + " " + username + " " + type.name();
    }
}

