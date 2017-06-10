/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stanchefskitchen.presentation.admin;

import com.stanchefskitchen.data.models.Customization;
import com.stanchefskitchen.data.models.ItemType;
import com.stanchefskitchen.data.providers.MenuItemProvider;
import com.stanchefskitchen.data.models.MenuItem;
import com.stanchefskitchen.data.models.MenuItemType;
import com.stanchefskitchen.data.providers.ItemTypeProvider;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ryan
 */
public class EditMenuItemController {
    private final String ADD_MENU_ITEM = "admin_add_menuitem";
    public MenuItem menuItem;
    public int selectedTypeId;
    
    public void addMenuItemType() { 
        MenuItemProvider.addMenuItemType(menuItem, new MenuItemType(menuItem.name, selectedTypeId));
    }
    
    public int getSelectedTypeId() {
        return selectedTypeId;
    }

    public void setSelectedTypeId(int selectedTypeId) {
        this.selectedTypeId = selectedTypeId;
    }
    
    
    public String deleteMenuItemType(ItemType itemType) {
        System.out.println(itemType.getId());
        MenuItemProvider.removeMenuItemType(menuItem, new MenuItemType(menuItem.name, itemType.getId()));
        return "";
    }
    
    public void swapVisibility(ItemType itemType) {
        // Maybe? what is visibility?
        
    }
    
    public void deleteMenuItemCustomization(Customization custom) {
        MenuItemProvider.removeCustomization(menuItem, custom);
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }
    
    public List<ItemType> getItemTypes() {
        return ItemTypeProvider.getMenuItemType(menuItem.name);
    }
    
    public List<ItemType> getAvailableItemTypes() {
        return ItemTypeProvider.getAllItemTypes();
    }
    
    public List<Customization> getItemCustomizations() {
        return MenuItemProvider.getCustomizations(menuItem);
    }
    
    public String goToAddMenuItem() {
        return ADD_MENU_ITEM;
    }
}
