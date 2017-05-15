package com.stanchefskitchen.data.models;

/**
 *
 * @author brittanyberlanga
 */
public class MenuItem {
    //TODO add meal type and item type
    public String name;
    public double price;
    public String description;
    
    public MenuItem(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
