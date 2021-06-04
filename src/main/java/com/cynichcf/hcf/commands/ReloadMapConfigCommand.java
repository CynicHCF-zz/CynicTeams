package com.cynichcf.hcf.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.Command;

public class ReloadMapConfigCommand {

    @Command(names={ "reloadMapConfig" }, permission="op")
    public static void reloadMapConfig(Player sender) {
        HCF.getInstance().getMapHandler().reloadConfig();
        sender.sendMessage(ChatColor.DARK_PURPLE + "Reloaded mapInfo.json from file.");
    }

}