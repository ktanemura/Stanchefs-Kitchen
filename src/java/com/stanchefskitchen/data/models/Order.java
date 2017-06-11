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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public boolean isIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(boolean isDelivery) {
        this.isDelivery = isDelivery;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    
}
