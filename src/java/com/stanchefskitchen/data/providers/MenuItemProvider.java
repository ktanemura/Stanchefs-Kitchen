package com.stanchefskitchen.data.providers;

import com.stanchefskitchen.data.models.Customization;
import com.stanchefskitchen.data.models.MenuItem;
import com.stanchefskitchen.data.models.MenuItemType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brittany Berlanga
 */
public class MenuItemProvider {
    
    private static Connection connection = DatabaseProvider.getConnection();
    
    public static final String GET_CUSTOMIZATIONS = "SELECT c.id, c.description"
            + ", c.price FROM menuitemcustomization m, customization c WHERE it"
            + "emname = ?;";
    public static final String ADD_CUSTOM = "INSERT INTO menuitemcustomization "
            + "(itemname, customizationid) values (?, ?);";
    public static final String REMOVE_CUSTOM = "DELETE FROM menuitemcustomizati"
            + "on WHERE itemname = ? AND customizationid = ?;";
    public static final String ADD_TYPE = "INSERT INTO menuitemtype "
            + "values (?, ?);";
    public static final String REMOVE_TYPE = "DELETE FROM menuitemtype WHERE me"
            + "nuitem = ? AND itemtypeid = ?;";
    
    public static List<Customization> getCustomizations(MenuItem menuItem) {
        List<Customization> customizations = new ArrayList();
        try {
            PreparedStatement statement = connection
                    .prepareStatement(GET_CUSTOMIZATIONS);
            statement.setString(1, menuItem.name);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                customizations.add(
                        new Customization(results.getInt(Customization.ID), 
                        results.getString(Customization.DESCRIPTION), 
                        results.getDouble(Customization.PRICE)));
            }
        }
        catch (SQLException e) {
            System.out.println("Could not get customizations");
        }     
        return customizations;
    }
    
    public static boolean addCustomization(MenuItem menuItem, 
            Customization customization) {
        boolean success = true;
        try {
            PreparedStatement statement = connection
                    .prepareStatement(ADD_CUSTOM);
            statement.setString(1, menuItem.name);
            statement.setInt(2, customization.id);
            statement.executeQuery();
        }
        catch (SQLException e) {
            System.out.println("Could not add customization to menu item");
            success = false;
        } 
        return success;
    }
    
    public static boolean removeCustomization(MenuItem menuItem, 
            Customization customization) {
        boolean success = true;
        try {
            PreparedStatement statement = connection
                    .prepareStatement(REMOVE_CUSTOM);
            statement.setString(1, menuItem.name);
            statement.setInt(2, customization.id);
            statement.executeQuery();
        }
        catch (SQLException e) {
            System.out.println("Could not remove customization from menu item");
            success = false;
        } 
        return success;
    }
    
    public static boolean addType(MenuItem menuItem, 
            MenuItemType menuItemType) {
        boolean success = true;
        try {
            PreparedStatement statement = connection
                    .prepareStatement(ADD_TYPE);
            statement.setString(1, menuItem.name);
            statement.setInt(2, menuItemType.getItemTypeId());
            statement.executeQuery();
        }
        catch (SQLException e) {
            System.out.println("Could not add type to menu item");
            success = false;
        } 
        return success;
    }
    
    public static boolean removeType(MenuItem menuItem, 
            MenuItemType menuItemType) {
        boolean success = true;
        try {
            PreparedStatement statement = connection
                    .prepareStatement(REMOVE_TYPE);
            statement.setString(1, menuItem.name);
            statement.setInt(2, menuItemType.getItemTypeId());
            statement.executeQuery();
        }
        catch (SQLException e) {
            System.out.println("Could not remove type from menu item");
            success = false;
        } 
        return success;
    }
}