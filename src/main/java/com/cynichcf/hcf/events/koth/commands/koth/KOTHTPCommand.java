package com.cynichcf.hcf.events.koth.commands.koth;

import com.cynichcf.hcf.HCF;
import com.cynichcf.hcf.events.Event;
import com.cynichcf.hcf.events.dtc.DTC;
import com.cynichcf.hcf.events.koth.KOTH;
import rip.lazze.libraries.command.Command;
import rip.lazze.libraries.command.Param;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class KOTHTPCommand {

    @Command(names={ "KOTH TP", "KOTHTP", "events tp", "event tp" }, permission="foxtrot.koth")
    public static void kothTP(Player sender, @Param(name="koth", defaultValue="active") Event event) {

        switch (event.getType()) {
            case KOTH: {
                sender.teleport(((KOTH) event).getCapLocation().toLocation(HCF.getInstance().getServer().getWorld(((KOTH) event).getWorld())));
                sender.sendMessage(ChatColor.GRAY + "Teleported to the " + event.getName() + " KOTH.");
                break;
            }
            case DTC: {
                sender.teleport(((DTC) event).getCapLocation().toLocation(HCF.getInstance().getServer().getWorld(((DTC) event).getWorld())));
                sender.sendMessage(ChatColor.GRAY + "Teleported to the " + event.getName() + " DTC.");
                break;
            }
            default: {
                sender.sendMessage(ChatColor.RED + "You can't TP to an event that doesn't have a location.");
            }
        }
    }

}