/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stanchefskitchen.data.models;

import java.util.List;

/**
 *
 * @author Kyle
 */
public class MenuItemDisplay {
    private MenuItem item;
    private String types;

    public MenuItemDisplay(MenuItem item, List<ItemType> types) {
        this.item = item;
        StringBuilder sb = new StringBuilder();
        
        for (ItemType type : types) {
            sb.append(type.getName() + ", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        this.types = sb.toString();
    }

    
}
