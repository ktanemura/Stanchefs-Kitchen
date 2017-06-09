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
public class OrderItemCustomization {
    private int orderItemId;
    private int itemCustomizationId;
    
    public static final String ORDER_ITEM_ID = "orderItemId";
    public static final String ITEM_CUSTOMIZATION_ID = "menuItemCustomizationId";

    public OrderItemCustomization(int orderItemId, int itemCustomizationId) {
        this.orderItemId = orderItemId;
        this.itemCustomizationId = itemCustomizationId;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public int getItemCustomizationId() {
        return itemCustomizationId;
    }
    
        }
