/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stanchefskitchen.presentation.employee;

import com.stanchefskitchen.data.models.Order;
import com.stanchefskitchen.data.models.OrderStatus;
import com.stanchefskitchen.data.providers.OrderProvider;
import java.util.List;
import javax.faces.component.UIInput;

/**
 *
 * @author Kyle
 */
public class OrderListController {
    private static List<Order> ordersList;
    private List<Order> searchResults;
    private UIInput customerFirstName;
    private UIInput customerLastName;
    
    public List<Order> getOrders() {
        ordersList = OrderProvider.getIncompleteOrders();
        return ordersList;
    }
    
    /*
    public List<Order> getSearchResults() {
        firstName =
    }
*/
    public boolean isCancelAvailable(Order o) {
        return o.orderStatus == OrderStatus.RECEIVED;
    }
    
    public String cancel(Order o) {
        
    }
}
