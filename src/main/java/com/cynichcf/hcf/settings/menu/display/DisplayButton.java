package com.cynichcf.hcf.settings.menu.display;

import rip.lazze.libraries.menu.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.Arrays;
import java.util.List;

public class DisplayButton extends Button {

    public String getName(Player player) {
        return ChatColor.LIGHT_PURPLE + "Change Display Settings";
    }


    public List<String> getDescription(Player player) {
        return Arrays.asList("", ChatColor.BLUE + "Click to modify more", ChatColor.BLUE + "display based colors.");
    }


    public Material getMaterial(Player player) {
        return Material.PAINTING;
    }


    public void clicked(Player player, int slot, ClickType clickType) {
        new DisplayMenu().openMenu(player);
    }
}
