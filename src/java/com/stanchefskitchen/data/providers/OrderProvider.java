/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stanchefskitchen.data.providers;

import com.stanchefskitchen.data.models.Account;
import com.stanchefskitchen.data.models.Order;
import com.stanchefskitchen.data.models.OrderItem;
import com.stanchefskitchen.data.models.OrderItemCustomization;
import com.stanchefskitchen.data.models.OrderStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author Ryan
 */
public class OrderProvider {
    private static Connection connection = DatabaseProvider.getConnection();
    
    private final String ORDERITEM_BY_ID = "SELECT * FROM orderitem WHERE orderId = ?";
    private final String CUSTOM_BY_ID = "SELECT * FROM orderItemCustomization WHERE orderId = ?";
    private final String INSERT_ORDER = "INSERT INTO order (customerId, billId) VALUES (?,?)";
    private final String INSERT_ITEM = "INSERT INTO orderItem (orderId, itemName, quantity) " +
            "VALUES (?,?,?)";
    private final String INSERT_CUSTOM = "INSERT INTO orderItemCustomization VALUES (?,?)";
    
    public ArrayList<OrderItem> getOrderItems(int orderId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ORDERITEM_BY_ID);
            statement.setInt(1, orderId);
            ResultSet results = statement.executeQuery();
            
            ArrayList<OrderItem> orderItems = new ArrayList<>();
            while (results.next()) {
                orderItems.add(new OrderItem(results.getInt(1), results.getInt(2), 
                        results.getString(3), results.getInt(4)));
            }
            
            return orderItems;
        }
        catch (SQLException e) {
            System.out.println("Error getting order items: "+e.toString());
            return null;
        }
    }
    
    public ArrayList<OrderItemCustomization> getOrderCustomizations(int orderId) {
        try {
            PreparedStatement statement = connection.prepareStatement(ORDERITEM_BY_ID);
            statement.setInt(1, orderId);
            ResultSet results = statement.executeQuery();
            
            ArrayList<OrderItemCustomization> orderCustomizations = new ArrayList<>();
            while (results.next()) {
                orderCustomizations.add(new OrderItemCustomization(results.getInt(1), results.getInt(2)));
            }
            
            return orderCustomizations;
        }
        catch (SQLException e) {
            System.out.println("Error getting order customizations: "+e.toString());
            return null;
        }
    }
    
    /**
     * Inputs an order into the database
     * @param customerId
     * @param billId
     * @return Order object with the id created
     */
    public Order placeOrder(int customerId, int billId) {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_ORDER);
            statement.setInt(1, customerId);
            statement.setInt(2, billId);
            statement.executeUpdate();
            
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            int newId = generatedKeys.getInt(1);
            
            return new Order(newId, customerId, billId, OrderStatus.PREPARING);
        }
        catch (SQLException e) {
            System.out.println("Error placing order: "+e.toString());
            
            return null;
        }
    }
    
    public OrderItem addOrderItem(int orderId, String itemName, int quantity) {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_ITEM);
            statement.setInt(1, orderId);
            statement.setString(2, itemName);
            statement.setInt(3, quantity);
            statement.executeUpdate();
            
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            int newId = generatedKeys.getInt(1);
            
            return new OrderItem(newId, orderId, itemName, quantity);
        }
        catch (SQLException e) {
            System.out.println("Error adding order item: "+e.toString());
            
            return null;
        }
    }
    
    public OrderItemCustomization addOrderCustomization(int orderItemId, int menuItemCustomizationId) {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_ORDER);
            statement.setInt(1, orderItemId);
            statement.setInt(2, menuItemCustomizationId);
            statement.executeUpdate();
            
            return new OrderItemCustomization(orderItemId, menuItemCustomizationId);
        }
        catch (SQLException e) {
            System.out.println("Error adding order item: "+e.toString());
            
            return null;
        }
    }
}
