package com.stanchefskitchen.data.models;

/**
 * 
 * @author Ryan
 */
public class Order {
    public int id;
    public int customerId;
    public int billId;
    public boolean isDelivery;
    public OrderStatus orderStatus;
    
    public Order (int id, int customerId, int billId, boolean isDelivery, OrderStatus orderStatus) {
        this.customerId = customerId;
        this.billId = billId;
        this.orderStatus = orderStatus;
        this.isDelivery = isDelivery;
    }
}
