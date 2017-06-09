package com.stanchefskitchen.presentation.cart;

import com.stanchefskitchen.data.models.Customization;
import com.stanchefskitchen.data.models.MenuItem;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Brittany Berlanga
 */
public class CartOrderItem {
    public MenuItem menuItem;
    public int quantity;
    public List<Customization> customizations;
    private NumberFormat formatter = NumberFormat.getCurrencyInstance();
    
    
    public CartOrderItem(MenuItem menuItem) {
        this.menuItem = menuItem;
        this.customizations = new ArrayList();
    }
    
    public double getTotal() {
        double singlePrice = menuItem.price;
        singlePrice = customizations.stream().map((cus) -> cus.price)
                .reduce(singlePrice, (accumulator, _item) -> accumulator 
                        + _item);
        return singlePrice * quantity;
    }
    
    public String getStringTotal() {
        return formatter.format(getTotal());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj != null && obj.getClass() == CartOrderItem.class)) {
            return false;
        }
        CartOrderItem other = (CartOrderItem) obj;
        if(!menuItem.name.equals(other.menuItem.name)) {
            return false;
        }
        return equalCustomizations(customizations, other.customizations);
    }
    
    public static boolean equalCustomizations(@NotNull 
            List<Customization> l1, @NotNull List<Customization> l2) {
        if (l1.size() != l2.size()) {
            return false;
        }
        boolean identical = true;
        for (Customization cus : l1) {
            if (!l2.contains(cus)) {
                identical = false;
            }
        }
        return identical;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Customization> getCustomizations() {
        return customizations;
    }

    public void setCustomizations(List<Customization> customizations) {
        this.customizations = customizations;
    }
    
}
