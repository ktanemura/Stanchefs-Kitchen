package com.stanchefskitchen.data.providers;

import com.stanchefskitchen.data.models.CreditCard;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Tyler Wong
 */
public class CreditCardProvider {
    private static Connection connection = DatabaseProvider.getConnection();
    
    private static final String INSERT_NEW_CARD = "INSERT INTO creditcard(number, "
            + "customerid, crc, address, expdate) VALUES(?, ?, ?, ?, ?);";
    private static final String REMOVE_CARD = "DELETE FROM creditcard WHERE number = %d;"; 
    
    public static int addCardToAccount(int accountId, CreditCard card) {
        int number = -1;
        
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(
                    INSERT_NEW_CARD, new String[] { CreditCard.NUMBER });
            statement.setString(1, card.getNumber());
            statement.setInt(2, card.getCustomerId());
            statement.setInt(3, card.getCrc());
            statement.setString(4, card.getAddress());
            statement.setDate(5, card.getExpDate());
            statement.executeUpdate();
            
            ResultSet result = statement.getGeneratedKeys();
            
            while (result.next()) {
                number = result.getInt(CreditCard.NUMBER);
            }
            
            connection.commit();
            connection.setAutoCommit(true);
        }
        catch (SQLException e) {
            System.out.println("Failed to add credit card");
            number = -1;
            
            try {
                System.out.println("Transaction is being rolled back");
                connection.rollback();
                connection.setAutoCommit(true);
            } 
            catch(SQLException ex) {
                System.out.println("Could not roll back");
            }
        }
        
        return number;
    }
    
    public static boolean removeCardFromAccount(int cardNumber) {
        boolean success = false;
        
        try {
            connection.setAutoCommit(false);
            connection.createStatement().executeQuery(
                    String.format(REMOVE_CARD, cardNumber));
            success = true;
        }
        catch (SQLException e) {
            System.out.println("Failed to remove credit card");
            
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
}
