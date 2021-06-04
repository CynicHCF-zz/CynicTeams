package com.cynichcf.hcf.listener;

import com.cynichcf.hcf.util.AbstractMenu;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;


public class MenuListener implements Listener
{
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof AbstractMenu) {
            ((AbstractMenu)event.getInventory().getHolder()).onInventoryClick(event);
        }
    }

    @EventHandler
    public void onInventoryDrag(final InventoryDragEvent event) {
        if (event.getInventory().getHolder() instanceof AbstractMenu) {
            ((AbstractMenu)event.getInventory().getHolder()).onInventoryDrag(event);
        }
    }

    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof AbstractMenu) {
            ((AbstractMenu)event.getInventory().getHolder()).onInventoryClose(event);
        }
    }

}
