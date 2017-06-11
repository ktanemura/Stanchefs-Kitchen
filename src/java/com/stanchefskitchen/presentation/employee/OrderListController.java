/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stanchefskitchen.presentation.employee;

import com.stanchefskitchen.data.models.Order;
import com.stanchefskitchen.data.models.OrderStatus;
import com.stanchefskitchen.data.providers.OrderProvider;
import com.stanchefskitchen.presentation.login.Session;
import java.util.List;
import javax.faces.component.UIInput;

/**
 *
 * @author Kyle
 */
public class OrderListController {
    private List<Order> ordersList;
    private List<Order> searchResults;
    private UIInput customerFirstName;
    private UIInput customerLastName;
    private List<Order> customerOrders;
    private OrderDetailsController orderDetailsController;
    
    private Session userSession;

    public OrderDetailsController getOrderDetailsController() {
        return orderDetailsController;
    }

    public void setOrderDetailsController(OrderDetailsController orderDetailsController) {
        this.orderDetailsController = orderDetailsController;
    }

   
   

    public Session getUserSession() {
        return userSession;
    }

    public void setUserSession(Session userSession) {
        this.userSession = userSession;
    }
    
    public List<Order> getCustomerOrders() {
        customerOrders = OrderProvider.getOrdersByCustomerId(userSession.getAccount().id);
        return customerOrders;
    }
    
    public List<Order> getOrdersList() {
        ordersList = OrderProvider.getIncompleteOrders();
        return ordersList;
    }
    
    public String getDetailsValue() {
        return "View Order Details";
    }
    
    public String getOrderDetails(Order o) {

        return orderDetailsController.details(o);
    }
    
    /*
    public List<Order> getSearchResults() {
        firstName =
    }
*/
    public boolean isCancelAvailable(Order o) {
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
        ordersList.remove(o);
        return null;
    }

    public boolean isCookingAvailable(Order o) {
        return o.orderStatus == OrderStatus.RECEIVED;
    }
    
    
    public String setOrderCooking(Order o) {
        OrderProvider.changeOrderStatus(o.id, OrderStatus.COOKING);
        
        return null;
    }
    
    public boolean isReadyAvailable(Order o) {
        return o.orderStatus == OrderStatus.COOKING;
    }
    
    
    public String getOrderReadyValue(Order o) {
        if (o.isDelivery) {
            return "Send on Delivery";
        }
        else {
            return "Ready for Pickup";
        }
       
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
    
    public String getAdvanceValue(Order o) {
        if (isCookingAvailable(o)) {
            return "Begin Cooking";
        }
        else if (isReadyAvailable(o)) {
            return getOrderReadyValue(o);
        }
        else if (isCompleteAvailable(o)) {
            return "Complete Order";
        }
        
        return null;
    }
    
    
    public String advanceOrder(Order o) {
        switch (o.orderStatus) {
            case RECEIVED:
                OrderProvider.changeOrderStatus(o.id, OrderStatus.COOKING);
                break;
            case COOKING:
                setOrderReady(o);
                break;
            case READY:
                OrderProvider.changeOrderStatus(o.id, OrderStatus.COMPLETE);
                ordersList.remove(o);
                break;
            case DELIVERING:
                OrderProvider.changeOrderStatus(o.id, OrderStatus.COMPLETE); 
                ordersList.remove(o);
                break;
        }
        return null;
    }
}
