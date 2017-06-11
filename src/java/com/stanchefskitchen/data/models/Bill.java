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
public class Bill {
    public int id;
    public int employeeId;
    public double price;
    public double tip;
    public boolean paid;
    
    public static final String ID = "id";
    public static final String EMPL_ID = "employeeId";
    public static final String TOTAL = "total";
    public static final String TIP = "tip";
    public static final String PAID = "paid";
    
    public Bill(int id, int employeeId, double price, double tip, boolean paid) {
        this.id = id;
        this.employeeId = employeeId;
        this.price = price;
        this.tip = tip;
        this.paid = paid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTip() {
        return tip;
    }

    public void setTip(double tip) {
        this.tip = tip;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
    
    
    
    
}
