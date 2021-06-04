package com.cynichcf.hcf.commands;

import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import rip.lazze.libraries.util.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand {
    @Command(names={"item", "i", "get"}, permission="basic.give", description="Spawn yourself an item")
    public static void item(Player sender, @Param(name="item") ItemStack item, @Param(name="amount", defaultValue="1") int amount) {
        if (amount < 1) {
            sender.sendMessage((Object)ChatColor.RED + "The amount must be greater than zero.");
            return;
        }
        item.setAmount(amount);
        sender.getInventory().addItem(item);
        sender.sendMessage((Object)ChatColor.GOLD + "Giving " + (Object)ChatColor.WHITE + amount + (Object)ChatColor.GOLD + " of " + (Object)ChatColor.WHITE + ItemUtils.getName((ItemStack)item) + (Object)ChatColor.GOLD + ".");
    }

    @Command(names={"give"}, permission="basic.give.other", description="Spawn a player an item")
    public static void give(Player sender, @Param(name="player") Player target, @Param(name="item") ItemStack item, @Param(name="amount", defaultValue="1") int amount) {
        if (amount < 1) {
            sender.sendMessage((Object)ChatColor.RED + "The amount must be greater than zero.");
            return;
        }
        item.setAmount(amount);
        target.getInventory().addItem(item);
        sender.sendMessage((Object)ChatColor.GOLD + "Giving " + (Object)ChatColor.WHITE + target.getDisplayName() + (Object)ChatColor.WHITE + " " + amount + (Object)ChatColor.GOLD + " of " + (Object)ChatColor.WHITE + ItemUtils.getName((ItemStack)item) + (Object)ChatColor.GOLD + ".");
    }
}

