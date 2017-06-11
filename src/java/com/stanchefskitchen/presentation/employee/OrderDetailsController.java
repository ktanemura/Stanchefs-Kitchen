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
import com.stanchefskitchen.data.models.OrderStatus;
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
        return "/order_details.xhtml?faces-redirect=true";
    }
    
    public String cusDetails(Order o) {
        orderId = o.id;
        check = true;
        order = o;
        return "/cus_order_details.xhtml?faces-redirect=true";
    }
    
    public boolean isPayAvailable() {
        Bill bill = BillProvider.get_bill(order.billId);
        return !bill.paid && userSession.getAccount().type.equals(AccountType.employee);
    }
    
    public String payBill() {
        Bill bill = BillProvider.get_bill(order.billId);
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
        Bill bill = BillProvider.get_bill(order.billId);
        if (bill != null) {
            return String.format(total, bill.price);
        }
        
        return String.valueOf(order.billId);
    }
    
    public boolean payEnabled() {
        return order.getOrderStatus() == OrderStatus.COMPLETE && userSession.getAccount().type == AccountType.employee;
    }
}
