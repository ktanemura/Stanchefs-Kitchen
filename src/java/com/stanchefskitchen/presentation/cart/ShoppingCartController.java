package com.stanchefskitchen.presentation.cart;

import com.stanchefskitchen.data.models.MenuItem;
import com.stanchefskitchen.presentation.NavController;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brittany Berlanga
 */
@Named(value = "shoppingCart")
@SessionScoped
public class ShoppingCartController implements Serializable {
    
    private List<CartOrderItem> orderItems;
    private NumberFormat formatter = NumberFormat.getCurrencyInstance();

    /**
     * Creates a new instance of ShoppingCart
     */
    public ShoppingCartController() {
        orderItems = new ArrayList();
    }
    
    public boolean isEmpty() {
        return orderItems.isEmpty();
    }
    
    public void addItem(CartOrderItem orderItem) {
        int equal = orderItems.indexOf(orderItem);
        if (equal >= 0) {         
            CartOrderItem duplicateItem = orderItems.get(equal);
            duplicateItem.quantity += orderItem.quantity;
        }
        else {
            orderItems.add(orderItem);
        }
    }

    public int getItemCount() {
        int count = 0;
        count = orderItems.stream().map((orderItem) -> orderItem.quantity)
                .reduce(count, Integer::sum);
        return count;
    }
    
    public double getTotal() {
        double total = 0;
        for (CartOrderItem item : orderItems) {
            total += item.getTotal();
        }
        return total;
    }
    
    public String getStringTotal() {
        return formatter.format(getTotal());
    }

    public List<CartOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<CartOrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
    public String checkout() {
        return NavController.CHECKOUT;
    }
    
    public void clearCart() {
        orderItems.clear();
    }
}
