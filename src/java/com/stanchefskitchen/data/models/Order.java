package com.stanchefskitchen.data.models;

/**
 * 
 * @author Ryan
 */
public class Order {
    public enum Status {
        PREPARING, READY, DELIVERING, COMPLETE;
    }
    
    public int id;
    public int customerId;
    public int billId;
    public Status orderStatus;
    
    public Order (int id, int customerId, int billId, Status orderStatus) {
        this.customerId = customerId;
        this.billId = billId;
        this.orderStatus = orderStatus;
    }
}
