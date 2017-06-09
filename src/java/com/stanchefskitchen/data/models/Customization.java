package com.stanchefskitchen.data.models;

/**
 *
 * @author Brittany Berlanga
 */
public class Customization {
    public static final String ID = "id";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    
    public final int id;
    public final String description;
    public final double price;

    public Customization(int id, String description, double price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
    
}
