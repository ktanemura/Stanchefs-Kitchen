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
    public final MealType mealType;
    
    public MenuItem(String name, double price, String description, 
            MealType mealType) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.mealType = mealType;
    }
}
