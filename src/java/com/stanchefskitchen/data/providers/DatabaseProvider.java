package com.stanchefskitchen.data.providers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Tyler Wong
 */
public class DatabaseProvider {

    private static Connection connection;

    private static final String DB_URL = "jdbc:postgresql://ec2-54-225-236-102.compute-1.amazonaws.com:5432/de6dmhj7b6ro7d?sslmode=require";
    private static final String USERNAME = "wgeivsjrpwgqen";
    private static final String PASSWORD = "5d19067cbb35d307fe1a34c6a76ed10057b168eb9980b7162094a1076de9dfbb";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            } 
            catch (SQLException e) {
                System.out.println("Problem connecting to database");
            }
        }
        return connection;
    }
}
