/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stanchefskitchen.data.models;

/**
 * Specifies a customization that is available for an item
 * @author Kyle
 */
public class MenuItemCustomization {
    // Attributes
    private int id;
    private String itemName;
    private int customizationId;
    
    // Attribute names
    public String ID = "id";
    public String ITEM_NAME = "itemName";
    public String CUSTOMIZATION = "customizationId";

    public MenuItemCustomization(int id, String itemName, int customizationId) {
        this.id = id;
        this.itemName = itemName;
        this.customizationId = customizationId;
    }

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public int getCustomizationId() {
        return customizationId;
    }    
}
