package com.cynichcf.hcf.map.kits.command;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.map.kits.Kit;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class KitCommands {

    @Command(names = { "kit create" }, permission = "op")
    public static void kitCreateCommand(Player sender, @Param(name = "name", wildcard = true) String name) {
        if (!HCF.getInstance().getMapHandler().isKitMap() && !HCF.getInstance().getServerHandler().isVeltKitMap()) {
            sender.sendMessage("§cThis is a KitMap only command.");
            return;
        }

        if (HCF.getInstance().getMapHandler().getKitManager().get(name) != null) {
            sender.sendMessage(ChatColor.RED + "That kit already exists.");
            return;
        }

        Kit kit = HCF.getInstance().getMapHandler().getKitManager().getOrCreate(name);

        kit.update(sender.getInventory());
        HCF.getInstance().getMapHandler().getKitManager().save();

        sender.sendMessage(ChatColor.YELLOW + "The " + ChatColor.BLUE + kit.getName() + ChatColor.YELLOW
                + " kit has been created from your inventory.");
    }

    @Command(names = { "kit delete" }, permission = "op")
    public static void kitDeleteCommand(Player sender, @Param(name = "kit", wildcard = true) Kit kit) {
        if (!HCF.getInstance().getMapHandler().isKitMap() && !HCF.getInstance().getServerHandler().isVeltKitMap()) {
            sender.sendMessage("§cThis is a KitMap only command.");
            return;
        }

        HCF.getInstance().getMapHandler().getKitManager().delete(kit);

        sender.sendMessage(
                ChatColor.YELLOW + "Kit " + ChatColor.BLUE + kit.getName() + ChatColor.YELLOW + " has been deleted.");
    }

    @Command(names = { "kit edit" }, permission = "op")
    public static void kitEditCommand(Player sender, @Param(name = "kit", wildcard = true) Kit kit) {
        if (!HCF.getInstance().getMapHandler().isKitMap() && !HCF.getInstance().getServerHandler().isVeltKitMap()) {
            sender.sendMessage("§cThis is a KitMap only command.");
            return;
        }

        kit.update(sender.getInventory());
        HCF.getInstance().getMapHandler().getKitManager().save();

        sender.sendMessage(ChatColor.YELLOW + "Kit " + ChatColor.BLUE + kit.getName() + ChatColor.YELLOW
                + " has been edited and saved.");
    }

    @Command(names = { "kit load" }, permission = "op")
    public static void kitLoadCommand(Player sender, @Param(name = "kit", wildcard = true) Kit kit) {
        if (!HCF.getInstance().getMapHandler().isKitMap() && !HCF.getInstance().getServerHandler().isVeltKitMap()) {
            sender.sendMessage("§cThis is a KitMap only command.");
            return;
        }

        kit.apply(sender);

        sender.sendMessage(ChatColor.YELLOW + "Applied the " + ChatColor.BLUE + kit.getName() + ChatColor.YELLOW + ".");
    }

}
