package com.stanchefskitchen.data.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brittany Berlanga
 */
public class MenuItem {

    public final String name;
    public final double price;
    public final String description;
    public final boolean available;
    
    public final List<ItemType> itemTypes;
    
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String DESC = "description";
    public static final String AVAIL = "available";
    
    public MenuItem(String name, double price, String description, boolean 
            available) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.available = available;
        this.itemTypes = new ArrayList();
    }
    
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    
    public String getFormattedPrice() {
        return "$" + String.valueOf(price);
    }

    public String getDescription() {
        return description;
    }

    public boolean isAvailable() {
        return available;
    }

    public List<ItemType> getItemTypes() {
        return itemTypes;
    }
    
    @Override
    public boolean equals(Object o) {
        return o != null && o.getClass() == MenuItem.class 
                && ((MenuItem) o).name.equals(name);
    }
}
