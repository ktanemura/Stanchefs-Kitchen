/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stanchefskitchen.data.providers;

import com.stanchefskitchen.data.models.ItemType;
import com.stanchefskitchen.data.models.MenuItem;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kyle
 */
public class MenuProvider {

    public static List<MenuItem> getSpecificItems(List<String> allowedTypes) {
        HashSet<String> allowed = new HashSet<String>(allowedTypes);
        ArrayList<MenuItem> filteredItems = new ArrayList<MenuItem>();
        List<MenuItem> allItems = MenuItemProvider.getAllMenuItems();
        List<ItemType> types;
        for (MenuItem item : allItems) {
            types = ItemTypeProvider.getMenuItemType(item.name);
            for (ItemType type : types) {
                if (allowed.contains(type.getName())) {
                    filteredItems.add(item);
                    break;
                }
            }
        }
        
        return filteredItems;
    }

    public static Map<ItemType, List<MenuItem>> getMenu() {
        Map<ItemType, List<MenuItem>> menu = new LinkedHashMap<>();
        List<ItemType> itemTypes = ItemTypeProvider.getAllItemTypes();
        
        itemTypes.forEach(type ->
            menu.put(type, MenuItemProvider.getMenuItemsByType(type))
        );
        
        return menu;
    }
    
    public static void main(String[] args) {
        Map<ItemType, List<MenuItem>> menu = getMenu();
        
        menu.keySet().forEach(type -> {
            System.out.println("---------------------------");
            System.out.println("ItemType: " + type.getName());
            
            menu.get(type).forEach(menuItem -> System.out.println(menuItem.name + 
                    " costs " + String.valueOf(menuItem.price)));
            
            System.out.println("---------------------------");
        });
    }
}
