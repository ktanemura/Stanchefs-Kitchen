/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stanchefskitchen.data.models;

/**
 *
 * @author Ryan
 */
public class OrderItem {
    public int id;
    public int orderId;
    public String itemName;
    public int quantity;
    
    public OrderItem (int id, int orderId, String itemName, int quantity) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.quantity = quantity;
    }
        }
