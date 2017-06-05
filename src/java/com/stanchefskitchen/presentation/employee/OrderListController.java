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
        ordersList.remove(o);
        return o.orderStatus == OrderStatus.RECEIVED;
    }
    
    public String getCancelValue(Order o) {
        if (isCancelAvailable(o)) {
            return "Cancel Order";
        }
        
        return null;
    }
    
    public String cancelOrder(Order o) {
        OrderProvider.changeOrderStatus(o.id, OrderStatus.CANCELLED);
        
        return null;
    }
    
    public boolean isCookingAvailable(Order o) {
        return o.orderStatus == OrderStatus.RECEIVED;
    }
    
    public String getCookingValue(Order o) {
        if (isCookingAvailable(o)) {
            return "Begin Cooking";
        }
        
        return null;
    }
    
    public String setOrderCooking(Order o) {
        OrderProvider.changeOrderStatus(o.id, OrderStatus.COOKING);
        
        return null;
    }
    
    public boolean isReadyAvailable(Order o) {
        return o.orderStatus == OrderStatus.COOKING;
    }
    
    
    public String getOrderReadyValue(Order o) {
        if (isReadyAvailable(o) && o.isDelivery) {
            return "Send on Delivery";
        }
        else if (isReadyAvailable(o)){
            return "Ready for Pickup";
        }
        
        return null;
    }
    
    public String setOrderReady(Order o) {
        if (o.isDelivery) {
            OrderProvider.changeOrderStatus(o.id, OrderStatus.DELIVERING);
        }
        else {
            OrderProvider.changeOrderStatus(o.id, OrderStatus.READY);
        }
        
        return null;
    }
    
    public boolean isCompleteAvailable(Order o) {
        return o.orderStatus == OrderStatus.READY ? true : o.orderStatus == OrderStatus.DELIVERING;
    }
    
    public String getCompleteValue(Order o) {
        if (isCompleteAvailable(o)) {
            return "Complete Order";
        }
        
        return null;
    }
    
    public String completeOrder(Order o) {
        OrderProvider.changeOrderStatus(o.id, OrderStatus.COMPLETE);
        ordersList.remove(o);
        return null;
    }
}
