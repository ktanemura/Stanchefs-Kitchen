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
    private Date expDate;
    
    public static final String NUMBER = "number";
    public static final String CUSTOMER_ID = "customerId";
    public static final String CRC = "crc";
    public static final String ADDRESS = "address";
    public static final String EXP_DATE = "expdate";
    
    public CreditCard(String number, int customerId, int crc, String address, Date expDate) {
        this.number = number;
        this.customerId = customerId;
        this.crc = crc;
        this.address = address;
        this.expDate = expDate;
    }
    
    public String getNumber() {
        return number;
    }
    
    public String getSafeNumber() {
        String safeNumber = "";
        for (int index = 0; index < number.length(); index++) {
            if (index < number.length() - 4) {
                safeNumber += "*";
            }
            else {
                safeNumber += number.charAt(index);
            }
        }
        return safeNumber;
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

    public Date getExpDate() {
        return expDate;
    }
    
    public String getFormattedExpDate() {
        int year = expDate.toLocalDate().getYear();
        int month = expDate.toLocalDate().getMonthValue();
        
        return month + "/" + year;
    }
}
