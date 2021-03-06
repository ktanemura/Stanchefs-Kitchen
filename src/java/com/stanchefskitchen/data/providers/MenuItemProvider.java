package com.stanchefskitchen.data.providers;

import com.stanchefskitchen.data.models.Customization;
import com.stanchefskitchen.data.models.ItemType;
import com.stanchefskitchen.data.models.MenuItem;
import com.stanchefskitchen.data.models.MenuItemType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brittany Berlanga
 */
public class MenuItemProvider {
    
    private static Connection connection = DatabaseProvider.getConnection();
    
    public static final String MENU_ITEM = "select * from menuitem where menuitem.name = ?;";
    
    public static final String MENU_ITEMS_BY_TYPE = "SELECT mi.name, mi.price, "
            + "mi.description, mi.available FROM menuitem mi "
            + "JOIN menuitemtype mit ON mi.name = mit.menuitem WHERE mit.itemtypeid = %d;";
    public static final String GET_CUSTOMIZATIONS_WITH_MENU_CUSTOMIZATION_ID = "SELECT m.id, c.description"
            + ", c.price FROM menuitemcustomization m, customization c WHERE it"
            + "emname = ? AND c.id = m.customizationid;";
     public static final String GET_CUSTOMIZATIONS = "SELECT c.id, c.description"
            + ", c.price FROM menuitemcustomization m, customization c WHERE it"
            + "emname = ? AND c.id = m.customizationid;";
    public static final String GET_TYPES = "SELECT id, name FROM menuitemty"
            + "pe, itemtype WHERE menuitem = ?;";
    public static final String ADD_CUSTOM = "INSERT INTO menuitemcustomization "
            + "(itemname, customizationid) values (?, ?);";
    public static final String REMOVE_CUSTOM = "DELETE FROM menuitemcustomizati"
            + "on WHERE itemname = ? AND customizationid = ?;";
    public static final String ADD_TYPE = "INSERT INTO menuitemtype "
            + "values (?, ?);";
    public static final String REMOVE_TYPE = "DELETE FROM menuitemtype WHERE me"
            + "nuitem = ? AND itemtypeid = ?;";
    public static final String GET_ALL_ITEMS = "SELECT * FROM menuitem;";
    public static final String GET_ALL_AVAIL_ITEMS = "SELECT * FROM menuitem "
            + "WHERE available = true;";
    
    public static final String ADD_MENU_ITEM = "INSERT INTO menuitem " +
            "VALUES (?,?,?,?)";
    
    public static boolean addMenuItem(MenuItem menuItem) {
        boolean success = true;
        try {
            PreparedStatement statement = connection
                    .prepareStatement(ADD_MENU_ITEM);
            statement.setString(1, menuItem.name);
            statement.setDouble(2, menuItem.price);
            statement.setString(3, menuItem.description);
            statement.setBoolean(4, true);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Could not add menu item: "+e.toString());
            success = false;
        } 
        return success;
    }
    
    public static MenuItem getMenuItem(String name) {
        try {
            PreparedStatement s = connection.prepareStatement(MENU_ITEM);
            s.setString(1, name);
            ResultSet r = s.executeQuery();
            
            if (r.next()) {
                return new MenuItem(r.getString(1), r.getDouble(2), r.getString(3), r.getBoolean(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuItemProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static List<MenuItem> getAllMenuItems() {
        List<MenuItem> menuItems = new ArrayList();
        try {
            ResultSet results = connection.createStatement()
                    .executeQuery(GET_ALL_ITEMS);
            while (results.next()) {
                menuItems.add(
                        new MenuItem(results.getString(MenuItem.NAME),
                        results.getDouble(MenuItem.PRICE),
                        results.getString(MenuItem.DESC),
                        results.getBoolean(MenuItem.AVAIL)));
            }
        }
        catch (SQLException e) {
            System.out.println("Could not get all items");
        }     
        return menuItems;
    }
    
    public static List<MenuItem> getAllAvailableMenuItems() {
        List<MenuItem> menuItems = new ArrayList();
        try {
            ResultSet results = connection.createStatement()
                    .executeQuery(GET_ALL_AVAIL_ITEMS);
            while (results.next()) {
                menuItems.add(
                        new MenuItem(results.getString(MenuItem.NAME),
                        results.getDouble(MenuItem.PRICE),
                        results.getString(MenuItem.DESC),
                        results.getBoolean(MenuItem.AVAIL)));
            }
        }
        catch (SQLException e) {
            System.out.println("Could not get all available items");
        }     
        return menuItems;
    }
    
    public static List<MenuItem> getMenuItemsByType(ItemType itemType) {
        List<MenuItem> menuItems = new ArrayList<>();
        
        try {
            PreparedStatement statement = connection.prepareStatement(
                    String.format(MENU_ITEMS_BY_TYPE, itemType.getId()));
            ResultSet results = statement.executeQuery();
            
            while (results.next()) {
                menuItems.add(new MenuItem(results.getString(MenuItem.NAME), 
                        results.getDouble(MenuItem.PRICE), 
                        results.getString(MenuItem.DESC), 
                        results.getBoolean(MenuItem.AVAIL)));
            }
        }
        catch (SQLException e) {
            System.out.println("Could not get menu item");
        }
        
        return menuItems;
    }
    
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
    
    public static List<Customization> getCustomizationsWithMenuCustomizationId(MenuItem menuItem) {
        List<Customization> customizations = new ArrayList();
        try {
            PreparedStatement statement = connection
                    .prepareStatement(GET_CUSTOMIZATIONS_WITH_MENU_CUSTOMIZATION_ID);
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
    
    public static List<ItemType> getItemTypes(MenuItem menuItem) {
        List<ItemType> itemTypes = new ArrayList();
        try {
            PreparedStatement statement = connection
                    .prepareStatement(GET_TYPES);
            statement.setString(1, menuItem.name);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                itemTypes.add(
                        new ItemType(results.getInt(ItemType.ID), 
                        results.getString(ItemType.NAME),
                        results.getBoolean(ItemType.VISIBLE)));
            }
        }
        catch (SQLException e) {
            System.out.println("Could not get item types");
        }     
        return itemTypes;
    }
    
    public static boolean addCustomization(MenuItem menuItem, 
            int customizationId) {
        boolean success = true;
        try {
            PreparedStatement statement = connection
                    .prepareStatement(ADD_CUSTOM);
            statement.setString(1, menuItem.name);
            statement.setInt(2, customizationId);
            statement.executeQuery();
        }
        catch (SQLException e) {
            System.out.println("Could not add customization to menu item"+e.toString());
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
            System.out.println("Could not remove customization from menu item: "+e.toString());
            success = false;
        } 
        return success;
    }
    
    public static boolean addMenuItemType(MenuItem menuItem, 
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
    
    public static boolean removeMenuItemType(MenuItem menuItem, 
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
            System.out.println("Could not remove type from menu item: "+e.toString());
            success = false;
        } 
        return success;
    }
}