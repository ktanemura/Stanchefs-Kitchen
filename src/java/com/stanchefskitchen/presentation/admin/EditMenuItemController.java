package com.stanchefskitchen.presentation.admin;

import com.stanchefskitchen.data.models.Customization;
import com.stanchefskitchen.data.models.ItemType;
import com.stanchefskitchen.data.providers.MenuItemProvider;
import com.stanchefskitchen.data.models.MenuItem;
import com.stanchefskitchen.data.models.MenuItemType;
import com.stanchefskitchen.data.providers.CustomizationProvider;
import com.stanchefskitchen.data.providers.ItemTypeProvider;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Ryan
 */
public class EditMenuItemController implements Serializable  {
    private final String ADD_MENU_ITEM = "admin_add_menuitem";
    private final String ADMIN_HOME = "admin_home";
    public MenuItem menuItem;
    public int selectedTypeId;
    public int selectedCustomId;

    public int getSelectedCustomId() {
        return selectedCustomId;
    }

    public void setSelectedCustomId(int selectedCustomId) {
        this.selectedCustomId = selectedCustomId;
    }
    
    public void addMenuItemCustomization() {
        MenuItemProvider.addCustomization(menuItem, selectedCustomId);
    }
    
    public void addMenuItemType() { 
        if (!MenuItemProvider.addMenuItemType(menuItem, 
                new MenuItemType(menuItem.name, selectedTypeId))) {
            
        }
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
        ItemTypeProvider.swapVisibility(itemType.getId());
    }
    
    public void deleteMenuItemCustomization(Customization custom) {
        System.out.println(custom.getId());
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
    
    public List<Customization> getAvailableCustomizations() {
        return CustomizationProvider.getCustomizations();
    }
    
    public List<Customization> getItemCustomizations() {
        return MenuItemProvider.getCustomizations(menuItem);
    }
    
    public String goToAddMenuItem() {
        return ADD_MENU_ITEM;
    }
    
    public String goToAdminHome() {
        return ADMIN_HOME;
    }
}
