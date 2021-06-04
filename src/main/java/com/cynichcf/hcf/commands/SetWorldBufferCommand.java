package com.cynichcf.hcf.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.cynichcf.hcf.HCF;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;

public class SetWorldBufferCommand {

    @Command(names={ "SetWorldBuffer" }, permission="op")
    public static void setWorldBuffer(Player sender, @Param(name="worldBuffer") int newBuffer) {
        HCF.getInstance().getMapHandler().setWorldBuffer(newBuffer);
        sender.sendMessage(ChatColor.GRAY + "The world buffer is now set to " + newBuffer + " blocks.");

        new BukkitRunnable() {

            
            public void run() {
                HCF.getInstance().getMapHandler().saveWorldBuffer();
            }

        }.runTaskAsynchronously(HCF.getInstance());
    }

}
