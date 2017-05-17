package com.stanchefskitchen.data.models;

/**
 *
 * @author Brittany Berlanga
 */
public class MenuItem {
    //TODO add item type
    public final String name;
    public final double price;
    public final String description;
    public final ItemType itemType;
    
    public MenuItem(String name, double price, String description, 
            ItemType itemType) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.itemType = itemType;
    }
}
