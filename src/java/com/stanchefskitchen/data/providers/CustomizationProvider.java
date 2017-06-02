/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stanchefskitchen.data.providers;

import com.stanchefskitchen.data.models.Customization;
import com.stanchefskitchen.data.models.MenuItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kyle
 */
public class CustomizationProvider {
    private static Connection connection = DatabaseProvider.getConnection();
    
    private static final String SELECT_CUSTOM = "SELECT * FROM customization;";
    private static final String SELECT_CUSTOM_BY_ID = "SELECT * FROM customization where id = ?;";
    private static final String ADD_CUSTOM = "INSERT INTO customization(description, price) VALUES(?, ?);";
    private static final String UPDATE_CUSTOM = "UPDATE customization SET description = ? price = ? where id = ?;";
    private static final String DELETE_CUSTOM = "DELETE FROM customization WHERE id = ?;";
    private static final String GET_MENUITEM_CUSTOM = "SELECT i.name, i.price, i.description FROM menuitemcustomization m JOIN "
            + "MenuItem i ON m.itemName = i.name WHERE m.customizationId = ?;";
    
    public static List<Customization> getCustomizations() {
        ArrayList<Customization> customs = new ArrayList<Customization>();
        
        try {
            ResultSet results = connection.createStatement().executeQuery(SELECT_CUSTOM);
            while (results.next()) {
                customs.add(new Customization(
                        results.getInt(Customization.ID),
                        results.getString(Customization.DESCRIPTION),
                        results.getDouble(Customization.PRICE)
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomizationProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return customs;
    }
    
    public static Customization getCustomById(int id) {
        Customization custom = null;
        
        PreparedStatement s;
        try {
            s = connection.prepareStatement(SELECT_CUSTOM_BY_ID);
            s.setInt(1, id);
            ResultSet result = s.executeQuery();
            
            if (result.next()){
                custom = new Customization(
                        result.getInt(Customization.ID),
                        result.getString(Customization.DESCRIPTION),
                        result.getDouble(Customization.PRICE)
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomizationProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return custom;
    }
    
    public static void addCustom(String desc, double price) {
        try {
            PreparedStatement s = connection.prepareStatement(ADD_CUSTOM);
            s.setString(1, desc);
            s.setDouble(2, price);
            s.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomizationProvider.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public static void updateCustom(int id, String newDesc, double newPrice) {
        try {
            PreparedStatement s = connection.prepareStatement(UPDATE_CUSTOM);
            s.setString(1, newDesc);
            s.setDouble(2, newPrice);
            s.setInt(3, id);
            s.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomizationProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void deleteCustom(int id) {
        try {
            PreparedStatement s = connection.prepareStatement(DELETE_CUSTOM);
            s.setInt(1, id);
            s.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomizationProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static List<MenuItem> getMenuItemsByCustomization(Customization c) {
       HashSet<MenuItem> list = new HashSet<MenuItem>();
        
        try {
            PreparedStatement s = connection.prepareStatement(GET_MENUITEM_CUSTOM);
            s.setInt(1, c.id);
            ResultSet r = s.executeQuery();
            
            while (r.next()) {
                MenuItem i = new MenuItem(r.getString(MenuItem.NAME), 
                        r.getDouble(MenuItem.PRICE), 
                        r.getString(MenuItem.DESC),
                        r.getBoolean(MenuItem.AVAIL));
                list.add(i);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomizationProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return new ArrayList<MenuItem>(list);
    }
}
