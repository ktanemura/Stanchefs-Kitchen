package com.stanchefskitchen.presentation.admin;

import com.stanchefskitchen.data.models.ItemType;
import com.stanchefskitchen.data.models.MenuItem;
import com.stanchefskitchen.data.providers.ItemTypeProvider;
import com.stanchefskitchen.data.providers.MenuItemProvider;
import com.stanchefskitchen.data.providers.MenuProvider;
import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Ryan
 */
public class AdminHomeController implements Serializable {
    private final String EMPL_ACCOUNT = "admin_create_employee";
    private final String ADD_MENUITEM = "admin_add_menuitem";
    private final String EDIT_CUSTOM = "admin_edit_custom";
    private final String EDIT_MENUITEM = "admin_edit_menuitem";
    private EditMenuItemController editMenuItemController;
    
    public Map<ItemType, List<MenuItem>> menuMap = MenuProvider.getMenu();
    
    public List<ItemType> getItemTypes() {
        return new ArrayList<>(menuMap.keySet());
    }
    public List<MenuItem> getMenuItemsByItemType(ItemType itemType) {
        return menuMap.get(itemType);
    }
    
    public String goToCreateEmplAccount() {
        return EMPL_ACCOUNT;
    }
    
    public String goToEditCustomizations() {
        return EDIT_CUSTOM;
    }
    
    public String goToEditMenuItem() {
        // fer testing
        List<MenuItem> items = MenuItemProvider.getAllAvailableMenuItems();
        for (MenuItem cur_item : items) {
            if (cur_item.name.equals("Mozzarella Pizza")) {
                editMenuItemController.menuItem = cur_item;
            }
        }
        
        return EDIT_MENUITEM;
    }

    public String goToAddMenuItem() {
        return ADD_MENUITEM;
    }
    
    public EditMenuItemController getEditMenuItemController() {
        return editMenuItemController;
    }

    public void setEditMenuItemController(EditMenuItemController editMenuItemController) {
        this.editMenuItemController = editMenuItemController;
    }
}
