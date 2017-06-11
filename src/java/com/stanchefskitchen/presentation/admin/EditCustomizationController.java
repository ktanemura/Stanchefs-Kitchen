/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stanchefskitchen.presentation.admin;

import com.stanchefskitchen.data.models.Customization;
import com.stanchefskitchen.data.models.MenuItem;
import com.stanchefskitchen.data.providers.CustomizationProvider;
import com.stanchefskitchen.data.providers.MenuItemProvider;
import java.util.List;
import javax.faces.component.UIInput;

/**
 *
 * @author Ryan
 */
public class EditCustomizationController {
    private UIInput customDesc;
    private UIInput customPrice;
    
    public void createCustomization() {
        String description = customDesc.getLocalValue().toString().trim();
        double price = Double.parseDouble(customPrice.getLocalValue().toString().trim());
    
        CustomizationProvider.addCustom(description, price);
    }

    public UIInput getCustomDesc() {
        return customDesc;
    }

    public void setCustomDesc(UIInput customDesc) {
        this.customDesc = customDesc;
    }

    public UIInput getCustomPrice() {
        return customPrice;
    }

    public void setCustomPrice(UIInput customPrice) {
        this.customPrice = customPrice;
    }
    
    public void deleteCustomization(Customization custom) {
        for (MenuItem menuItem : CustomizationProvider.getMenuItemsByCustomization(custom)) {
            MenuItemProvider.removeCustomization(menuItem, custom);
        }
        
        CustomizationProvider.deleteCustom(custom.id);
    }
    
    public List<Customization> getCustomizations() {
        return CustomizationProvider.getCustomizations();
    }
}
