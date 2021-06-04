package com.cynichcf.hcf.util;

import com.cynichcf.hcf.HCF;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class AbstractMenu implements InventoryHolder
{
    protected HCF plugin;
    protected Inventory inventory;
    
    public AbstractMenu(final HCF plugin, final int size, String title) {
        this.plugin = plugin;
        if (title.length() > 32) {
            title = title.substring(0, 32);
        }
        this.inventory = Bukkit.createInventory((InventoryHolder)this, size, title);
    }
    
    public void open(final Player player) {
        player.openInventory(this.inventory);
    }
    
    public abstract void onInventoryClick(final InventoryClickEvent p0);
    
    public abstract void onInventoryDrag(final InventoryDragEvent p0);
    
    public abstract void onInventoryClose(final InventoryCloseEvent p0);
    
    public HCF getPlugin() {
        return this.plugin;
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
}
