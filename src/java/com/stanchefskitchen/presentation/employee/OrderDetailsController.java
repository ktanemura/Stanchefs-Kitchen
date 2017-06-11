/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stanchefskitchen.presentation.employee;

import com.stanchefskitchen.data.models.AccountType;
import com.stanchefskitchen.data.models.Bill;
import com.stanchefskitchen.data.models.Order;
import com.stanchefskitchen.data.models.OrderItem;
import com.stanchefskitchen.data.providers.BillProvider;
import com.stanchefskitchen.data.providers.OrderProvider;
import com.stanchefskitchen.presentation.login.Session;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kyle
 */
public class OrderDetailsController {
    private int orderId;
    private Order order;
    private Bill bill;
    private List<OrderItem> items;
    private boolean check = false;
    private Session userSession;

    public Session getUserSession() {
        return userSession;
    }

    public void setUserSession(Session userSession) {
        this.userSession = userSession;
    }
    
    
    
    public String details(Order o) {
        orderId = o.id;
        check = true;
        order = o;
        bill = BillProvider.get_bill(o.billId);
        return "/order_details.xhtml?faces-redirect=true";
    }
    
    public boolean isPayAvailable() {
        return !bill.paid && userSession.getAccount().type.equals(AccountType.employee);
    }
    
    public String payBill() {
        BillProvider.set_paid(bill.id);
        return null;
    }
    
    public List<OrderItem> getItems() {
        if (check) {
            items = OrderProvider.getOrderItems(orderId);
            return items;
        }
        
        return null;
    }
    
    public String billTotal() {
        String total = "Total: $%.2f";
        if (bill != null) {
            double billTotal = bill.price;
        
            return String.format(total, billTotal);
        }
        return null;
    }
}
