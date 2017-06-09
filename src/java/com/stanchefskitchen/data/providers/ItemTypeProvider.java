/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stanchefskitchen.data.providers;

import com.stanchefskitchen.data.models.ItemType;
import com.stanchefskitchen.data.models.MenuItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kyle
 */
public class ItemTypeProvider {
    private static Connection connection = DatabaseProvider.getConnection();
    
    private static final String ALL_ITEM_TYPES = "select * from ItemType;";
    private static final String GET_TYPE_ID = "select * from ItemType where id = ?;";
    private static final String GET_TYPE_NAME = "select * from ItemType where name = ?;";
    private static final String GET_MENUITEM_TYPES = "select i.id, i.name "
            + "from ItemType i join MenuItem m "
            + "on m.itemTypeId = i.id where m.name = ?;";
    private static final String GET_MENUITEMS_TYPE = "select m.name, m.price, m.description from "
            + "MenuItem m left join MenuItemType mi on m.name = mi.menuItem "
            + "where not(m.name = null) and mi.itemTypeId = ?;";
    private static final String ADD_TYPE = "insert into ItemType(name) values(?);";
    private static final String UPDATE_TYPE = "update ItemType set name = ? where id = ?;";
    private static final String DELETE_TYPE = "delete from ItemType where id = ?;";
    
    public static ItemType getTypeById(int itemTypeId) {
        ItemType type = null;
        try {
            PreparedStatement statement = connection.prepareStatement(GET_TYPE_ID);
            statement.setInt(1, itemTypeId);
            ResultSet results = statement.executeQuery();
            
            if (results.isBeforeFirst()) {
                results.next();
                type = new ItemType(
                    results.getInt(ItemType.ID),
                    results.getString(ItemType.NAME),
                    results.getBoolean(ItemType.VISIBLE));
            }
            
            return type;
        } catch (SQLException ex) {
            Logger.getLogger(ItemTypeProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return type;
    }
    
    public static ItemType getTypeByName(String name) {
        ItemType type = null;
        try {
            PreparedStatement statement = connection.prepareStatement(GET_TYPE_ID);
            statement.setString(1, name);
            ResultSet results = statement.executeQuery();
            
            if (results.isBeforeFirst()) {
                results.next();
                type = new ItemType(
                    results.getInt(ItemType.ID),
                    results.getString(ItemType.NAME),
                    results.getBoolean(ItemType.VISIBLE));
            }
            
            return type;
        } catch (SQLException ex) {
            Logger.getLogger(ItemTypeProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return type;
    }
    
    public static List<ItemType> getMenuItemType(String menuItem) {
        ArrayList<ItemType> types= new ArrayList<ItemType>();
        
        try {
            PreparedStatement ps = connection.prepareStatement(GET_MENUITEM_TYPES);
            ps.setString(1, menuItem);
            ResultSet results = ps.executeQuery();
            
            while (results.next()) {
                types.add(new ItemType(results.getInt(ItemType.ID), 
                        results.getString(ItemType.NAME),
                        results.getBoolean(ItemType.VISIBLE)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemTypeProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return types;
    }
    
    public static List<MenuItem> getMenuItemsOfType(List<String> allowedTypes) {
        /* Wait for now */
        HashSet<MenuItem> list = new HashSet<MenuItem>();
        for (String type : allowedTypes) {
            ItemType i = ItemTypeProvider.getTypeByName(type);
            try { 
                PreparedStatement s = connection.prepareStatement(GET_MENUITEMS_TYPE);
                s.setInt(1, i.getId());
                ResultSet r = s.executeQuery();
                
                while (r.next()) {
                    MenuItem item = new MenuItem(r.getString(MenuItem.NAME), 
                            r.getDouble(MenuItem.PRICE), 
                            r.getString(MenuItem.DESC),
                            r.getBoolean(MenuItem.AVAIL));
                    list.add(item);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ItemTypeProvider.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new ArrayList<MenuItem>(list);
    }
    
    public static List<ItemType> getAllItemTypes() {
        List<ItemType> itemTypes = new ArrayList<>();
        
        try {
            PreparedStatement statement = connection.prepareStatement(ALL_ITEM_TYPES);
            ResultSet results = statement.executeQuery();
            
            while (results.next()) {
                itemTypes.add(new ItemType(results.getInt(ItemType.ID), 
                        results.getString(ItemType.NAME),
                        results.getBoolean(ItemType.VISIBLE)));
            }
        }
        catch (SQLException e) {
            System.out.println("Could not get item types");
        }
        
        return itemTypes;
    }
    
    public static int addItemType(String name) {
        try {
            PreparedStatement s = connection.prepareStatement(ADD_TYPE, 
                    Statement.RETURN_GENERATED_KEYS);
            s.setString(1, name);
            s.executeUpdate();
            
            ResultSet generatedKeys = s.getGeneratedKeys();
            generatedKeys.next();
            
            return generatedKeys.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(ItemTypeProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public static void updateItemType(int id, String newName) {
        try {
            PreparedStatement s = connection.prepareStatement(UPDATE_TYPE);
            s.setInt(2, id);
            s.setString(1, newName);
            s.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ItemTypeProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void deleteItemType(int id) {
        try {
            PreparedStatement s = connection.prepareStatement(DELETE_TYPE);
            s.setInt(1, id);
            s.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ItemTypeProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
