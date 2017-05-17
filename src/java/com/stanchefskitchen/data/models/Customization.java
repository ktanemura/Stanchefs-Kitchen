package com.stanchefskitchen.data.models;

/**
 *
 * @author Brittany Berlanga
 */
public class Customization {
    public final int id;
    public final String description;
    public final double price;

    public Customization(int id, String description, double price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }
}
