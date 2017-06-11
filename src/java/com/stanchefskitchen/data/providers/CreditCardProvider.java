package com.stanchefskitchen.data.providers;

import com.stanchefskitchen.data.models.CreditCard;
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
public class CreditCardProvider {

    private static Connection connection = DatabaseProvider.getConnection();

    private static final String CARDS_BY_ACCOUNT_ID = "SELECT * FROM creditcard "
            + "WHERE customerId = %d";
    private static final String INSERT_NEW_CARD = "INSERT INTO creditcard(number, "
            + "customerid, crc, address, expdate) VALUES(?, ?, ?, ?, ?);";
    private static final String REMOVE_CARD = "DELETE FROM creditcard WHERE number = ?;";

    public static List<CreditCard> getCardsByAccountId(int accountId) {
        List<CreditCard> cards = new ArrayList<>();

        try {
            ResultSet results = connection.createStatement().executeQuery(
                    String.format(CARDS_BY_ACCOUNT_ID, accountId));

            while (results.next()) {
                cards.add(new CreditCard(results.getString(CreditCard.NUMBER),
                    results.getInt(CreditCard.CUSTOMER_ID),
                    results.getInt(CreditCard.CRC),
                    results.getString(CreditCard.ADDRESS),
                    results.getDate(CreditCard.EXP_DATE)));
            }
        }
        catch (SQLException e) {
            System.out.println("Failed to remove credit card");
        }

        return cards;
    }

    public static boolean addCardToAccount(int accountId, CreditCard card) {
        boolean success = false;

        try {
            PreparedStatement statement = connection.prepareStatement(
                    INSERT_NEW_CARD);
            statement.setString(1, card.getNumber());
            statement.setInt(2, accountId);
            statement.setInt(3, card.getCrc());
            statement.setString(4, card.getAddress());
            statement.setDate(5, card.getExpDate());
            statement.executeUpdate();
            success = true;
        }
        catch (SQLException e) {
            System.out.println("Failed to add credit card");
        }

        return success;
    }

    public static boolean removeCardFromAccount(String cardNumber) {
        boolean success = false;

        try {
            PreparedStatement statement = connection.prepareStatement(REMOVE_CARD);
            statement.setString(1, cardNumber);
            statement.executeUpdate();
            success = true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to remove credit card");
        }

        return success;
    }
}
