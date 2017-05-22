package com.stanchefskitchen.data.models;

/**
 *
 * @author Brittany Berlanga
 */
public class MenuItem {
    public final String name;
    public final double price;
    public final String description;
    
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String DESC = "description";
    
    public MenuItem(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
