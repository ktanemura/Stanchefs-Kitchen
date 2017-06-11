package com.stanchefskitchen.data.models;

/**
 *
 * @author Brittany Berlanga
 */
public class Customization {
    public static final String ID = "id";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    
    public int id;
    public String description;
    public double price;

    public String getDisplayString() {
        return description + " - " + getPriceString();
    }
    
    public String getPriceString() {
        return String.format("$%.2f", price);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Customization(int id, String description, double price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }
}
