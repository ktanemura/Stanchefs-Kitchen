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
import java.util.stream.Collectors;

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
        
        itemTypes.forEach(type -> {
            if (type.getVisible()) {
                List<MenuItem> menuItems = MenuItemProvider.getMenuItemsByType(type)
                        .stream()
                        .filter(item -> item.available)
                        .collect(Collectors.toList());
                menu.put(type, menuItems);
            }
        });
        
        return menu;
    }
}
