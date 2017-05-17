/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stanchefskitchen.data.models;

/**
 *
 * @author Kyle
 */
public class MenuItemType {
    private int menuItemId;
    private int itemTypeId;
    
    public static final String ITEM_ID = "menuItemId";
    public static final String TYPE_ID = "itemTypeId";

    public MenuItemType(int menuItemId, int itemTypeId) {
        this.menuItemId = menuItemId;
        this.itemTypeId = itemTypeId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public int getItemTypeId() {
        return itemTypeId;
    }

    
}
