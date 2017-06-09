package com.stanchefskitchen.presentation.menu;

import com.stanchefskitchen.data.models.ItemType;
import com.stanchefskitchen.data.models.MenuItem;
import com.stanchefskitchen.data.providers.MenuProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tyler Wong
 */
public class MenuController {
    public Map<ItemType, List<MenuItem>> menuMap = MenuProvider.getMenu();
    
    public List<ItemType> getItemTypes() {
        return new ArrayList<>(menuMap.keySet());
    }
    
    public List<MenuItem> getMenuItemsByItemType(ItemType itemType) {
        return menuMap.get(itemType);
    }
}
