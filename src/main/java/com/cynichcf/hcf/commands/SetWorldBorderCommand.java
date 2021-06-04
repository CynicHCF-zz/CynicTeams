package com.cynichcf.hcf.commands;

import com.cynichcf.hcf.listener.BorderListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;

public class SetWorldBorderCommand {

    @Command(names={ "SetWorldBorder" }, permission="op")
    public static void setWorldBorder(Player sender, @Param(name="border") int border) {
        BorderListener.BORDER_SIZE = border;
        sender.sendMessage(ChatColor.GRAY + "The world border is now set to " + BorderListener.BORDER_SIZE + " blocks.");


        new BukkitRunnable() {


            public void run() {
                HCF.getInstance().getMapHandler().saveBorder();
            }

        }.runTaskAsynchronously(HCF.getInstance());
    }

}