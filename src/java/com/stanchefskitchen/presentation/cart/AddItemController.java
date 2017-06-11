package com.stanchefskitchen.presentation.cart;

import com.stanchefskitchen.data.models.Customization;
import com.stanchefskitchen.data.models.MenuItem;
import com.stanchefskitchen.data.providers.MenuItemProvider;
import com.stanchefskitchen.presentation.NavController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Brittany Berlanga
 */
public class AddItemController implements Serializable {

    private ShoppingCartController shoppingCart;
    private MenuItem currentMenuItem;
    private List<Customization> customizations;
    private Map<Integer, Boolean> selectedCustomizations;
    private Integer quantity;
    private double total;

    /**
     * Creates a new instance of AddItemController
     */
    public AddItemController() {
        this.customizations = new ArrayList();
        this.selectedCustomizations = new HashMap();
    }

    public MenuItem getCurrentMenuItem() {
        return currentMenuItem;
    }

    public String addItemToShoppingCart() {
        CartOrderItem orderItem = new CartOrderItem(currentMenuItem);
        orderItem.quantity = quantity;

        for (Customization cus : customizations) {
            if (selectedCustomizations.get(cus.id)) {
                orderItem.customizations.add(cus);
            }
        }
        shoppingCart.addItem(orderItem);
        return NavController.CUS_HOME;
    }

    public void validateQuantity(FacesContext context,
            UIComponent component, Object value) {
        try {
            if ((quantity = Integer.valueOf(value.toString())) <= 0) {
                FacesMessage msg = new FacesMessage("Invalid quantity",
                        "Invalid quantity");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        } catch (NumberFormatException ex) {
            FacesMessage msg = new FacesMessage("Quantity must be a number", 
                    "Quantity must be a number");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

    public void setCurrentMenuItem(MenuItem currentMenuItem) {
        this.currentMenuItem = currentMenuItem;
        this.quantity = 1;
        this.customizations = new ArrayList();
        this.selectedCustomizations = new HashMap();
        if (currentMenuItem != null) {
            this.customizations = MenuItemProvider
                    .getCustomizations(currentMenuItem);
            customizations.stream().forEach((cus) -> {
                selectedCustomizations.put(cus.id, false);
            });
        }
    }

    public List<Customization> getCustomizations() {
        return customizations;
    }

    public void setCustomizations(List<Customization> customizations) {
        this.customizations = customizations;
    }

    public Map<Integer, Boolean> getSelectedCustomizations() {
        return selectedCustomizations;
    }

    public void setSelectedCustomizations(Map<Integer, Boolean> selectedCustomizations) {
        this.selectedCustomizations = selectedCustomizations;
    }

    public ShoppingCartController getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCartController shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
