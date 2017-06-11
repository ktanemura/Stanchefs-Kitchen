package com.stanchefskitchen.presentation.menu;

import com.stanchefskitchen.data.models.AccountType;
import com.stanchefskitchen.data.models.ItemType;
import com.stanchefskitchen.data.models.MenuItem;
import com.stanchefskitchen.data.providers.ItemTypeProvider;
import com.stanchefskitchen.data.providers.MenuItemProvider;
import com.stanchefskitchen.presentation.NavController;
import com.stanchefskitchen.presentation.admin.EditMenuItemController;
import com.stanchefskitchen.presentation.cart.AddItemController;
import com.stanchefskitchen.presentation.login.Session;
import java.util.List;

/**
 *
 * @author Tyler Wong
 */
public class MenuController {
    private Session userSession;
    private AddItemController addItemController;
    private EditMenuItemController editMenuItemController;
    public String actionLabel = "";
    
    public List<ItemType> getItemTypes() {
        if (getActionEnabled() && userSession.getAccount().type == AccountType.admin) {
            return ItemTypeProvider.getAllItemTypes();
        }
        return ItemTypeProvider.getAllVisibleItemTypes();
    }
    
    public List<MenuItem> getMenuItemsByItemType(ItemType itemType) {
        return MenuItemProvider.getMenuItemsByType(itemType);
    }
    
    public String getActionLabel() {
        if (userSession != null && userSession.getAccount() != null) {
            switch(userSession.getAccount().type) {
                case admin:
                    actionLabel = "Edit Item";
                    break;
                case customer:
                    actionLabel = "Add Item";
                    break;
                default:
                    break;
            }
        }
        
        return actionLabel;
    }
    
    public Session getUserSession() {
        return userSession;
    }

    public void setUserSession(Session userSession) {
        this.userSession = userSession;
    }
    
    public boolean getActionEnabled() {
        return userSession != null && userSession.getAccount() != null;
    }
    
    public AddItemController getAddItemController() {
        return addItemController;
    }

    public void setAddItemController(AddItemController addItemController) {
        this.addItemController = addItemController;
    }
    
    public EditMenuItemController getEditMenuItemController() {
        return editMenuItemController;
    }

    public void setEditMenuItemController(EditMenuItemController editMenuItemController) {
        this.editMenuItemController = editMenuItemController;
    }
    
    public String fireAction(MenuItem item) {
        String nextPage = "";
        if (userSession != null && userSession.getAccount() != null) {
            switch(userSession.getAccount().type) {
                case admin:
                    editMenuItemController.setMenuItem(item);
                    nextPage = NavController.EDIT_ITEM;
                    break;
                case customer:
                    addItemController.setCurrentMenuItem(item);
                    nextPage = NavController.ADD_ITEM;
                    break;
                default:
                    break;
            }
        }
        
        return nextPage;
    }
}
