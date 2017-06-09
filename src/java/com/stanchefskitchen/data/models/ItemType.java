/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stanchefskitchen.data.models;

/**
 *
 * @author Kyle
 */
public class ItemType {
    private int id;
    private String name;
    private boolean visible;
    
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String VISIBLE = "visible";

    public ItemType(int id, String name, boolean visible) {
        this.id = id;
        this.name = name;
        this.visible = visible;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public boolean getVisible() {
        return visible;
    }
}
