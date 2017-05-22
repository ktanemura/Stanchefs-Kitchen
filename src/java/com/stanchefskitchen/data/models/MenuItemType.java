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
    private String menuItem;
    private int itemTypeId;
    
    public static final String ITEM_ID = "menuItem";
    public static final String TYPE_ID = "itemTypeId";

    public MenuItemType(String menuItem, int itemTypeId) {
        this.menuItem = menuItem;
        this.itemTypeId = itemTypeId;
    }

    public String getMenuItem() {
        return menuItem;
    }

    public int getItemTypeId() {
        return itemTypeId;
    }

    
}
