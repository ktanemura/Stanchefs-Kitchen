package com.stanchefskitchen.data.models;

import java.sql.Date;

/**
 *
 * @author Tyler Wong
 */
public class CreditCard {
    private String number;
    private int customerId;
    private int crc;
    private String address;
    private Date date;
    
    public CreditCard(String number, int customerId, int crc, String address, Date date) {
        this.number = number;
        this.customerId = customerId;
        this.crc = crc;
        this.address = address;
        this.date = date;
    }
    
    public String getNumber() {
        return number;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getCrc() {
        return crc;
    }

    public String getAddress() {
        return address;
    }

    public Date getDate() {
        return date;
    }
}
