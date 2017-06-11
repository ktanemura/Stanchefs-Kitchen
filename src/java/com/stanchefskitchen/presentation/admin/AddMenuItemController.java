/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stanchefskitchen.presentation.admin;

import com.stanchefskitchen.data.models.ItemType;
import com.stanchefskitchen.data.models.MenuItem;
import com.stanchefskitchen.data.models.MenuItemType;
import com.stanchefskitchen.data.providers.ItemTypeProvider;
import com.stanchefskitchen.data.providers.MenuItemProvider;
import java.io.Serializable;
import javax.faces.component.UIInput;

/**
 *
 * @author Ryan
 */
public class AddMenuItemController implements Serializable {
    private UIInput itemNameUI;
    private UIInput priceUI;
    private UIInput descriptionUI;
    private UIInput itemTypeUI;

    public void addMenuItem() {
        String itemName = itemNameUI.getLocalValue().toString().trim();
        double price = Double.parseDouble(priceUI.getLocalValue().toString());
        String description = descriptionUI.getLocalValue().toString().trim();
        String itemTypeStr = itemTypeUI.getLocalValue().toString().trim();
    
        // Add menu item
        MenuItem menuItem = new MenuItem(itemName, price, description, true);
        if (!MenuItemProvider.addMenuItem(menuItem)) {
            System.out.println("Couldn't add menu item"); // throws error
            return;
        }
        
        // Create or get item type id
        ItemType itemType = ItemTypeProvider.getTypeByName(itemTypeStr);
        int itemTypeId;
        if (itemType == null) {
            itemTypeId = ItemTypeProvider.addItemType(itemTypeStr);
        }
        else {
            itemTypeId = itemType.getId();
        }
        
        if (!MenuItemProvider.addMenuItemType(menuItem, new MenuItemType(itemName, itemTypeId))) {
            // throw error
        }
    }

    public UIInput getItemNameUI() {
        return itemNameUI;
    }

    public void setItemNameUI(UIInput itemNameUI) {
        this.itemNameUI = itemNameUI;
    }

    public UIInput getPriceUI() {
        return priceUI;
    }

    public void setPriceUI(UIInput priceUI) {
        this.priceUI = priceUI;
    }

    public UIInput getDescriptionUI() {
        return descriptionUI;
    }

    public void setDescriptionUI(UIInput descriptionUI) {
        this.descriptionUI = descriptionUI;
    }

    public UIInput getItemTypeUI() {
        return itemTypeUI;
    }

    public void setItemTypeUI(UIInput itemTypeUI) {
        this.itemTypeUI = itemTypeUI;
    }
   
}
