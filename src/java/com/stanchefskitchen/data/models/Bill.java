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
    public static final String PRICE = "price";
    public static final String TIP = "tip";
    public static final String PAID = "paid";
    
    public Bill(int id, int employeeId, double price, double tip, boolean paid) {
        this.id = id;
        this.employeeId = employeeId;
        this.price = price;
        this.tip = tip;
        this.paid = paid;
    }
    
    
}
