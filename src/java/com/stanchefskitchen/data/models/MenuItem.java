package com.stanchefskitchen.data.models;

/**
 *
 * @author Brittany Berlanga
 */
public class MenuItem {
    //TODO add meal type and item type
    public final String name;
    public final double price;
    public final String description;
    
    public MenuItem(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
