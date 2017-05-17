package com.stanchefskitchen.data.models;

/**
 * 
 * @author Ryan
 */
public class Order {
    public int id;
    public int customerId;
    public int billId;
    public OrderStatus orderStatus;
    
    public Order (int id, int customerId, int billId, OrderStatus orderStatus) {
        this.customerId = customerId;
        this.billId = billId;
        this.orderStatus = orderStatus;
    }
}
